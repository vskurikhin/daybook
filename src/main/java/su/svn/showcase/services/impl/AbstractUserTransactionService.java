/*
 * This file was last modified at 2020.02.15 18:13 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * AbstractUserTransactionService.java$
 * $Id$
 */

package su.svn.showcase.services.impl;

import org.slf4j.Logger;
import su.svn.showcase.dao.Dao;
import su.svn.showcase.domain.DBEntity;
import su.svn.showcase.dto.Dto;
import su.svn.showcase.exceptions.ErrorCase;
import su.svn.showcase.utils.CollectionUtil;

import javax.transaction.*;
import java.util.List;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.stream.Collectors;

abstract class AbstractUserTransactionService {

    abstract UserTransaction getUserTransaction();

    abstract Logger getLogger();

    <K, T extends DBEntity<K>, D extends Dao<K, T>, O extends Dto<K>> T find(D dao, O dto, Class<T> c) {
        if (dto != null) {
            T o = dao.findById(dto.getId()).orElse(null);
            return c.isInstance(o) ? c.cast(o) : null;
        }
        return null;
    }

    <K, T extends DBEntity<K>, D extends Dao<K, T>> void save(D dao, T entity) {
        try {
            getUserTransaction().begin();
            dao.save(entity);
            getUserTransaction().commit();
        } catch ( NotSupportedException
                | HeuristicMixedException
                | HeuristicRollbackException
                | RollbackException
                | RuntimeException
                | SystemException e) {
            try {
                getUserTransaction().rollback();
            } catch (SystemException ex) {
                getLogger().error("Can't rollback because had the exception ", ex);
            }
            errorLoggin(entity);
            throw ErrorCase.open(getLogger(),"Can't save because had the exception ", e);
        }
    }

    <K, T extends DBEntity<K>> void execute(Runnable runnable) {
        try {
            getUserTransaction().begin();
            runnable.run();
            getUserTransaction().commit();
        } catch ( NotSupportedException
                | HeuristicMixedException
                | HeuristicRollbackException
                | RollbackException
                | RuntimeException
                | SystemException e) {
            try {
                getUserTransaction().rollback();
            } catch (SystemException ex) {
                getLogger().error("Can't rollback because had the exception ", ex);
            }
            throw ErrorCase.open(getLogger(), "Can't execute because had the exception ", e);
        }
    }

    <K, T extends DBEntity<K>> void consume(Consumer<T> consumer, T entity) {
        try {
            getUserTransaction().begin();
            consumer.accept(entity);
            getUserTransaction().commit();
        } catch ( NotSupportedException
                | HeuristicMixedException
                | HeuristicRollbackException
                | RollbackException
                | RuntimeException
                | SystemException e) {
            try {
                getUserTransaction().rollback();
            } catch (SystemException ex) {
                getLogger().error("Can't rollback because had the exception ", ex);
            }
            errorLoggin(entity);
            throw ErrorCase.open(getLogger(),"Can't consume because had the exception ", e);
        }
    }

    <K, I, X extends DBEntity<K>, Y extends DBEntity<I>> void biConsume(BiConsumer<X, Y> consumer, X x, Y y) {
        try {
            getUserTransaction().begin();
            consumer.accept(x, y);
            getUserTransaction().commit();
        } catch ( NotSupportedException
                | HeuristicMixedException
                | HeuristicRollbackException
                | RollbackException
                | RuntimeException
                | SystemException e) {
            try {
                getUserTransaction().rollback();
            } catch (SystemException ex) {
                getLogger().error("Can't rollback because had the exception ", ex);
            }
            errorLoggin(x);
            errorLoggin(y);
            throw ErrorCase.open(getLogger(),"Can't biConsume because had the exception ", e);
        }
    }

    <K, T extends Dto<K>> void iterate(Consumer<Iterable<T>> consumer, Iterable<T> entities) {
        List<K> uuids = CollectionUtil.iterableToStream(entities)
                .map(Dto::getId)
                .collect(Collectors.toList());
        try {
            getUserTransaction().begin();
            consumer.accept(entities);
            getUserTransaction().commit();
        } catch ( NotSupportedException
                | HeuristicMixedException
                | HeuristicRollbackException
                | RollbackException
                | RuntimeException
                | SystemException e) {
            try {
                getUserTransaction().rollback();
            } catch (SystemException ex) {
                getLogger().error("Can't rollback because had the exception ", ex);
            }
            errorLoggin(uuids);
            throw ErrorCase.open(getLogger(),"Can't iterate because had the exception ", e);
        }
    }

    <D extends Dto> void validateId(D dto) {
        Objects.requireNonNull(dto);
        Objects.requireNonNull(dto.getId());
    }

    private <K> void errorLoggin(K e) {
        getLogger().error("Error with : {}", e);
    }

    private <K, T extends DBEntity<K>> void errorLoggin(T entity) {
        K id = null;
        Class<?> c = null;
        if (entity != null) {
            id = entity.getId();
            c = entity.getClass();
        }
        getLogger().error("Error with DBEntity : {} and id : {}", c, id);
    }
}
//EOF
