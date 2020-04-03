/*
 * This file was last modified at 2020.04.01 22:50 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * RoleBaseConverterImpl.java
 * $Id$
 */

package su.svn.showcase.converters.impl;

import su.svn.showcase.converters.RoleConverter;
import su.svn.showcase.domain.Role;
import su.svn.showcase.dto.RoleFullDto;
import su.svn.showcase.utils.ReadyMap;

import javax.annotation.Nonnull;
import javax.ejb.Stateless;
import java.util.UUID;

@Stateless(name = "RoleBaseConverter")
public class RoleBaseConverterImpl extends AbstractConverter<UUID, Role, RoleFullDto>
       implements RoleConverter {

    @Override
    public RoleFullDto convert(@Nonnull Role entity) {
        return super.convertByGetter(new RoleFullDto(), entity);
    }

    @Override
    public RoleFullDto convert(@Nonnull Role entity, ReadyMap ready) {
        return super.convertByGetter(new RoleFullDto(), entity);
    }

    @Override
    public Role convert(@Nonnull RoleFullDto dto) {
        return super.convertBySetter(new Role(dto.getId()), dto);
    }

    @Override
    public Role convert(@Nonnull RoleFullDto dto, ReadyMap ready) {
        return super.convertBySetter(new Role(dto.getId()), dto);
    }

    @Override
    Class<Role> getEClass() {
        return Role.class;
    }

    @Override
    Class<RoleFullDto> getDClass() {
        return RoleFullDto.class;
    }
}
