/*
 * This file was last modified at 2020.04.14 20:12 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * RoleConverter.java
 * $Id$
 */

package su.svn.showcase.converters;

import su.svn.showcase.domain.Link;
import su.svn.showcase.domain.Role;
import su.svn.showcase.dto.jdo.LinkJdo;
import su.svn.showcase.dto.jdo.RoleJdo;
import su.svn.showcase.utils.FieldUtil;

import javax.annotation.Nonnull;
import java.util.UUID;

public interface RoleConverter extends EntityConverter<UUID, Role, RoleJdo> {

    class Updater {

        public static Link update(@Nonnull Link entity, @Nonnull LinkJdo dto) {
            FieldUtil.updateIfNotNull(entity::setDateTime, dto.getDateTime());
            FieldUtil.updateIfNotNull(entity::setVisible, dto.getVisible());
            FieldUtil.updateIfNotNull(entity::setLink, dto.getLink());

            return entity;
        }
    }
}
