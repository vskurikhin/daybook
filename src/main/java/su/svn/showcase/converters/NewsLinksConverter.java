/*
 * This file was last modified at 2020.04.15 00:03 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * NewsLinksConverter.java
 * $Id$
 */

package su.svn.showcase.converters;

import su.svn.showcase.domain.NewsLinks;
import su.svn.showcase.dto.jdo.NewsLinksJdo;

import javax.annotation.Nonnull;
import java.util.UUID;

public interface NewsLinksConverter extends EntityConverter<UUID, NewsLinks, NewsLinksJdo> {

    class Updater {

        public static NewsLinks update(@Nonnull NewsLinks entity, @Nonnull NewsLinksJdo dto) {
            entity.setDateTime(dto.getDateTime());
            entity.setTitle(dto.getTitle());

            return entity;
        }
    }
}
