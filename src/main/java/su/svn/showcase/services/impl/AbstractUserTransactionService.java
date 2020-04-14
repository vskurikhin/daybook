/*
 * This file was last modified at 2020.04.14 21:45 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * AbstractUserTransactionService.java
 * $Id$
 */

package su.svn.showcase.services.impl;

import org.slf4j.Logger;
import su.svn.showcase.domain.DBEntity;
import su.svn.showcase.dto.Dto;
import su.svn.showcase.exceptions.ErrorCase;
import su.svn.showcase.utils.CollectionUtil;
import su.svn.showcase.utils.StringUtil;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.Status;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

abstract class AbstractUserTransactionService {

    abstract UserTransaction getUserTransaction();

    abstract Logger getLogger();

    <K, T extends DBEntity<K>> Optional<T> utxFindById(EntityManager em, Class<T> tClass, K id) {
        try {
            getUserTransaction().begin();
            T result = em.find(tClass, id);
            getUserTransaction().commit();

            return Optional.ofNullable(result);
        } catch ( NotSupportedException
                | HeuristicMixedException
                | HeuristicRollbackException
                | RollbackException
                | RuntimeException
                | SystemException e) {
            rollbackIfStatusNoTransaction(e);
            throw ErrorCase.open(getLogger(), "Can't execute because had the exception ", e);
        } finally {
            if (em != null) em.close();
        }
    }

    <K, T extends DBEntity<K>> List<T> utxRange(EntityManager em, Class<T> tClass, String query, int start, int size) {
        try {
            getUserTransaction().begin();
            TypedQuery<T> typedQuery = em.createNamedQuery(query, tClass);
            typedQuery.setFirstResult(start);
            typedQuery.setMaxResults(size);
            List<T> result = typedQuery.getResultList();
            getUserTransaction().commit();

            return result;
        } catch ( NotSupportedException
                | HeuristicMixedException
                | HeuristicRollbackException
                | RollbackException
                | RuntimeException
                | SystemException e) {
            rollbackIfStatusNoTransaction(e);
            throw ErrorCase.open(getLogger(), "Can't execute because had the exception ", e);
        } finally {
            if (em != null) em.close();
        }
    }

    <K, T extends DBEntity<K>> void utxExecuteBySupplier(Supplier<EntityManager> supplier) {
        EntityManager entityManager = null;
        try {
            getUserTransaction().begin();
            entityManager = supplier.get();
            getUserTransaction().commit();
        } catch ( NotSupportedException
                | HeuristicMixedException
                | HeuristicRollbackException
                | RollbackException
                | RuntimeException
                | SystemException e) {
            rollbackIfStatusNoTransaction(e);
            throw ErrorCase.open(getLogger(), "Can't execute because had the exception ", e);
        } finally {
            if (entityManager != null) entityManager.close();
        }
    }

    <K, T extends Dto<K>> void utxConsumeByFunction(Function<T, EntityManager> function, T dto) {
        EntityManager entityManager = null;
        try {
            getUserTransaction().begin();
            entityManager = function.apply(dto);
            getUserTransaction().commit();
        } catch ( NotSupportedException
                | HeuristicMixedException
                | HeuristicRollbackException
                | RollbackException
                | RuntimeException
                | SystemException e) {
            rollbackIfStatusNoTransaction(e);
            errorLoggin(dto);
            throw ErrorCase.open(getLogger(),"Can't consume because had the exception ", e);
        } finally {
            if (entityManager != null) entityManager.close();
        }
    }

    <K, T extends Dto<K>> void utxConsumeByFunction(Function<Iterable<T>, EntityManager> function, Iterable<T> entities) {
        EntityManager entityManager = null;
        List<K> uuids = CollectionUtil.iterableToStream(entities)
                .map(Dto::getId)
                .collect(Collectors.toList());
        try {
            getUserTransaction().begin();
            entityManager = function.apply(entities);
            getUserTransaction().commit();
        } catch ( NotSupportedException
                | HeuristicMixedException
                | HeuristicRollbackException
                | RollbackException
                | RuntimeException
                | SystemException e) {
            rollbackIfStatusNoTransaction(e);
            errorLoggin(uuids);
            throw ErrorCase.open(getLogger(),"Can't iterate because had the exception ", e);
        } finally {
            if (entityManager != null) entityManager.close();
        }
    }

    <K, I, X extends DBEntity<K>, Y extends DBEntity<I>>
    void utxBiConsumeByFunction(BiFunction<X, Y, EntityManager> function, X x, Y y) {
        EntityManager entityManager = null;
        try {
            getUserTransaction().begin();
            entityManager = function.apply(x, y);
            getUserTransaction().commit();
        } catch ( NotSupportedException
                | HeuristicMixedException
                | HeuristicRollbackException
                | RollbackException
                | RuntimeException
                | SystemException e) {
            rollbackIfStatusNoTransaction(e);
            errorLoggin(x);
            errorLoggin(y);
            throw ErrorCase.open(getLogger(),"Can't biConsume because had the exception ", e);
        } finally {
            if (entityManager != null) entityManager.close();
        }
    }

    private void rollbackIfStatusNoTransaction(Exception exception) {
        if (getUserTransaction() == null) return;
        try {
            if (getUserTransaction().getStatus() != Status.STATUS_NO_TRANSACTION) {
                getUserTransaction().rollback();
            }
        } catch (SystemException e) {
            getLogger().error("Can't rollback because had the exception ", exception);
        }
    }

    <D extends Dto> void validateId(D dto) {
        Objects.requireNonNull(dto.getId());
    }

    <T extends Dto<String>> String getOrGenerateStringKey(T entity) {
        return entity.getId() != null ? entity.getId() : StringUtil.generateStringId();
    }

    <T extends Dto<UUID>> UUID getOrGenerateUuidKey(T entity) {
        return entity.getId() != null ? entity.getId() : UUID.randomUUID();
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
