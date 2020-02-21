/*
 * This file was last modified at 2020.02.12 23:11 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * NewsGroupDao.java
 * $Id$
 */

package su.svn.showcase.dao;

import su.svn.showcase.domain.NewsGroup;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface NewsGroupDao extends Dao<UUID, NewsGroup> {

    /**
     * Retrieves the record of entity where group the value.
     *
     * @param group - the value.
     * @return record of entity by condition or empty.
     */
    Optional<NewsGroup> findWhereGroup(String group);

    /**
     * {@inheritDoc }
     */
    @Override
    List<NewsGroup> findAll();

    /**
     * Retrieves all records in ordered mode.
     *
     * @return records of entity type by condition.
     */
    List<NewsGroup> findAllOrderByGroupAsc();

    /**
     * Retrieves all records in ordered mode.
     *
     * @return records of entity type by condition.
     */
    List<NewsGroup> findAllOrderByGroupDesc();

    /**
     * {@inheritDoc }
     */
    @Override
    List<NewsGroup> findAllByIdIn(Iterable<UUID> ids);

    /**
     * Retrieves all records where group equals the value
     *
     * @param group - the value.
     * @return records of entity by condition.
     */
    List<NewsGroup> findAllWhereGroup(String group);

    /**
     * Returns all instances of Group type and falling within
     * the range specified in the records of the initial position and size.
     *
     * @param start - initial position.
     * @param size - size.
     * @return records of entity by condition.
     */
    List<NewsGroup> range(int start, int size);

    /**
     * Returns all instances of Group type from ordered query and
     * falling within the range specified in the arguments of the initial
     * position and size.
     *
     * @return records of entity type by condition.
     */
    List<NewsGroup> rangeOrderByGroupAsc(int start, int size);

    /**
     * Returns all instances of Group type from ordered query and
     * falling within the range specified in the arguments of the initial
     * position and size.
     *
     * @return records of entity type by condition.
     */
    List<NewsGroup> rangeOrderByGroupDesc(int start, int size);
}
