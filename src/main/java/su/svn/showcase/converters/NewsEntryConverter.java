/*
 * This file was last modified at 2020.04.15 00:03 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * NewsEntryConverter.java
 * $Id$
 */

package su.svn.showcase.converters;

import su.svn.showcase.domain.NewsEntry;
import su.svn.showcase.dto.jdo.NewsEntryJdo;

import javax.annotation.Nonnull;
import java.util.UUID;

public interface NewsEntryConverter extends EntityConverter<UUID, NewsEntry, NewsEntryJdo> {

    class Updater {

        public static NewsEntry update(@Nonnull NewsEntry entity, @Nonnull NewsEntryJdo dto) {
            entity.setDateTime(dto.getDateTime());
            entity.setTitle(dto.getTitle());
            entity.setContent(dto.getContent());

            return entity;
        }
    }
}
