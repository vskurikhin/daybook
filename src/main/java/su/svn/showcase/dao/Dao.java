/*
 * This file was last modified at 2020.02.07 12:20 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * Dao.java
 * $Id$
 */

package su.svn.showcase.dao;

import su.svn.showcase.domain.DBEntity;

import java.util.Collection;
import java.util.Optional;

public interface Dao<K, E extends DBEntity<K>> {
    Collection<E> findAll();

    Optional<E> findById(K id);

    boolean save(E entity);

    boolean saveAll(Iterable<E> entities);

    boolean delete(K id);
}
//EOF