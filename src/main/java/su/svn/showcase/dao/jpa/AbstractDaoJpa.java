/*
 * This file was last modified at 2020.03.28 18:55 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * AbstractDaoJpa.java
 * $Id$
 */

package su.svn.showcase.dao.jpa;

import org.slf4j.Logger;
import su.svn.showcase.dao.Dao;
import su.svn.showcase.domain.DBEntity;
import su.svn.showcase.utils.CollectionUtil;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.metamodel.EntityType;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
        if (iterable == null) {
            throw new IllegalArgumentException();
        }
        List<T> list = CollectionUtil.iterableToList(iterable);
        if (list.isEmpty()) {
            throw new IllegalArgumentException();
        }
        return list;
    }

    // Retrieves the meta-model for a certain entity.
    //
    // @throws IllegalStateException if the entity manager has
    //         been closed
    EntityType<E> getMetaModel() {
        return getEntityManager().getMetamodel().entity(getEClass());
    }

    // Retrieves the record of entity by key.
    //
    // @param id - key.
    // @throws IllegalArgumentException if the first argument does
    //         not denote an entity type or the second argument is
    //         is not a valid type for that entitys primary key or
    //         is null
    Optional<E> jpaFindById(K id) {
        return Optional.ofNullable(getEntityManager().find(getEClass(), id));
    }

    // Retrieves the record of entity by namedQuery by the parameter and his value.
    //
    // @param namedQuery - query.
    // @param parameter - name of parameter.
    // @param value - parameter value.
    // @param <T> - type of parameter value.
    // @throws IllegalArgumentException if a query has not been
    //         defined with the given name or if the query string is
    //         found to be invalid or if the query result is found to
    //         not be assignable to the specified type or if called for a Java
    //         Persistence query language UPDATE or DELETE statement
    // @throws QueryTimeoutException if the query execution exceeds
    //         the query timeout value set and only the statement is
    //         rolled back
    // @throws TransactionRequiredException if a lock mode has
    //         been set and there is no transaction
    // @throws PessimisticLockException if pessimistic locking
    //         fails and the transaction is rolled back
    // @throws LockTimeoutException if pessimistic locking
    //         fails and only the statement is rolled back
    // @throws PersistenceException if the query execution exceeds
    //         the query timeout value set and the transaction
    //         is rolled back
    <T> Optional<E> jpaFindWhereField(String namedQuery, String parameter, T value) {
        EntityManager em = getEntityManager();
        try {
            return Optional.of(em.createNamedQuery(namedQuery, getEClass())
                    .setParameter(parameter, value)
                    .getSingleResult());
        } catch (NoResultException | NonUniqueResultException e) {
            return Optional.empty();
        }
    }

    // Returns whether an entity with the given id exists.
    //
    // @param id must not be {@literal null}.
    // @throws IllegalArgumentException if the first argument does
    //         not denote an entity type or the second argument is
    //         is not a valid type for that entitys primary key or
    //         is null
    boolean jpaExistsById(K id) {
        try {
            return getEntityManager().find(getEClass(), id) != null;
        } catch (NoResultException e) {
            return false;
        }
    }

    // Retrieves all records of entity by namedQuery.
    //
    // @param namedQuery - query.
    // @throws IllegalArgumentException if a query has not been
    //         defined with the given name or if the query string is
    //         found to be invalid or if the query result is found to
    //         not be assignable to the specified type
    // @throws QueryTimeoutException if the query execution exceeds
    //         the query timeout value set and only the statement is
    //         rolled back
    // @throws TransactionRequiredException if a lock mode has
    //         been set and there is no transaction
    // @throws PessimisticLockException if pessimistic locking
    //         fails and the transaction is rolled back
    // @throws LockTimeoutException if pessimistic locking
    //         fails and only the statement is rolled back
    // @throws PersistenceException if the query execution exceeds
    //         the query timeout value set and the transaction
    //         is rolled back
    List<E> jpaDaoFindAll(String namedQuery) {
        return getEntityManager().createNamedQuery(namedQuery, getEClass()).getResultList();
    }

    // Retrieves all records of entity by namedQuery by the parameter and his value.
    //
    // @param namedQuery - query.
    // @param parameter - name of parameter.
    // @param value - parameter value.
    // @param <T> - type of parameter value.
    // @throws IllegalArgumentException if a query has not been
    //         defined with the given name or if the query string is
    //         found to be invalid or if the query result is found to
    //         not be assignable to the specified type
    // @throws QueryTimeoutException if the query execution exceeds
    //         the query timeout value set and only the statement is
    //         rolled back
    // @throws TransactionRequiredException if a lock mode has
    //         been set and there is no transaction
    // @throws PessimisticLockException if pessimistic locking
    //         fails and the transaction is rolled back
    // @throws LockTimeoutException if pessimistic locking
    //         fails and only the statement is rolled back
    // @throws PersistenceException if the query execution exceeds
    //         the query timeout value set and the transaction
    //         is rolled back
    <T> List<E> jpaDaoFindAllWhereField(String namedQuery, String parameter, T value) {
        return getEntityManager().createNamedQuery(namedQuery, getEClass())
                .setParameter(parameter, value)
                .getResultList();
    }

    // Retrieves all records of entity by namedQuery by the parameter and his possible values.
    //
    // @param namedQuery - query.
    // @param parameter - name of parameter.
    // @param iterable - collection of parameter values.
    // @throws IllegalArgumentException if a query has not been
    //         defined with the given name or if the query string is
    //         found to be invalid or if the query result is found to
    //         not be assignable to the specified type
    //         or if iterable is null
    // @throws QueryTimeoutException if the query execution exceeds
    //         the query timeout value set and only the statement is
    //         rolled back
    // @throws TransactionRequiredException if a lock mode has
    //         been set and there is no transaction
    // @throws PessimisticLockException if pessimistic locking
    //         fails and the transaction is rolled back
    // @throws LockTimeoutException if pessimistic locking
    //         fails and only the statement is rolled back
    // @throws PersistenceException if the query execution exceeds
    //         the query timeout value set and the transaction
    //         is rolled back
    <T> List<E> jpaDaoFindAllWhereIn(String namedQuery, String parameter, Iterable<T> iterable) {
        if (iterable == null) {
            throw new IllegalArgumentException();
        }
        return jpaDaoFindAllWhereField(namedQuery, parameter, toList(iterable));
    }

    // Retrieves all records of entity by namedQuery by the native named query.
    //
    // @param namedQuery - query.
    // @param tClass
    // @param <T>
    // @throws IllegalArgumentException if a query has not been
    //         defined with the given name or if the query string is
    //         found to be invalid or if the query result is found to
    //         not be assignable to the specified type
    // @throws QueryTimeoutException if the query execution exceeds
    //         the query timeout value set and only the statement is
    //         rolled back
    // @throws TransactionRequiredException if a lock mode has
    //         been set and there is no transaction
    // @throws PessimisticLockException if pessimistic locking
    //         fails and the transaction is rolled back
    // @throws LockTimeoutException if pessimistic locking
    //         fails and only the statement is rolled back
    // @throws PersistenceException if the query execution exceeds
    //         the query timeout value set and the transaction
    //         is rolled back
    <T> List<T> jpaDaoNativeResultList(String namedQuery, Class<T> tClass) {
        return convertList(getEntityManager().createNativeQuery(namedQuery).getResultList(), tClass);
    }

    // Retrieves set of entity records with size of capacity
    // and start - the position of the first result to retrieve.
    //
    // @param query - namedQuery
    // @param start - the position of the first result to retrieve
    // @param size - capacity
    // @return
    //
    // @throws IllegalArgumentException if a query has not been
    //         defined with the given name or if the query string is
    //         found to be invalid or if the query result is found to
    //         not be assignable to the specified type
    // @throws IllegalArgumentException if the argument is negative
    // @throws IllegalStateException if called for a Java
    //         Persistence query language UPDATE or DELETE statement
    // @throws QueryTimeoutException if the query execution exceeds
    //         the query timeout value set and only the statement is
    //         rolled back
    // @throws TransactionRequiredException if a lock mode has
    //         been set and there is no transaction
    // @throws PessimisticLockException if pessimistic locking
    //         fails and the transaction is rolled back
    // @throws LockTimeoutException if pessimistic locking
    //         fails and only the statement is rolled back
    // @throws PersistenceException if the query execution exceeds
    //         the query timeout value set and the transaction
    //         is rolled back
    List<E> jpaRange(String query, int start, int size) {
        TypedQuery<E> typedQuery = getEntityManager().createNamedQuery(query, getEClass());
        typedQuery.setFirstResult(start);
        typedQuery.setMaxResults(size);

        return typedQuery.getResultList();
    }

    // Retrieves set of entity records with size of capacity
    // and start - the position of the first result to retrieve.
    //
    // @param query - namedQuery
    // @param start - the position of the first result to retrieve
    // @param size - capacity
    // @param ids
    // @return
    // @throws IllegalArgumentException if a query has not been
    //         defined with the given name or if the query string is
    //         found to be invalid or if the query result is found to
    //         not be assignable to the specified type
    // @throws IllegalArgumentException if the argument is negative
    //         or if ids is null
    // @throws IllegalStateException if called for a Java
    //         Persistence query language UPDATE or DELETE statement
    // @throws QueryTimeoutException if the query execution exceeds
    //         the query timeout value set and only the statement is
    //         rolled back
    // @throws TransactionRequiredException if a lock mode has
    //         been set and there is no transaction
    // @throws PessimisticLockException if pessimistic locking
    //         fails and the transaction is rolled back
    // @throws LockTimeoutException if pessimistic locking
    //         fails and only the statement is rolled back
    // @throws PersistenceException if the query execution exceeds
    //         the query timeout value set and the transaction
    //         is rolled back
    List<E> jpaRangeIdIn(String query, int start, int size, Iterable<K> ids) {
        if (ids == null) {
            throw new IllegalArgumentException();
        }
        TypedQuery<E> typedQuery = getEntityManager().createNamedQuery(query, getEClass());
        typedQuery.setFirstResult(start);
        typedQuery.setMaxResults(size);
        typedQuery.setParameter("ids", toList(ids));

        return typedQuery.getResultList();
    }

    // Returns the number of entities available.
    //
    // TODO
    long jpaCount() {
        CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        countQuery.select(criteriaBuilder.count(countQuery.from(getEClass())));
        return getEntityManager().createQuery(countQuery).getSingleResult();
    }

    // Saves a given entity.
    //
    // @param entity must not be {@literal null}.
    // @throws IllegalArgumentException if the instance is not an
    //         entity
    // @throws TransactionRequiredException if invoked on a
    //         container-managed entity manager of type
    //         <code>PersistenceContextType.TRANSACTION</code> and there is
    //         no transaction
    // @throws PersistenceException if the flush fails
    E jpaDaoSave(E entity) {
        EntityManager em = getEntityManager();
        if (null == entity.getId()) {
            em.persist(entity);
            em.flush();
        } else {
            em.merge(entity);
            em.flush();
        }
        getLogger().info("Save {} with id: {}", getEClass().getSimpleName(), entity.getId());
        return entity;
    }

    E jpaDaoSave(Supplier<E> supplier) {
        return jpaDaoSave(supplier.get());
    }

    // Saves all given entities.
    //
    // @param entities must not be {@literal null}.
    Iterable<E> jpaDaoSaveAll(Iterable<E> entities) {
        if (entities == null) {
            throw new IllegalArgumentException();
        }
        List<K> ids = new ArrayList<>();
        EntityManager em = getEntityManager();
        for (E entity : entities) {
            if (null == entity.getId()) {
                em.persist(entity);
            } else {
                em.merge(entity);
            }
            ids.add(entity.getId());
        }
        em.flush();
        getLogger().info("Save {} with ids: {}", getEClass().getSimpleName(), ids);
        return entities;
    }

    // Deletes the entity with the given id.
    //
    // @param id must not be {@literal null}.
    // @throws IllegalArgumentException if instance is not an
    //         entity or is a removed entity
    // @throws TransactionRequiredException if invoked on a
    //         container-managed entity manager of type
    //         <code>PersistenceContextType.TRANSACTION</code> and there is
    //         no transaction
    // @throws IllegalArgumentException if the instance is not an
    //         entity or is a detached entity
    // @throws TransactionRequiredException if invoked on a
    //         container-managed entity manager of type
    //         <code>PersistenceContextType.TRANSACTION</code> and there is
    //         no transaction
    // @throws TransactionRequiredException if there is
    //         no transaction
    // @throws PersistenceException if the flush fails
    void jpaDaoDelete(K id) {
        EntityManager em = getEntityManager();
        jpaFindById(id).ifPresent(e -> {
            e = em.merge(e);
            em.remove(e);
            em.flush();
        });
        getLogger().info("Delete {} with id: {}", getEClass().getSimpleName(), id);
    }

    // Deletes the given entities.
    //
    // @param entities
    // @throws IllegalArgumentException if instance is not an TODO
    //         entity or is a removed entity
    // @throws TransactionRequiredException if invoked on a
    //         container-managed entity manager of type
    //         <code>PersistenceContextType.TRANSACTION</code> and there is
    //         no transaction
    // @throws IllegalArgumentException if the instance is not an TODO merge IllegalArgumentException
    //         entity or is a detached entity
    // @throws TransactionRequiredException if invoked on a TODO merge IllegalArgumentException
    //         container-managed entity manager of type
    //         <code>PersistenceContextType.TRANSACTION</code> and there is
    //         no transaction
    // @throws TransactionRequiredException if there is TODO merge IllegalArgumentException
    //         no transaction
    // @throws PersistenceException if the flush fails
    void abstractDaoDeleteAll(Iterable<E> entities) {
        if (entities == null) {
            throw new IllegalArgumentException();
        }
        List<K> ids = new ArrayList<>();
        EntityManager em = getEntityManager();
        for (E entity : entities) {
            em.remove(entity);
            ids.add(entity.getId());
        }
        em.flush();
        getLogger().info("Save {} with ids: {}", getEClass().getSimpleName(), ids);
    }

    void close() {
        getEntityManager().close();
    }
}
//EOF
