/*
 * This file was last modified at 2020.02.09 20:09 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * UserRoleDto.java
 * $Id$
 */

package su.svn.showcase.dto;

import su.svn.showcase.domain.UserLogin;
import su.svn.showcase.domain.UserRole;
import su.svn.showcase.interfaces.Updating;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public interface UserRoleDto extends Dto<UUID>, Updating<UserRole> {

    LocalDateTime getDateTime();

    void setDateTime(LocalDateTime dateTime);

    String getRoleName();

    void setRoleName(String roleName);

    default UserRole update(@NotNull UserRole entity, Map<String, Object> values) {
        Objects.requireNonNull(entity);
        convertIfContainsKey(UserLogin.class, values, "userLogin").ifPresent(entity::setUserLogin);

        return update(entity);
    }
}
//EOF
