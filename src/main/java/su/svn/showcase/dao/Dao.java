/*
 * This file was last modified at 2020.02.09 23:39 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * Dao.java
 * $Id$
 */

package su.svn.showcase.dao;

import su.svn.showcase.domain.DBEntity;
import su.svn.showcase.exceptions.ErrorCase;

import java.util.Collection;
import java.util.Optional;

/**
 * The Data-Access Object interface as a contract of how to interact with all DAO's.
 *
 * @author Victor N. Skurikhin
 */
public interface Dao<K, E extends DBEntity<K>> {

    /**
     * Retrieves the record of entity by key.
     *
     * @param id - key.
     * @return record of entity by key.
     * @throws ErrorCase if {@code id} is {@literal null}.
     */
    Optional<E> findById(K id);

    /**
     * Returns all instances of the type.
     *
     * @return all entities
     */
    Collection<E> findAll();

    /**
     * Retrieves all records of an entity type by the set of possible key values.
     *
     * @param ids - possible values.
     * @return records of entity by condition.
     * @throws ErrorCase in case the given {@link Iterable} is {@literal null}.
     */
    Collection<E> findAllByIdIn(Iterable<K> ids);

    /**
     * Returns the number of entities available.
     *
     * @return the number of entities.
     */
    long count();

    // @return {@literal true} if all entities saved, {@literal false} otherwise.
    /**
     * Saves a given entity.
     *
     * @param entity must not be {@literal null}.
     * @return the saved entity will never be {@literal null}.
     * @throws ErrorCase in case the given {@code id} is {@literal null}
     */
    E save(E entity);

    /**
     * Saves all given entities.
     *
     * @param entities must not be {@literal null}.
     * @return the saved entities will never be {@literal null}.
     * @throws IllegalArgumentException in case the given {@link Iterable} is {@literal null}.
     */
    Iterable<E> saveAll(Iterable<E> entities);

    /**
     * Deletes the entity with the given id.
     *
     * @param id must not be {@literal null}.
     * @throws ErrorCase in case the given {@code id} is {@literal null}
     */
    void delete(K id);

    /**
     * Deletes the given entities.
     *
     * @param entities
     * @return {@literal true} if all entities deleted, {@literal false} otherwise.
     * @throws ErrorCase in case the given {@link Iterable} is {@literal null}.
     */
    void deleteAll(Iterable<E> entities);
}
//EOF