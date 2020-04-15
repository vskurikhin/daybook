/*
 * This file was last modified at 2020.04.12 15:34 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * UserOnlyLoginConverter.java
 * $Id$
 */

package su.svn.showcase.converters.impl;

import su.svn.showcase.converters.UserLoginConverter;
import su.svn.showcase.domain.UserLogin;
import su.svn.showcase.dto.UserOnlyLoginDto;
import su.svn.showcase.utils.ReadyMap;

import javax.annotation.Nonnull;
import javax.ejb.Stateless;
import java.util.UUID;

@Stateless(name = "UserOnlyLoginConverter")
public class UserOnlyLoginConverter extends AbstractConverter<UUID, UserLogin, UserOnlyLoginDto>
       implements UserLoginConverter<UserOnlyLoginDto> {

    @Override
    public UserOnlyLoginDto convert(@Nonnull UserLogin entity) {
        return super.convertByGetter(new UserOnlyLoginDto(), entity);
    }

    @Override
    public UserOnlyLoginDto convert(@Nonnull UserLogin entity, ReadyMap ready) {
        return super.convertByGetter(new UserOnlyLoginDto(), entity);
    }

    @Override
    public UserLogin convert(@Nonnull UserOnlyLoginDto dto) {
        return super.convertBySetter(new UserLogin(dto.getId()), dto);
    }

    @Override
    public UserLogin convert(@Nonnull UserOnlyLoginDto dto, ReadyMap ready) {
        return super.convertBySetter(new UserLogin(dto.getId()), dto);
    }

    @Override
    Class<UserLogin> getEClass() {
        return UserLogin.class;
    }

    @Override
    Class<UserOnlyLoginDto> getDClass() {
        return UserOnlyLoginDto.class;
    }
}
