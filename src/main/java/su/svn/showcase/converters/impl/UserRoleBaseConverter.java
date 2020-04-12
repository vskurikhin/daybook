/*
 * This file was last modified at 2020.04.12 15:34 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * UserRoleBaseConverter.java
 * $Id$
 */

package su.svn.showcase.converters.impl;

import su.svn.showcase.converters.UserRoleConverter;
import su.svn.showcase.domain.UserRole;
import su.svn.showcase.dto.UserRoleFullDto;
import su.svn.showcase.utils.ReadyMap;

import javax.annotation.Nonnull;
import javax.ejb.Stateless;
import java.util.UUID;

@Stateless(name = "UserRoleBaseConverter")
public class UserRoleBaseConverter extends AbstractConverter<UUID, UserRole, UserRoleFullDto>
       implements UserRoleConverter {

    @Override
    public UserRoleFullDto convert(@Nonnull UserRole entity) {
        return super.convertByGetter(new UserRoleFullDto(), entity);
    }

    @Override
    public UserRoleFullDto convert(@Nonnull UserRole entity, ReadyMap ready) {
        return super.convertByGetter(new UserRoleFullDto(), entity);
    }

    @Override
    public UserRole convert(@Nonnull UserRoleFullDto dto) {
        return super.convertBySetter(new UserRole(dto.getId()), dto);
    }

    @Override
    public UserRole convert(@Nonnull UserRoleFullDto dto, ReadyMap ready) {
        return super.convertBySetter(new UserRole(dto.getId()), dto);
    }

    @Override
    Class<UserRole> getEClass() {
        return UserRole.class;
    }

    @Override
    Class<UserRoleFullDto> getDClass() {
        return UserRoleFullDto.class;
    }
}
//EOF