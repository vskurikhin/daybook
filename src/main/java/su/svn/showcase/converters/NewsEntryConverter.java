/*
 * This file was last modified at 2020.03.31 20:05 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * NewsEntryConverter.java
 * $Id$
 */

package su.svn.showcase.converters;

import su.svn.showcase.domain.NewsEntry;
import su.svn.showcase.dto.NewsEntryFullDto;

import java.util.UUID;

public interface NewsEntryConverter extends EntityConverter<UUID, NewsEntry, NewsEntryFullDto> {
}
