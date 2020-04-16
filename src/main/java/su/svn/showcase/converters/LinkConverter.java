/*
 * This file was last modified at 2020.04.15 00:03 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * LinkConverter.java
 * $Id$
 */

package su.svn.showcase.converters;

import su.svn.showcase.domain.Link;
import su.svn.showcase.dto.jdo.LinkJdo;

import javax.annotation.Nonnull;
import java.util.UUID;

public interface LinkConverter extends EntityConverter<UUID, Link, LinkJdo> {

    class Updater {

        public static Link update(@Nonnull Link entity, @Nonnull LinkJdo dto) {
            entity.setDateTime(dto.getDateTime());
            entity.setVisible(dto.getVisible());
            entity.setLink(dto.getLink());

            return entity;
        }
    }
}
