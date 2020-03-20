/*
 * This file was last modified at 2020.03.20 19:15 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * ArticleDao.java
 * $Id$
 */

package su.svn.showcase.dao;

import su.svn.showcase.domain.Article;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ArticleDao extends Dao<UUID, Article> {

    /**
     * Retrieves the record of entity where title the value.
     *
     * @param title - the value.
     * @return record of entity by condition or empty.
     */
    Optional<Article> findWhereTitle(String title);

    /**
     * {@inheritDoc }
     */
    @Override
    List<Article> findAll();

    /**
     * Retrieves all records in ordered mode.
     *
     * @return records of entity type by condition.
     */
    List<Article> findAllOrderByTitleAsc();

    /**
     * Retrieves all records in ordered mode.
     *
     * @return records of entity type by condition.
     */
    List<Article> findAllOrderByTitleDesc();

    /**
     * {@inheritDoc }
     */
    @Override
    List<Article> findAllByIdIn(Iterable<UUID> ids);

    /**
     * Retrieves all records where title like a value
     *
     * @param title - the value.
     * @return records of entity by condition.
     */
    List<Article> findAllWhereTitle(String title);

    /**
     * Returns all instances of Article type and falling within
     * the range specified in the records of the initial position and size.
     *
     * @param start - initial position.
     * @param size - size.
     * @return records of entity by condition.
     */
    List<Article> range(int start, int size);

    /**
     * Returns all instances of Article type from ordered query and
     * falling within the range specified in the arguments of the initial
     * position and size.
     *
     * @return records of entity type by condition.
     */
    List<Article> rangeOrderByTitleAsc(int start, int size);

    /**
     * Returns all instances of Article type from ordered query and
     * falling within the range specified in the arguments of the initial
     * position and size.
     *
     * @return records of entity type by condition.
     */
    List<Article> findAllOrderByTitleDesc(int start, int size);
}
