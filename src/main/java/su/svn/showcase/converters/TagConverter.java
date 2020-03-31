/*
 * This file was last modified at 2020.03.31 20:05 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * TagConverter.java
 * $Id$
 */

package su.svn.showcase.converters;

import su.svn.showcase.domain.Tag;
import su.svn.showcase.dto.TagFullDto;

public interface TagConverter extends EntityConverter<String, Tag, TagFullDto> {
}
