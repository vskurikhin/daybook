/*
 * This file was last modified at 2020.02.07 12:22 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * TagDao.java
 * $Id$
 */

package su.svn.showcase.dao;

import su.svn.showcase.domain.Tag;

import java.util.List;
import java.util.Optional;

public interface TagDao extends Dao<String, Tag> {
    @Override
    List<Tag> findAll();

    List<Tag> findAllWhereTag(String role);

    List<Tag> findAllByIdIn(Iterable<String> uuids);

    Optional<Tag> findWhereTag(String role);

    List<Tag> findAllByTagIn(Iterable<String> iterable);

    List<String> outerSection(Iterable<String> iterable);
}
//EOF
