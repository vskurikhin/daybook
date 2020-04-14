/*
 * This file was last modified at 2020.04.15 00:03 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * RoleBaseConverter.java
 * $Id$
 */

package su.svn.showcase.converters.impl;

import su.svn.showcase.converters.AbstractConverter;
import su.svn.showcase.converters.RoleConverter;
import su.svn.showcase.domain.Role;
import su.svn.showcase.dto.jdo.RoleJdo;
import su.svn.showcase.utils.ReadyMap;

import javax.annotation.Nonnull;
import javax.ejb.Stateless;
import java.util.UUID;

@Stateless(name = "RoleBaseConverter")
public class RoleBaseConverter extends AbstractConverter<UUID, Role, RoleJdo>
       implements RoleConverter {

    @Override
    public RoleJdo convert(@Nonnull Role entity) {
        return super.convertByGetter(new RoleJdo(), entity);
    }

    @Override
    public RoleJdo convert(@Nonnull Role entity, ReadyMap ready) {
        return super.convertByGetter(new RoleJdo(), entity);
    }

    @Override
    public Role convert(@Nonnull RoleJdo dto) {
        return super.convertBySetter(new Role(dto.getId()), dto);
    }

    @Override
    public Role convert(@Nonnull RoleJdo dto, ReadyMap ready) {
        return super.convertBySetter(new Role(dto.getId()), dto);
    }

    @Override
    public Role update(@Nonnull Role entity, @Nonnull RoleJdo dto) {
        return super.convertBySetter(entity, dto);
    }

    @Override
    public Role update(@Nonnull Role entity, @Nonnull RoleJdo dto, @Nonnull ReadyMap ready) {
        return super.convertBySetter(entity, dto);
    }

    @Override
    protected Class<Role> getEClass() {
        return Role.class;
    }

    @Override
    protected Class<RoleJdo> getDClass() {
        return RoleJdo.class;
    }
}
//EOF
