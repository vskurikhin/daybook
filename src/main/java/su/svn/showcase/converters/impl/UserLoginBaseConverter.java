/*
 * This file was last modified at 2020.04.12 15:34 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * UserLoginBaseConverter.java
 * $Id$
 */

package su.svn.showcase.converters.impl;

import su.svn.showcase.converters.UserLoginConverter;
import su.svn.showcase.domain.UserLogin;
import su.svn.showcase.dto.UserLoginFullDto;
import su.svn.showcase.utils.ReadyMap;

import javax.annotation.Nonnull;
import javax.ejb.Stateless;
import java.util.UUID;

@Stateless(name = "UserLoginBaseConverter")
public class UserLoginBaseConverter extends AbstractConverter<UUID, UserLogin, UserLoginFullDto>
       implements UserLoginConverter<UserLoginFullDto> {

    @Override
    public UserLoginFullDto convert(@Nonnull UserLogin entity) {
        return super.convertByGetter(new UserLoginFullDto(), entity);
    }

    @Override
    public UserLoginFullDto convert(@Nonnull UserLogin entity, ReadyMap ready) {
        return super.convertByGetter(new UserLoginFullDto(), entity);
    }

    @Override
    public UserLogin convert(@Nonnull UserLoginFullDto dto) {
        return super.convertBySetter(new UserLogin(dto.getId()), dto);
    }

    @Override
    public UserLogin convert(@Nonnull UserLoginFullDto dto, ReadyMap ready) {
        return super.convertBySetter(new UserLogin(dto.getId()), dto);
    }

    @Override
    Class<UserLogin> getEClass() {
        return UserLogin.class;
    }

    @Override
    Class<UserLoginFullDto> getDClass() {
        return UserLoginFullDto.class;
    }
}
