/*
 * This file was last modified at 2020.04.15 22:24 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * UserRoleAbstractConverter.java
 * $Id$
 */

package su.svn.showcase.converters.user;

import su.svn.showcase.converters.AbstractConverter;
import su.svn.showcase.converters.RoleConverter;
import su.svn.showcase.converters.UserOnlyLoginConverter;
import su.svn.showcase.domain.UserRole;
import su.svn.showcase.dto.UserOnlyLoginDto;
import su.svn.showcase.dto.jdo.UserRoleJdo;
import su.svn.showcase.dto.jdo.RoleJdo;
import su.svn.showcase.exceptions.ErrorCase;
import su.svn.showcase.utils.ReadyMap;

import java.util.UUID;
import java.util.function.Consumer;

abstract class UserRoleAbstractConverter extends AbstractConverter<UUID, UserRole, UserRoleJdo> {

    abstract RoleConverter getRoleConverter();

    abstract UserOnlyLoginConverter getUserLoginConverter();

    UserRoleJdo doConvert(UserRoleJdo dto, UserRole entity, ReadyMap ready) {
        ReadyMap.Key key = new ReadyMap.UuidKey(dto.getId(), UserRoleJdo.class);
        if (ready.containsKey(key)) {
            Object value = ready.get(key);
            if (value instanceof UserRoleJdo) {
                return (UserRoleJdo) value;
            }
            throw ErrorCase.badType(value.getClass().getSimpleName());
        } else {
            ready.put(dto);
        }
        if (entity.getRole() != null) {
            dto.setRole(getRoleConverter().convert(entity.getRole(), ready));
        }
        if (entity.getUserLogin() != null) {
            dto.setUserLogin(getUserLoginConverter().convert(entity.getUserLogin(), ready));
        }
        return super.convertByGetter(dto, entity);
    }

    UserRole doConvert(UserRole entity, UserRoleJdo dto, ReadyMap ready) {
        ReadyMap.Key key = new ReadyMap.UuidKey(entity.getId(), UserRole.class);
        if (ready.containsKey(key)) {
            Object value = ready.get(key);
            if (value instanceof UserRole) {
                return (UserRole) value;
            }
            throw ErrorCase.badType(value.getClass().getSimpleName());
        } else {
            ready.put(entity);
        }
        if (dto.getRole() != null) {
            entity.setRole(getRoleConverter().convert((RoleJdo) dto.getRole(), ready));
        }
        if (dto.getUserLogin() != null) {
            entity.setUserLogin(getUserLoginConverter().convert((UserOnlyLoginDto) dto.getUserLogin(), ready));
        }
        return super.convertBySetter(entity, dto);
    }

    public <T> void updateIfNotNull(Consumer<T> consumer, T o) {
        if (o != null) consumer.accept(o);
    }

    @Override
    protected Class<UserRole> getEClass() {
        return UserRole.class;
    }

    @Override
    protected Class<UserRoleJdo> getDClass() {
        return UserRoleJdo.class;
    }
}
//EOF
