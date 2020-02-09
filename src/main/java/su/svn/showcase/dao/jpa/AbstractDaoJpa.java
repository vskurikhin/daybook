/*
 * This file was last modified at 2020.02.09 13:36 by Victor N. Skurikhin.
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

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.metamodel.EntityType;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

import static su.svn.showcase.utils.CollectionUtil.convertList;

/**
 * Abstract Data-Access Object class to be implemented by all DAO's.
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

    /**
     * Retrieves the meta-model for a certain entity.
     *
     * @return the meta-model of a certain entity.
     */
    protected EntityType<E> getMetaModel() {
        return getEntityManager().getMetamodel().entity(getEClass());
    }

    /**
     * Retrieves the record of entity by key.
     *
     * @param id - key.
     * @return record of entity by key.
     */
    protected Optional<E> abstractDaoFindById(K id) {
        EntityManager em = getEntityManager();
        try {
            E entry = em.find(getEClass(), id);
            return Optional.ofNullable(entry);
        } catch (IllegalArgumentException | IllegalStateException e) {
            getLogger().error("Can't search because had the exception", e);
        }
        return Optional.empty();
    }

    /**
     * Retrieves the record of entity by namedQuery by the parameter and his value.
     *
     * @param namedQuery - query.
     * @param parameter - name of parameter.
     * @param value - parameter value.
     * @param <T> - type of parameter value.
     * @return record of entity by query or empty.
     */
    protected <T> Optional<E> abstractDaoFindWhereField(String namedQuery, String parameter, T value) {
        EntityManager em = getEntityManager();
        try {
            return Optional.of(em.createNamedQuery(namedQuery, getEClass())
                    .setParameter(parameter, value)
                    .getSingleResult());
        } catch (IllegalArgumentException | IllegalStateException | PersistenceException e) {
            getLogger().error("Can't search all because had the exception", e);
        }
        return Optional.empty();
    }


    /**
     * Returns whether an entity with the given id exists.
     *
     * @param id must not be {@literal null}.
     * @return {@literal true} if an entity with the given id exists, {@literal false} otherwise.
     * @throws IllegalArgumentException if {@code id} is {@literal null}.
     */
    protected boolean abstractExistsById(K id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(getEClass(), id) != null;
        } catch (IllegalArgumentException | IllegalStateException | PersistenceException e) {
            getLogger().error("Can't search all because had the exception", e);
        }
        return false;
    }

    /**
     * Retrieves all records of entity by namedQuery.
     *
     * @param namedQuery - query.
     * @return records of entity by query.
     */
    protected List<E> abstractDaoFindAll(String namedQuery) {
        EntityManager em = getEntityManager();
        try {
            return em.createNamedQuery(namedQuery, getEClass()).getResultList();
        } catch (IllegalArgumentException | IllegalStateException | PersistenceException e) {
            getLogger().error("Can't search all because had the exception", e);
        }
        return Collections.emptyList();
    }

    /**
     * Retrieves all records of entity by namedQuery by the parameter and his value.
     *
     * @param namedQuery - query.
     * @param parameter - name of parameter.
     * @param value - parameter value.
     * @param <T> - type of parameter value.
     * @return records of entity by query.
     */
    protected <T> List<E> abstractDaoFindAllWhereField(String namedQuery, String parameter, T value) {
        EntityManager em = getEntityManager();
        try {
            return em.createNamedQuery(namedQuery, getEClass())
                    .setParameter(parameter, value)
                    .getResultList();
        } catch (IllegalArgumentException | IllegalStateException | PersistenceException e) {
            getLogger().error("Can't search all because had the exception", e);
        }
        return Collections.emptyList();
    }

    /**
     * Retrieves all records of entity by namedQuery by the parameter and his possible values.
     *
     * @param namedQuery - query.
     * @param parameter - name of parameter.
     * @param iterable - collection of parameter values.
     * @return records of entity by query.
     */
    protected <T> List<E> abstractDaoFindAllWhereIn(String namedQuery, String parameter, Iterable<T> iterable) {
        List<T> list = CollectionUtil.iterableToList(iterable);
        if (list.isEmpty()) {
            return Collections.emptyList();
        }
        return abstractDaoFindAllWhereField(namedQuery, parameter, list);
    }

    /**
     * Retrieves all records of entity by namedQuery by the native named query.
     *
     * @param namedQuery - query.
     * @param tClass
     * @param <T>
     * @return records of entity by query.
     */
    protected  <T> List<T> abstractDaoNativeResultList(String namedQuery, Class<T> tClass) {
        EntityManager em = getEntityManager();
        try {
            return convertList(em.createNativeQuery(namedQuery).getResultList(), tClass);
        } catch (IllegalArgumentException | IllegalStateException | PersistenceException e) {
            getLogger().error("Can't search native because had the exception", e);
            return Collections.emptyList();
        }
    }

    /**
     * Returns the number of entities available.
     *
     * @return the number of entities
     */
    protected long abstractCount() {
        CriteriaBuilder criteriaBuilder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        countQuery.select(criteriaBuilder.count(countQuery.from(getEClass())));
        return getEntityManager().createQuery(countQuery).getSingleResult();
    }

    /**
     * Saves a given entity.
     * Use the true if further operations as the save operation
     * might have changed the entity instance completely.
     *
     * @param entity must not be {@literal null}.
     * @return {@literal true} if an entity saved, {@literal false} otherwise.
     */
    protected boolean abstractDaoSave(E entity) {
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
            getLogger().error("Can't save because had the exception", e);
            return false;
        }
        getLogger().info("Save {} with id: {}", getEClass().getSimpleName(), entity.getId());
        return true;
    }

    protected boolean abstractDaoSave(Supplier<E> supplier) {
        return abstractDaoSave(supplier.get());
    }

    /**
     * Saves all given entities.
     *
     * @param entities must not be {@literal null}.
     * @return {@literal true} if all entities saved, {@literal false} otherwise.
     */
    protected boolean abstractDaoSaveAll(Iterable<? extends E> entities) {
        List<K> ids = new ArrayList<>();
        EntityManager em = getEntityManager();
        try {
            if (entities == null) {
                return false;
            }

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
            getLogger().error("Can't save because had the exception", e);
            return false;
        }
        getLogger().info("Save {} with ids: {}", getEClass().getSimpleName(), ids);
        return true;
    }

    /**
     * Deletes the entity with the given id.
     *
     * @param id must not be {@literal null}.
     * @throws NoResultException in case the given {@code id} is {@literal null}
     */
    protected boolean abstractDaoDelete(K id) {
        EntityManager em = getEntityManager();
        try {
            E merged = em.merge(abstractDaoFindById(id).orElseThrow(NoResultException::new));
            em.remove(merged);
            em.flush();
        } catch (RuntimeException e) {
            getLogger().error("Can't delete because had the exception", e);
            return false;
        }
        getLogger().info("Delete {} with id: {}", getEClass().getSimpleName(), id);
        return true;
    }

    /**
     * Deletes the given entities.
     *
     * @param entities
     * @throws IllegalArgumentException in case the given {@link Iterable} is {@literal null}.
     */
    protected boolean abstractDaoDeleteAll(Iterable<? extends E> entities) {
        List<K> ids = new ArrayList<>();
        EntityManager em = getEntityManager();
        try {
            if (entities == null) {
                return false;
            }

            for (E entity : entities) {
                em.remove(entity);
                ids.add(entity.getId());
            }
            em.flush();
        } catch (RuntimeException e) {
            getLogger().error("Can't save because had the exception", e);
            return false;
        }
        getLogger().info("Save {} with ids: {}", getEClass().getSimpleName(), ids);
        return true;
    }

    protected void close() {
        getEntityManager().close();
    }
}
//EOF
