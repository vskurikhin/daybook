/*
 * This file was last modified at 2020.04.14 21:45 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * UserRoleBaseConverter.java
 * $Id$
 */

package su.svn.showcase.converters.impl;

import su.svn.showcase.converters.UserRoleConverter;
import su.svn.showcase.domain.UserRole;
import su.svn.showcase.dto.jdo.UserRoleJdo;
import su.svn.showcase.utils.ReadyMap;

import javax.annotation.Nonnull;
import javax.ejb.Stateless;
import java.util.UUID;

@Stateless(name = "UserRoleBaseConverter")
public class UserRoleBaseConverter extends AbstractConverter<UUID, UserRole, UserRoleJdo>
       implements UserRoleConverter {

    @Override
    public UserRoleJdo convert(@Nonnull UserRole entity) {
        return super.convertByGetter(new UserRoleJdo(), entity);
    }

    @Override
    public UserRoleJdo convert(@Nonnull UserRole entity, ReadyMap ready) {
        return super.convertByGetter(new UserRoleJdo(), entity);
    }

    @Override
    public UserRole convert(@Nonnull UserRoleJdo dto) {
        return super.convertBySetter(new UserRole(dto.getId()), dto);
    }

    @Override
    public UserRole convert(@Nonnull UserRoleJdo dto, ReadyMap ready) {
        return super.convertBySetter(new UserRole(dto.getId()), dto);
    }

    @Override
    Class<UserRole> getEClass() {
        return UserRole.class;
    }

    @Override
    Class<UserRoleJdo> getDClass() {
        return UserRoleJdo.class;
    }
}
//EOF