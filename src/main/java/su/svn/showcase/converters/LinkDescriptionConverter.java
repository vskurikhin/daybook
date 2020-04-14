/*
 * This file was last modified at 2020.04.15 00:03 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * LinkDescriptionConverter.java
 * $Id$
 */

package su.svn.showcase.converters;

import su.svn.showcase.domain.LinkDescription;
import su.svn.showcase.dto.jdo.LinkDescriptionJdo;

import javax.annotation.Nonnull;
import java.util.UUID;

public interface LinkDescriptionConverter extends EntityConverter<UUID, LinkDescription, LinkDescriptionJdo> {

    class Updater {

        public static LinkDescription update(@Nonnull LinkDescription entity, @Nonnull LinkDescriptionJdo dto) {
            entity.setDateTime(dto.getDateTime());
            entity.setDescription(dto.getDescription());
            entity.setDetails(dto.getDetails());

            return entity;
        }
    }
}
