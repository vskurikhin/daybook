/*
 * This file was last modified at 2020.02.27 18:02 by Victor N. Skurikhin.
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

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;
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

    default UserRole update(@NotNull UserRole entity, Map<String, Object> values) {
        assert entity != null;
        assert values != null;
        convertIfContainsKey(Role.class, values, "role").ifPresent(entity::setRole);
        convertIfContainsKey(UserLogin.class, values, "userLogin").ifPresent(entity::setUserLogin);

        return update(entity);
    }
}
//EOF
