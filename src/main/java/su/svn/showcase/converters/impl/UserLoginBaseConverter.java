/*
 * This file was last modified at 2020.04.14 20:47 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * UserLoginBaseConverter.java
 * $Id$
 */

package su.svn.showcase.converters.impl;

import su.svn.showcase.converters.UserLoginConverter;
import su.svn.showcase.domain.UserLogin;
import su.svn.showcase.dto.jdo.UserLoginJdo;
import su.svn.showcase.utils.ReadyMap;

import javax.annotation.Nonnull;
import javax.ejb.Stateless;
import java.util.UUID;

@Stateless(name = "UserLoginBaseConverter")
public class UserLoginBaseConverter extends AbstractConverter<UUID, UserLogin, UserLoginJdo>
       implements UserLoginConverter<UserLoginJdo> {

    @Override
    public UserLoginJdo convert(@Nonnull UserLogin entity) {
        return super.convertByGetter(new UserLoginJdo(), entity);
    }

    @Override
    public UserLoginJdo convert(@Nonnull UserLogin entity, ReadyMap ready) {
        return super.convertByGetter(new UserLoginJdo(), entity);
    }

    @Override
    public UserLogin convert(@Nonnull UserLoginJdo dto) {
        return super.convertBySetter(new UserLogin(dto.getId()), dto);
    }

    @Override
    public UserLogin convert(@Nonnull UserLoginJdo dto, ReadyMap ready) {
        return super.convertBySetter(new UserLogin(dto.getId()), dto);
    }

    @Override
    Class<UserLogin> getEClass() {
        return UserLogin.class;
    }

    @Override
    Class<UserLoginJdo> getDClass() {
        return UserLoginJdo.class;
    }
}
