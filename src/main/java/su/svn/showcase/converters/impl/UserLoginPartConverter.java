/*
 * This file was last modified at 2020.04.12 15:34 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * UserLoginPartConverter.java
 * $Id$
 */

package su.svn.showcase.converters.impl;

import su.svn.showcase.converters.*;
import su.svn.showcase.domain.UserLogin;
import su.svn.showcase.dto.UserLoginFullDto;
import su.svn.showcase.utils.ReadyMap;

import javax.annotation.Nonnull;
import javax.ejb.EJB;
import javax.ejb.Stateless;

@Stateless(name = "UserLoginPartConverter")
public class UserLoginPartConverter extends UserLoginAbstractConverter implements UserLoginConverter<UserLoginFullDto> {

    @EJB(beanName = "UserRoleBaseConverter")
    private UserRoleConverter userRoleConverter;

    @Override
    public UserLoginFullDto convert(@Nonnull UserLogin entity) {
        return doConvert(new UserLoginFullDto(entity.getId()), entity, new ReadyMap());
    }

    @Override
    public UserLoginFullDto convert(@Nonnull UserLogin entity, ReadyMap ready) {
        return doConvert(new UserLoginFullDto(entity.getId()), entity, ready);
    }

    @Override
    public UserLogin convert(@Nonnull UserLoginFullDto dto) {
        return doConvert(new UserLogin(dto.getId()), dto, new ReadyMap());
    }

    @Override
    public UserLogin convert(@Nonnull UserLoginFullDto dto, ReadyMap ready) {
        return doConvert(new UserLogin(dto.getId()), dto, ready);
    }

    @Override
    UserRoleConverter getUserRoleConverter() {
        return userRoleConverter;
    }
}
