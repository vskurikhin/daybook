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
import java.util.UUID;

public interface NewsGroupDao extends Dao<UUID, NewsGroup> {

    /**
     * Retrieves all records where group equals the value
     *
     * @param group - the value.
     * @return records of entity by condition.
     */
    List<NewsGroup> findAllWhereGroup(String group);
}
