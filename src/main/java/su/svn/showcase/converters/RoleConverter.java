/*
 * This file was last modified at 2020.04.15 00:03 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * RoleConverter.java
 * $Id$
 */

package su.svn.showcase.converters;

import su.svn.showcase.domain.Role;
import su.svn.showcase.dto.jdo.RoleJdo;

import javax.annotation.Nonnull;
import java.util.UUID;

public interface RoleConverter extends EntityConverter<UUID, Role, RoleJdo> {

    class Updater {

        public static Role update(@Nonnull Role entity, @Nonnull RoleJdo dto) {
            entity.setRoleName(dto.getRoleName());

            return entity;
        }
    }
}
