/*
 * This file was last modified at 2020.04.01 22:50 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * UserOnlyLoginConverterImpl.java
 * $Id$
 */

package su.svn.showcase.converters.impl;

import su.svn.showcase.converters.UserLoginConverter;
import su.svn.showcase.domain.UserLogin;
import su.svn.showcase.dto.UserOnlyLoginBaseDto;
import su.svn.showcase.utils.ReadyMap;

import javax.annotation.Nonnull;
import javax.ejb.Stateless;
import java.util.UUID;

@Stateless(name = "userOnlyLoginConverter")
public class UserOnlyLoginConverterImpl extends AbstractConverter<UUID, UserLogin, UserOnlyLoginBaseDto>
       implements UserLoginConverter {

    @Override
    public UserOnlyLoginBaseDto convert(@Nonnull UserLogin entity) {
        return super.convertByGetter(new UserOnlyLoginBaseDto(), entity);
    }

    @Override
    public UserOnlyLoginBaseDto convert(@Nonnull UserLogin entity, ReadyMap ready) {
        return super.convertByGetter(new UserOnlyLoginBaseDto(), entity);
    }

    @Override
    public UserLogin convert(@Nonnull UserOnlyLoginBaseDto dto) {
        return super.convertBySetter(new UserLogin(dto.getId()), dto);
    }

    @Override
    public UserLogin convert(@Nonnull UserOnlyLoginBaseDto dto, ReadyMap ready) {
        return super.convertBySetter(new UserLogin(dto.getId()), dto);
    }

    @Override
    Class<UserLogin> getEClass() {
        return UserLogin.class;
    }

    @Override
    Class<UserOnlyLoginBaseDto> getDClass() {
        return UserOnlyLoginBaseDto.class;
    }
}
