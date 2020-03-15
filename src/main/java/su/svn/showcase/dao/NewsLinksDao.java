/*
 * This file was last modified at 2020.03.15 17:19 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * NewsLinksDao.java
 * $Id$
 */

package su.svn.showcase.dao;

import su.svn.showcase.domain.NewsLinks;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface NewsLinksDao extends Dao<UUID, NewsLinks> {

    /**
     * Retrieves the record of entity where title the value.
     *
     * @param title - the value.
     * @return record of entity by condition or empty.
     */
    Optional<NewsLinks> findWhereTitle(String title);

    /**
     * {@inheritDoc }
     */
    @Override
    List<NewsLinks> findAll();

    /**
     * Retrieves all records in ordered mode.
     *
     * @return records of entity type by condition.
     */
    List<NewsLinks> findAllOrderByTitleAsc();

    /**
     * Retrieves all records in ordered mode.
     *
     * @return records of entity type by condition.
     */
    List<NewsLinks> findAllOrderByTitleDesc();

    /**
     * {@inheritDoc }
     */
    @Override
    List<NewsLinks> findAllByIdIn(Iterable<UUID> ids);

    /**
     * Retrieves all records where title like a value
     *
     * @param title - the value.
     * @return records of entity by condition.
     */
    List<NewsLinks> findAllWhereTitle(String title);

    /**
     * Returns all instances of NewsLinks type and falling within
     * the range specified in the records of the initial position and size.
     *
     * @param start - initial position.
     * @param size - size.
     * @return records of entity by condition.
     */
    List<NewsLinks> range(int start, int size);

    /**
     * Returns all instances of NewsLinks type from ordered query and
     * falling within the range specified in the arguments of the initial
     * position and size.
     *
     * @return records of entity type by condition.
     */
    List<NewsLinks> rangeOrderByTitleAsc(int start, int size);

    /**
     * Returns all instances of NewsLinks type from ordered query and
     * falling within the range specified in the arguments of the initial
     * position and size.
     *
     * @return records of entity type by condition.
     */
    List<NewsLinks> findAllOrderByTitleDesc(int start, int size);
}
