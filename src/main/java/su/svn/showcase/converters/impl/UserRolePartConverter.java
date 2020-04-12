/*
 * This file was last modified at 2020.04.12 15:34 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * UserRolePartConverter.java
 * $Id$
 */

package su.svn.showcase.converters.impl;

import su.svn.showcase.converters.RoleConverter;
import su.svn.showcase.converters.UserRoleConverter;
import su.svn.showcase.converters.UserRoleConverter;
import su.svn.showcase.domain.UserRole;
import su.svn.showcase.dto.UserRoleFullDto;
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
    public UserRoleFullDto convert(@Nonnull UserRole entity) {
        return doConvert(new UserRoleFullDto(entity.getId()), entity, new ReadyMap());
    }

    @Override
    public UserRoleFullDto convert(@Nonnull UserRole entity, ReadyMap ready) {
        return doConvert(new UserRoleFullDto(entity.getId()), entity, ready);
    }

    @Override
    public UserRole convert(@Nonnull UserRoleFullDto dto) {
        return doConvert(new UserRole(dto.getId()), dto, new ReadyMap());
    }

    @Override
    public UserRole convert(@Nonnull UserRoleFullDto dto, ReadyMap ready) {
        return doConvert(new UserRole(dto.getId()), dto, ready);
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
