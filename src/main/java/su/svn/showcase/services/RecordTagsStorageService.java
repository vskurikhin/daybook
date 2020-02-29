/*
 * This file was last modified at 2020.03.01 00:04 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * RecordTagsStorageService.java
 * $Id$
 */

package su.svn.showcase.services;

import su.svn.showcase.dto.RecordFullDto;
import su.svn.showcase.dto.TagBaseDto;

import javax.annotation.Nonnull;

public interface RecordTagsStorageService {
    void addTagsToRecord(@Nonnull RecordFullDto record, @Nonnull Iterable<TagBaseDto> tags);
}
//EOF
