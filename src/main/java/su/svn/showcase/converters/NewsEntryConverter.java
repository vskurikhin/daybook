/*
 * This file was last modified at 2020.04.14 16:50 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * NewsEntryConverter.java
 * $Id$
 */

package su.svn.showcase.converters;

import su.svn.showcase.domain.NewsEntry;
import su.svn.showcase.dto.jdo.NewsEntryJdo;
import su.svn.showcase.utils.FieldUtil;

import javax.annotation.Nonnull;
import java.util.UUID;

public interface NewsEntryConverter extends EntityConverter<UUID, NewsEntry, NewsEntryJdo> {

    class Updater {

        public static NewsEntry update(@Nonnull NewsEntry entity, @Nonnull NewsEntryJdo dto) {
            FieldUtil.updateIfNotNull(entity::setDateTime, dto.getDateTime());
            FieldUtil.updateIfNotNull(entity::setTitle, dto.getTitle());
            entity.setContent(dto.getContent());

            return entity;
        }
    }
}
