/*
 * This file was last modified at 2020.04.01 15:09 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * UserRoleFullConverterImpl.java
 * $Id$
 */

package su.svn.showcase.converters.impl;

import su.svn.showcase.converters.RoleConverter;
import su.svn.showcase.converters.UserRoleConverter;
import su.svn.showcase.converters.LinkConverter;
import su.svn.showcase.converters.RecordConverter;
import su.svn.showcase.domain.UserRole;
import su.svn.showcase.dto.UserRoleFullDto;
import su.svn.showcase.dto.LinkFullDto;
import su.svn.showcase.dto.RecordFullDto;
import su.svn.showcase.utils.ReadyMap;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.UUID;

@Named("articleFullConverter")
public class UserRoleFullConverterImpl extends AbstractConverter<UUID, UserRole, UserRoleFullDto>
       implements UserRoleConverter {

    @Inject
    @Named("roleBaseConverter")
    private RoleConverter roleConverter;

    @Override
    public UserRoleFullDto convert(@Nonnull UserRole entity) {
        return doConvert(new UserRoleFullDto(), entity, new ReadyMap());
    }

    @Override
    public UserRoleFullDto convert(@Nonnull UserRole entity, ReadyMap ready) {
        return doConvert(new UserRoleFullDto(), entity, ready);
    }

    private UserRoleFullDto doConvert(UserRoleFullDto dto, UserRole entity, ReadyMap ready) {
        if (entity.getRole() != null) {
            dto.setRole(convertUuid(entity.getRole(), ready, roleConverter::convert));
        }
        if (entity.getUserLogin() != null) {
            // TODO
        }
        return super.convertByGetter(dto, entity);
    }

    @Override
    public UserRole convert(@Nonnull UserRoleFullDto dto) {
        return doConvert(new UserRole(dto.getId()), dto, new ReadyMap());
    }

    @Override
    public UserRole convert(@Nonnull UserRoleFullDto dto, ReadyMap ready) {
        return doConvert(new UserRole(dto.getId()), dto, ready);
    }

    private UserRole doConvert(UserRole entity, UserRoleFullDto dto, ReadyMap ready) {
        if (dto.getRole() != null) {
            // TODO
        }
        if (dto.getUserLogin() != null) {
            // TODO
        }
        return super.convertBySetter(entity, dto);
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
