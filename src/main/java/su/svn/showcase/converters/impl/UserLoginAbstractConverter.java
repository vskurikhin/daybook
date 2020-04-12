/*
 * This file was last modified at 2020.04.12 15:34 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * UserLoginAbstractConverter.java
 * $Id$
 */

package su.svn.showcase.converters.impl;

import su.svn.showcase.converters.UserRoleConverter;
import su.svn.showcase.domain.UserLogin;
import su.svn.showcase.domain.UserRole;
import su.svn.showcase.dto.UserLoginFullDto;
import su.svn.showcase.dto.UserRoleDto;
import su.svn.showcase.dto.UserRoleFullDto;
import su.svn.showcase.exceptions.ErrorCase;
import su.svn.showcase.utils.ReadyMap;

import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

abstract class UserLoginAbstractConverter extends AbstractConverter<UUID, UserLogin, UserLoginFullDto> {

    abstract UserRoleConverter getUserRoleConverter();

    UserLoginFullDto doConvert(UserLoginFullDto dto, UserLogin entity, ReadyMap ready) {
        ReadyMap.Key key = new ReadyMap.UuidKey(dto.getId(), UserLoginFullDto.class);
        if (ready.containsKey(key)) {
            Object value = ready.get(key);
            if (value instanceof UserLoginFullDto) {
                return (UserLoginFullDto) value;
            }
            throw ErrorCase.badType(value.getClass().getSimpleName());
        } else {
            ready.put(dto);
        }
        if (entity.getRoles() != null) {
            List<UserRoleDto> set = entity.getRoles().stream()
                    .map(functionUserRoleToDto(ready))
                    .collect(Collectors.toList());
            dto.setRoles(set);
        }
        return super.convertByGetter(dto, entity);
    }

    private Function<UserRole, UserRoleFullDto> functionUserRoleToDto(ReadyMap ready) {
        return entity -> getUserRoleConverter().convert(entity, ready);
    }

    UserLogin doConvert(UserLogin entity, UserLoginFullDto dto, ReadyMap ready) {
        ReadyMap.Key key = new ReadyMap.UuidKey(entity.getId(), UserLogin.class);
        if (ready.containsKey(key)) {
            Object value = ready.get(key);
            if (value instanceof UserLogin) {
                return (UserLogin) value;
            }
            throw ErrorCase.badType(value.getClass().getSimpleName());
        } else {
            ready.put(entity);
        }
        if (dto.getRoles() != null) {
            List<UserRole> set = dto.getRoles().stream()
                    .map(functionUserRoleDtoToEntity(ready))
                    .collect(Collectors.toList());
            entity.setRoles(set);
        }
        return super.convertBySetter(entity, dto);
    }

    private Function<UserRoleDto, UserRole> functionUserRoleDtoToEntity(ReadyMap ready) {
        return dto -> getUserRoleConverter().convert((UserRoleFullDto) dto, ready);
    }

    public <T> void updateIfNotNull(Consumer<T> consumer, T o) {
        if (o != null) consumer.accept(o);
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
