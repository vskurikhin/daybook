/*
 * This file was last modified at 2020.02.09 13:36 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * TagDao.java
 * $Id$
 */

package su.svn.showcase.dao;

import su.svn.showcase.domain.Tag;

import java.util.List;
import java.util.Optional;

/**
 * This class provides Data Access methods for Tag objects
 */
public interface TagDao extends Dao<String, Tag> {

    /**
     * Retrieves the record of entity where tag equals the value.
     *
     * @param tag - the value.
     * @return record of entity by condition or empty.
     */
    Optional<Tag> findWhereTag(String tag);

    /**
     * {@inheritDoc }
     */
    @Override
    List<Tag> findAll();

    /**
     * Retrieves all records where tag equals the value
     *
     * @param tag - the value.
     * @return records of entity by condition.
     */
    List<Tag> findAllWhereTag(String tag);

    /**
     * Retrieves all records of entity by the key and his possible values.
     *
     * @param ids - possible values.
     * @return records of entity by condition.
     */
    List<Tag> findAllByIdIn(Iterable<String> ids);

    /**
     * Retrieves all records of entity by the tag and his possible values.
     *
     * @param tags - possible values.
     * @return records of entity by condition.
     */
    List<Tag> findAllByTagIn(Iterable<String> tags);

    /**
     * Retrieves all entity records where tags are not in the input set.
     *
     * @param tags - the input set.
     * @return records of entity by condition.
     */
    List<String> outerSection(Iterable<String> tags);
}
//EOF
