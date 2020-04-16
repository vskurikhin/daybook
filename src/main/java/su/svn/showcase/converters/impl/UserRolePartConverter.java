/*
 * This file was last modified at 2020.04.15 00:03 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * UserRolePartConverter.java
 * $Id$
 */

package su.svn.showcase.converters.impl;

import su.svn.showcase.converters.RoleConverter;
import su.svn.showcase.converters.UserRoleConverter;
import su.svn.showcase.domain.UserRole;
import su.svn.showcase.dto.jdo.UserRoleJdo;
import su.svn.showcase.utils.ReadyMap;

import javax.annotation.Nonnull;
import javax.ejb.EJB;
import javax.ejb.Stateless;

@Stateless(name = "UserRolePartConverter")
public class UserRolePartConverter extends UserRoleAbstractConverter implements UserRoleConverter {

    @EJB(beanName = "RoleBaseConverter")
    private RoleConverter roleConverter;

    @EJB(beanName = "UserLoginBaseConverter")
    private UserLoginBaseConverter userLoginBaseConverter;

    @Override
    public UserRoleJdo convert(@Nonnull UserRole entity) {
        return doConvert(new UserRoleJdo(entity.getId()), entity, new ReadyMap());
    }

    @Override
    public UserRoleJdo convert(@Nonnull UserRole entity, @Nonnull ReadyMap ready) {
        return doConvert(new UserRoleJdo(entity.getId()), entity, ready);
    }

    @Override
    public UserRole convert(@Nonnull UserRoleJdo dto) {
        return doConvert(new UserRole(dto.getId()), dto, new ReadyMap());
    }

    @Override
    public UserRole convert(@Nonnull UserRoleJdo dto, @Nonnull ReadyMap ready) {
        return doConvert(new UserRole(dto.getId()), dto, ready);
    }

    @Override
    public UserRole update(@Nonnull UserRole entity, @Nonnull UserRoleJdo dto) {
        return doConvert(entity, dto, new ReadyMap());
    }

    @Override
    public UserRole update(@Nonnull UserRole entity, @Nonnull UserRoleJdo dto, @Nonnull ReadyMap ready) {
        return doConvert(entity, dto, ready);
    }

    @Override
    RoleConverter getRoleConverter() {
        return roleConverter;
    }

    @Override
    UserLoginBaseConverter getUserLoginConverter() {
        return userLoginBaseConverter;
    }
}
//EOF
