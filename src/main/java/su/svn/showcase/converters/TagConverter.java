/*
 * This file was last modified at 2020.04.08 20:43 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * TagConverter.java
 * $Id$
 */

package su.svn.showcase.converters;

import su.svn.showcase.domain.Link;
import su.svn.showcase.domain.Tag;
import su.svn.showcase.dto.LinkFullDto;
import su.svn.showcase.dto.TagFullDto;
import su.svn.showcase.utils.FieldUtil;

import javax.annotation.Nonnull;

public interface TagConverter extends EntityConverter<String, Tag, TagFullDto> {

    class Updater {

        public static Link update(@Nonnull Link entity, @Nonnull LinkFullDto dto) {
            FieldUtil.updateIfNotNull(entity::setDateTime, dto.getDateTime());
            FieldUtil.updateIfNotNull(entity::setVisible, dto.getVisible());
            FieldUtil.updateIfNotNull(entity::setLink, dto.getLink());

            return entity;
        }
    }
}
