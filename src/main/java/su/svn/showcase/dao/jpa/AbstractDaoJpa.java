/*
 * This file was last modified at 2020.02.13 21:28 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * AbstractDaoJpa.java
 * $Id$
 */

package su.svn.showcase.dao.jpa;

import org.slf4j.Logger;
import su.svn.showcase.dao.Dao;
import su.svn.showcase.domain.DBEntity;
import su.svn.showcase.domain.Record;
import su.svn.showcase.exceptions.ErrorCase;
import su.svn.showcase.utils.CollectionUtil;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.metamodel.EntityType;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Supplier;

import static su.svn.showcase.utils.CollectionUtil.convertList;

/**
 * Abstract Data-Access Object class to be implemented by all DAO's.
 *
 * @author Victor N. Skurikhin
 */
abstract class AbstractDaoJpa<K, E extends DBEntity<K>> implements Dao<K, E> {

    /**
     * The entityManager fields are protected so that subclasses,
     * i.e. specific DAO implementations, can access them.
     *
     * @return Entity Manager field.
     */
    abstract EntityManager getEntityManager();

    abstract Logger getLogger();

    /**
     * The entityClass fields are protected so that subclasses,
     * i.e. specific DAO implementations, can access them.
     *
     * @return Entity Class field.
     */
    abstract Class<E> getEClass();

    <T> List<T> toList(Iterable<T> iterable) {
        List<T> list = CollectionUtil.iterableToList(iterable);
        if (list.isEmpty()) {
            throw ErrorCase.open(getLogger(), "The parameter list is empty");
        }
        return list;
    }

    // Retrieves the meta-model for a certain entity.
    EntityType<E> getMetaModel() {
        return getEntityManager().getMetamodel().entity(getEClass());
    }

    // Retrieves the record of entity by key.
    //
    // @param id - key.
    Optional<E> abstractDaoFindById(K id) {
        EntityManager em = getEntityManager();
        try {
            E entry = em.find(getEClass(), id);
            return Optional.ofNullable(entry);
        } catch (NoResultException e) {
            return Optional.empty();
        } catch (IllegalArgumentException | IllegalStateException e) {
            throw ErrorCase.open(getLogger(), "Can't search because had the exception", e);
        }
    }

    // Retrieves the record of entity by namedQuery by the parameter and his value.
    //
    // @param namedQuery - query.
    // @param parameter - name of parameter.
    // @param value - parameter value.
    // @param <T> - type of parameter value.
    <T> Optional<E> abstractDaoFindWhereField(String namedQuery, String parameter, T value) {
        EntityManager em = getEntityManager();
        try {
            return Optional.of(em.createNamedQuery(namedQuery, getEClass())
                    .setParameter(parameter, value)
                    .getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        } catch (IllegalArgumentException | IllegalStateException | PersistenceException e) {
            throw ErrorCase.open(getLogger(), "Can't search by condition because had the exception", e);
        }
    }

    // Returns whether an entity with the given id exists.
    //
    // @param id must not be {@literal null}.
    boolean abstractExistsById(K id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(getEClass(), id) != null;
        } catch (NoResultException e) {
            return false;
        } catch (IllegalArgumentException | IllegalStateException | PersistenceException e) {
            throw ErrorCase.open(getLogger(), "Brocken check is exists because had the exception", e);
        }
    }

    // Retrieves all records of entity by namedQuery.
    //
    // @param namedQuery - query.
    List<E> abstractDaoFindAll(String namedQuery) {
        EntityManager em = getEntityManager();
        try {
            return em.createNamedQuery(namedQuery, getEClass()).getResultList();
        } catch (IllegalArgumentException | IllegalStateException | PersistenceException e) {
            throw ErrorCase.open(getLogger(), "Can't search all because had the exception", e);
        }
    }

    // Retrieves all records of entity by namedQuery by the parameter and his value.
    //
    // @param namedQuery - query.
    // @param parameter - name of parameter.
    // @param value - parameter value.
    // @param <T> - type of parameter value.
    <T> List<E> abstractDaoFindAllWhereField(String namedQuery, String parameter, T value) {
        EntityManager em = getEntityManager();
        try {
            return em.createNamedQuery(namedQuery, getEClass())
                    .setParameter(parameter, value)
                    .getResultList();
        } catch (IllegalArgumentException | IllegalStateException | PersistenceException e) {
            throw ErrorCase.open(getLogger(), "Can't search all because had the exception", e);
        }
    }

    // Retrieves all records of entity by namedQuery by the parameter and his possible values.
    //
    // @param namedQuery - query.
    // @param parameter - name of parameter.
    // @param iterable - collection of parameter values.
    <T> List<E> abstractDaoFindAllWhereIn(String namedQuery, String parameter, Iterable<T> iterable) {
        if (iterable == null) {
            throw ErrorCase.open(getLogger(), "Can't search by condition because had the exception",
                    new IllegalArgumentException());
        }
        return abstractDaoFindAllWhereField(namedQuery, parameter, toList(iterable));
    }

