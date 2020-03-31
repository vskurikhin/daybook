/*
 * This file was last modified at 2020.03.31 20:21 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * NewsLinksConverter.java
 * $Id$
 */

package su.svn.showcase.converters;

import su.svn.showcase.domain.NewsLinks;
import su.svn.showcase.dto.NewsLinksFullDto;

import java.util.UUID;

public interface NewsLinksConverter extends EntityConverter<UUID, NewsLinks, NewsLinksFullDto> {
}
