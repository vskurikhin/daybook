/*
 * This file was last modified at 2020.03.31 20:28 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * LinkConverter.java
 * $Id$
 */

package su.svn.showcase.converters;

import su.svn.showcase.domain.Link;
import su.svn.showcase.dto.LinkFullDto;

import java.util.UUID;

public interface LinkConverter extends EntityConverter<UUID, Link, LinkFullDto> {
}
