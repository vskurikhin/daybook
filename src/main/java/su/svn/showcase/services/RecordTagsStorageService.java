/*
 * This file was last modified at 2020.04.14 21:45 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * RecordTagsStorageService.java
 * $Id$
 */

package su.svn.showcase.services;

import su.svn.showcase.dto.jdo.RecordJdo;
import su.svn.showcase.dto.jdo.TagJdo;

import javax.annotation.Nonnull;

public interface RecordTagsStorageService {
    void addTagsToRecord(@Nonnull RecordJdo record, @Nonnull Iterable<TagJdo> tags);
}
//EOF
