/*
 * This file was last modified at 2020.02.09 13:36 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * Dao.java
 * $Id$
 */

package su.svn.showcase.dao;

import su.svn.showcase.domain.DBEntity;

import javax.persistence.NoResultException;
import java.util.Collection;
import java.util.Optional;

public interface Dao<K, E extends DBEntity<K>> {

    /**
     * Returns all instances of the type.
     *
     * @return all entities
     */
    Collection<E> findAll();

    /**
     * Retrieves the record of entity by key.
     *
     * @param id - key.
     * @return record of entity by key.
     */
    Optional<E> findById(K id);

    /**
     * Returns the number of entities available.
     *
     * @return the number of entities
     */
    long count();

    /**
     * Saves a given entity.
     * Use the returned instance for further operations
     * as the save operation might have changed the entity instance completely.
     *
     * @param entity must not be {@literal null}.
     * @return the saved entity will never be {@literal null}.
     */
    boolean save(E entity);

    /**
     * Saves all given entities.
     *
     * @param entities must not be {@literal null}.
     * @return {@literal true} if all entities saved, {@literal false} otherwise.
     */
    boolean saveAll(Iterable<E> entities);

    /**
     * Deletes the entity with the given id.
     *
     * @param id must not be {@literal null}.
     * @throws NoResultException in case the given {@code id} is {@literal null}
     */
    boolean delete(K id);
}
//EOF