/*
 * This file was last modified at 2020.04.14 20:47 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * UserLoginPartConverter.java
 * $Id$
 */

package su.svn.showcase.converters.impl;

import su.svn.showcase.converters.*;
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
    public UserLoginJdo convert(@Nonnull UserLogin entity, ReadyMap ready) {
        return doConvert(new UserLoginJdo(entity.getId()), entity, ready);
    }

    @Override
    public UserLogin convert(@Nonnull UserLoginJdo dto) {
        return doConvert(new UserLogin(dto.getId()), dto, new ReadyMap());
    }

    @Override
    public UserLogin convert(@Nonnull UserLoginJdo dto, ReadyMap ready) {
        return doConvert(new UserLogin(dto.getId()), dto, ready);
    }

    @Override
    UserRoleConverter getUserRoleConverter() {
        return userRoleConverter;
    }
}
