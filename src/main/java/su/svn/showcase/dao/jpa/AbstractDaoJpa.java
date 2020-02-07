/*
 * This file was last modified at 2020.02.07 12:46 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * AbstractDaoJpa.java
 * $Id$
 */

package su.svn.showcase.dao.jpa;

import org.slf4j.Logger;
import su.svn.showcase.dao.Dao;
import su.svn.showcase.domain.DBEntity;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.metamodel.EntityType;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

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
     * Retrieves the all records of entity by namedQuery.
     *
     * @param namedQuery - query.
     * @return list of records of entity.
     */
    protected List<E> abstractDaoFindAll(String namedQuery) {
        EntityManager em = getEntityManager();
        try {
            return em.createNamedQuery(namedQuery, getEClass()).getResultList();
        } catch (IllegalArgumentException | IllegalStateException | PersistenceException e) {
            getLogger().error("Can't search all because had the exception", e);
            return Collections.emptyList();
        }
    }

    /**
     * Retrieves the record of entity by key.
     *
     * @param id - key.
     * @return record of entity.
     */
    Optional<E> abstractDaoFindById(K id) {
        EntityManager em = getEntityManager();
        try {
            E entry = em.find(getEClass(), id);
            return Optional.ofNullable(entry);
        } catch (IllegalArgumentException | IllegalStateException e) {
            getLogger().error("Can't search because had the exception", e);
            return Optional.empty();
        }
    }

    protected void close() {
        getEntityManager().close();
    }
}
//EOF
