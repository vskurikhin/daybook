/*
 * This file was last modified at 2020.02.16 00:13 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * NewsEntryDao.java$
 * $Id$
 */

package su.svn.showcase.dao;

import su.svn.showcase.domain.NewsEntry;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface NewsEntryDao extends Dao<UUID, NewsEntry> {


    /**
     * Retrieves the record of entity where title the value.
     *
     * @param title - the value.
     * @return record of entity by condition or empty.
     */
    Optional<NewsEntry> findWhereTitle(String title);

    /**
     * {@inheritDoc }
     */
    @Override
    List<NewsEntry> findAll();

    /**
     * Retrieves all records in ordered mode.
     *
     * @return records of entity type by condition.
     */
    List<NewsEntry> findAllOrderByTitleAsc();

    /**
     * Retrieves all records in ordered mode.
     *
     * @return records of entity type by condition.
     */
    List<NewsEntry> findAllOrderByTitleDesc();

    /**
     * {@inheritDoc }
     */
    @Override
    List<NewsEntry> findAllByIdIn(Iterable<UUID> ids);

    /**
     * Retrieves all records where title like a value
     *
     * @param title - the value.
     * @return records of entity by condition.
     */
    List<NewsEntry> findAllWhereTitle(String title);

    /**
     * Returns all instances of NewsEntry type and falling within
     * the range specified in the records of the initial position and size.
     *
     * @param start - initial position.
     * @param size - size.
     * @return records of entity by condition.
     */
    List<NewsEntry> range(int start, int size);

    /**
     * Returns all instances of NewsEntry type from ordered query and
     * falling within the range specified in the arguments of the initial
     * position and size.
     *
     * @return records of entity type by condition.
     */
    List<NewsEntry> rangeOrderByTitleAsc(int start, int size);

    /**
     * Returns all instances of NewsEntry type from ordered query and
     * falling within the range specified in the arguments of the initial
     * position and size.
     *
     * @return records of entity type by condition.
     */
    List<NewsEntry> findAllOrderByTitleDesc(int start, int size);
}
