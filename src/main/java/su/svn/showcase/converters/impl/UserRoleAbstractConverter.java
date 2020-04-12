/*
 * This file was last modified at 2020.04.12 15:34 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * UserRoleAbstractConverter.java
 * $Id$
 */

package su.svn.showcase.converters.impl;

import su.svn.showcase.converters.RoleConverter;
import su.svn.showcase.domain.UserRole;
import su.svn.showcase.dto.*;
import su.svn.showcase.dto.UserRoleFullDto;
import su.svn.showcase.exceptions.ErrorCase;
import su.svn.showcase.utils.ReadyMap;

import java.util.UUID;
import java.util.function.Consumer;

abstract class UserRoleAbstractConverter extends AbstractConverter<UUID, UserRole, UserRoleFullDto> {

    abstract RoleConverter getRoleConverter();

    abstract UserLoginBaseConverter getUserLoginConverter();

    UserRoleFullDto doConvert(UserRoleFullDto dto, UserRole entity, ReadyMap ready) {
        ReadyMap.Key key = new ReadyMap.UuidKey(dto.getId(), UserRoleFullDto.class);
        if (ready.containsKey(key)) {
            Object value = ready.get(key);
            if (value instanceof UserRoleFullDto) {
                return (UserRoleFullDto) value;
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

    UserRole doConvert(UserRole entity, UserRoleFullDto dto, ReadyMap ready) {
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
            entity.setRole(getRoleConverter().convert((RoleFullDto) dto.getRole(), ready));
        }
        if (dto.getUserLogin() != null) {
            entity.setUserLogin(getUserLoginConverter().convert((UserLoginFullDto) dto.getUserLogin(), ready));
        }
        return super.convertBySetter(entity, dto);
    }

    public <T> void updateIfNotNull(Consumer<T> consumer, T o) {
        if (o != null) consumer.accept(o);
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
