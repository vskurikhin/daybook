/*
 * This file was last modified at 2020.02.24 22:08 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * RecordTagsStorageService.java
 * $Id$
 */

package su.svn.showcase.services;

import su.svn.showcase.dto.RecordFullDto;
import su.svn.showcase.dto.TagBaseDto;

public interface RecordTagsStorageService {
    void addTagsToRecord(RecordFullDto record, Iterable<TagBaseDto> tags);
}