    // Retrieves all records of entity by namedQuery by the native named query.
    //
    // @param namedQuery - query.
    // @param tClass
    // @param <T>
    <T> List<T> abstractDaoNativeResultList(String namedQuery, Class<T> tClass) {
        EntityManager em = getEntityManager();
        try {
            return convertList(em.createNativeQuery(namedQuery).getResultList(), tClass);
        } catch (IllegalArgumentException | IllegalStateException | PersistenceException e) {
            throw ErrorCase.open(getLogger(), "Can't search native because had the exception", e);
        }
    }

    List<E> jpaRange(String query, int start, int size) {
        try {
            TypedQuery<E> typedQuery = getEntityManager().createNamedQuery(query, getEClass());
            typedQuery.setFirstResult(start);
            typedQuery.setMaxResults(size);

            return typedQuery.getResultList();
        } catch (IllegalArgumentException | IllegalStateException | PersistenceException e) {
            throw ErrorCase.open(getLogger(), "Can't search with the range because had the exception ", e);
        }
    }

    List<E> jpaRangeIdIn(String query, int start, int size, Iterable<K> ids) {
        if (ids == null) {
            throw new IllegalArgumentException();
        }
        try {
            TypedQuery<E> typedQuery = getEntityManager().createNamedQuery(query, getEClass());
            typedQuery.setFirstResult(start);
            typedQuery.setMaxResults(size);
            typedQuery.setParameter("ids", toList(ids));

            return typedQuery.getResultList();
        } catch (IllegalArgumentException | IllegalStateException | PersistenceException e) {
            throw ErrorCase.open(getLogger(), "Can't search by ids with the range because had the exception ", e);
        }
    }

    // Returns the number of entities available.
    long abstractCount() {
        CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        countQuery.select(criteriaBuilder.count(countQuery.from(getEClass())));
        return getEntityManager().createQuery(countQuery).getSingleResult();
    }

    // Saves a given entity.
    //
    // @param entity must not be {@literal null}.
    E abstractDaoSave(E entity) {
        EntityManager em = getEntityManager();
        try {
            if (null == entity.getId()) {
                em.persist(entity);
                em.flush();
            } else {
                em.merge(entity);
                em.flush();
            }
        } catch (RuntimeException e) {
            throw  ErrorCase.open(getLogger(), "Can't save because had the exception", e);
        }
        getLogger().info("Save {} with id: {}", getEClass().getSimpleName(), entity.getId());
        return entity;
    }

    E abstractDaoSave(Supplier<E> supplier) {
        return abstractDaoSave(supplier.get());
    }

    // Saves all given entities.
    //
    // @param entities must not be {@literal null}.
    Iterable<E> abstractDaoSaveAll(Iterable<E> entities) {
        if (entities == null) {
            throw new IllegalArgumentException();
        }
        List<K> ids = new ArrayList<>();
        EntityManager em = getEntityManager();
        try {

            for (E entity : entities) {
                if (null == entity.getId()) {
                    em.persist(entity);
                } else {
                    em.merge(entity);
                }
                ids.add(entity.getId());
            }
            em.flush();
        } catch (RuntimeException e) {
            throw  ErrorCase.open(getLogger(), "Can't save because had the exception", e);
        }
        getLogger().info("Save {} with ids: {}", getEClass().getSimpleName(), ids);
        return entities;
    }

    // Deletes the entity with the given id.
    //
    // @param id must not be {@literal null}.
    void abstractDaoDelete(K id) {
        EntityManager em = getEntityManager();
        try {
            abstractDaoFindById(id).ifPresent(e -> {
                e = em.merge(e);
                em.remove(e);
                em.flush();
            });
        } catch (RuntimeException e) {
            throw ErrorCase.open(getLogger(), "Can't delete because had the exception", e);
        }
        getLogger().info("Delete {} with id: {}", getEClass().getSimpleName(), id);
    }

    // Deletes the given entities.
    //
    // @param entities
    void abstractDaoDeleteAll(Iterable<E> entities) {
        if (entities == null) {
            throw new IllegalArgumentException();
        }
        List<K> ids = new ArrayList<>();
        EntityManager em = getEntityManager();
        try {

            for (E entity : entities) {
                em.remove(entity);
                ids.add(entity.getId());
            }
            em.flush();
        } catch (RuntimeException e) {
            throw ErrorCase.open(getLogger(), "Can't save because had the exception", e);
        }
        getLogger().info("Save {} with ids: {}", getEClass().getSimpleName(), ids);
    }

    void close() {
        getEntityManager().close();
    }
}
//EOF
