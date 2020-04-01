/*
 * This file was last modified at 2020.04.01 13:25 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * NewsGroupConverter.java
 * $Id$
 */

package su.svn.showcase.converters;

import su.svn.showcase.domain.NewsGroup;
import su.svn.showcase.dto.NewsGroupFullDto;

import java.util.UUID;

public interface NewsGroupConverter extends EntityConverter<UUID, NewsGroup, NewsGroupFullDto> {
}
