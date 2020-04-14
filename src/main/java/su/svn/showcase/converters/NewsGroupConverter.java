/*
 * This file was last modified at 2020.04.15 00:03 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * NewsGroupConverter.java
 * $Id$
 */

package su.svn.showcase.converters;

import su.svn.showcase.domain.NewsGroup;
import su.svn.showcase.dto.jdo.NewsGroupJdo;

import javax.annotation.Nonnull;
import java.util.UUID;

public interface NewsGroupConverter extends EntityConverter<UUID, NewsGroup, NewsGroupJdo> {

    class Updater {

        public static NewsGroup update(@Nonnull NewsGroup entity, @Nonnull NewsGroupJdo dto) {
            entity.setDateTime(dto.getDateTime());
            entity.setGroup(dto.getGroup());

            return entity;
        }
    }
}
