/*
 * This file was last modified at 2020.04.15 00:03 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * TagConverter.java
 * $Id$
 */

package su.svn.showcase.converters;

import su.svn.showcase.domain.Tag;
import su.svn.showcase.dto.jdo.TagJdo;

import javax.annotation.Nonnull;

public interface TagConverter extends EntityConverter<String, Tag, TagJdo> {

    class Updater {

        public static Tag update(@Nonnull Tag entity, @Nonnull TagJdo dto) {
            entity.setDateTime(dto.getDateTime());
            entity.setVisible(dto.getVisible());
            entity.setTag(dto.getTag());

            return entity;
        }
    }
}
