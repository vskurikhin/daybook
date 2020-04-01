/*
 * This file was last modified at 2020.03.31 20:41 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * LinkDescriptionConverter.java
 * $Id$
 */

package su.svn.showcase.converters;

import su.svn.showcase.domain.LinkDescription;
import su.svn.showcase.dto.LinkDescriptionFullDto;

import java.util.UUID;

public interface LinkDescriptionConverter extends EntityConverter<UUID, LinkDescription, LinkDescriptionFullDto> {
}
