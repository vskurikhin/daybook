/*
 * This file was last modified at 2020.04.14 20:47 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * TagConverter.java
 * $Id$
 */

package su.svn.showcase.converters;

import su.svn.showcase.domain.Link;
import su.svn.showcase.domain.Tag;
import su.svn.showcase.dto.jdo.LinkJdo;
import su.svn.showcase.dto.jdo.TagJdo;
import su.svn.showcase.utils.FieldUtil;

import javax.annotation.Nonnull;

public interface TagConverter extends EntityConverter<String, Tag, TagJdo> {

    class Updater {

        public static Link update(@Nonnull Link entity, @Nonnull LinkJdo dto) {
            FieldUtil.updateIfNotNull(entity::setDateTime, dto.getDateTime());
            FieldUtil.updateIfNotNull(entity::setVisible, dto.getVisible());
            FieldUtil.updateIfNotNull(entity::setLink, dto.getLink());

            return entity;
        }
    }
}
