/*
 * This file was last modified at 2020.03.21 19:38 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * LinkDao.java
 * $Id$
 */

package su.svn.showcase.dao;

import su.svn.showcase.domain.Link;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LinkDao extends Dao<UUID, Link> {

    /**
     * {@inheritDoc }
     */
    @Override
    List<Link> findAll();

    /**
     * {@inheritDoc }
     */
    @Override
    List<Link> findAllByIdIn(Iterable<UUID> ids);

    /**
     * Returns all instances of Link type and falling within
     * the range specified in the records of the initial position and size.
     *
     * @param start - initial position.
     * @param size - size.
     * @return records of entity by condition.
     */
    List<Link> range(int start, int size);
}
