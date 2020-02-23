/*
 * This file was last modified at 2020.02.22 11:03 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * TagNewsEntryStorageService.java
 * $Id$
 */

package su.svn.showcase.services;

import su.svn.showcase.dto.NewsEntryFullDto;
import su.svn.showcase.dto.TagBaseDto;

public interface NewsEntryTagsStorageService {
    void addTagsToNews(NewsEntryFullDto newsEntity, Iterable<TagBaseDto> tags);
}
