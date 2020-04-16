/*
 * This file was last modified at 2020.04.15 00:03 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * UserLoginPartConverter.java
 * $Id$
 */

package su.svn.showcase.converters.impl;

import su.svn.showcase.converters.UserLoginConverter;
import su.svn.showcase.converters.UserRoleConverter;
import su.svn.showcase.domain.UserLogin;
import su.svn.showcase.dto.jdo.UserLoginJdo;
import su.svn.showcase.utils.ReadyMap;

import javax.annotation.Nonnull;
import javax.ejb.EJB;
import javax.ejb.Stateless;

@Stateless(name = "UserLoginPartConverter")
public class UserLoginPartConverter extends UserLoginAbstractConverter implements UserLoginConverter<UserLoginJdo> {

    @EJB(beanName = "UserRoleBaseConverter")
    private UserRoleConverter userRoleConverter;

    @Override
    public UserLoginJdo convert(@Nonnull UserLogin entity) {
        return doConvert(new UserLoginJdo(entity.getId()), entity, new ReadyMap());
    }

    @Override
    public UserLoginJdo convert(@Nonnull UserLogin entity, @Nonnull ReadyMap ready) {
        return doConvert(new UserLoginJdo(entity.getId()), entity, ready);
    }

    @Override
    public UserLogin convert(@Nonnull UserLoginJdo dto) {
        return doConvert(new UserLogin(dto.getId()), dto, new ReadyMap());
    }

    @Override
    public UserLogin convert(@Nonnull UserLoginJdo dto, @Nonnull ReadyMap ready) {
        return doConvert(new UserLogin(dto.getId()), dto, ready);
    }

    @Override
    public UserLogin update(@Nonnull UserLogin entity, @Nonnull UserLoginJdo dto) {
        return doConvert(entity, dto, new ReadyMap());
    }

    @Override
    public UserLogin update(@Nonnull UserLogin entity, @Nonnull UserLoginJdo dto, @Nonnull ReadyMap ready) {
        return doConvert(entity, dto, ready);
    }

    @Override
    UserRoleConverter getUserRoleConverter() {
        return userRoleConverter;
    }
}
//EOF
