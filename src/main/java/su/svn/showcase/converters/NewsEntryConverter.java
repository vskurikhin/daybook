/*
 * This file was last modified at 2020.04.06 22:03 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * NewsEntryConverter.java
 * $Id$
 */

package su.svn.showcase.converters;

import su.svn.showcase.domain.NewsEntry;
import su.svn.showcase.dto.NewsEntryFullDto;
import su.svn.showcase.utils.FieldUtil;

import javax.annotation.Nonnull;
import java.util.UUID;

public interface NewsEntryConverter extends EntityConverter<UUID, NewsEntry, NewsEntryFullDto> {

    class Updater {

        public static NewsEntry update(@Nonnull NewsEntry entity, @Nonnull NewsEntryFullDto dto) {
            FieldUtil.updateIfNotNull(entity::setDateTime, dto.getDateTime());
            FieldUtil.updateIfNotNull(entity::setTitle, dto.getTitle());
            entity.setContent(dto.getContent());

            return entity;
        }
    }
}
