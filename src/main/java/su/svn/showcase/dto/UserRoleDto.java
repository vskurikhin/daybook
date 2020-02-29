/*
 * This file was last modified at 2020.03.01 00:04 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * UserRoleDto.java
 * $Id$
 */

package su.svn.showcase.dto;

import su.svn.showcase.domain.Role;
import su.svn.showcase.domain.UserLogin;
import su.svn.showcase.domain.UserRole;
import su.svn.showcase.interfaces.Updating;

import javax.annotation.Nonnull;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

/**
 * The DTO of UserRole is interface as a contract.
 *
 * @author Victor N. Skurikhin
 */
public interface UserRoleDto extends Dto<UUID>, Updating<UserRole> {

    LocalDateTime getDateTime();

    void setDateTime(LocalDateTime dateTime);

    String getRoleName();

    void setRoleName(String roleName);

    UserRole update(@NotNull UserRole entity, UserLogin userLogin);

    default UserRole update(@Nonnull UserRole entity, @Nonnull Map<String, Object> values) {
        convertIfContainsKey(Role.class, values, "role").ifPresent(entity::setRole);
        convertIfContainsKey(UserLogin.class, values, "userLogin").ifPresent(entity::setUserLogin);

        return update(entity);
    }
}
//EOF
