/*
 * This file was last modified at 2020.04.15 00:03 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * UserRoleConverter.java
 * $Id$
 */

package su.svn.showcase.converters;

import su.svn.showcase.domain.UserRole;
import su.svn.showcase.dto.jdo.UserRoleJdo;

import javax.annotation.Nonnull;
import java.util.UUID;

public interface UserRoleConverter extends EntityConverter<UUID, UserRole, UserRoleJdo> {

    class Updater {

        public static UserRole update(@Nonnull UserRole entity, @Nonnull UserRoleJdo dto) {
            entity.setDateTime(dto.getDateTime());
            entity.setRoleName(dto.getRoleName());

            return entity;
        }
    }
}
