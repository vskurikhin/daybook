/*
 * This file was last modified at 2020.02.10 21:23 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * UserLoginDto.java
 * $Id$
 */

package su.svn.showcase.dto;

import su.svn.showcase.domain.UserLogin;
import su.svn.showcase.domain.UserRole;
import su.svn.showcase.interfaces.Updating;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.*;

/**
 * The DTO of UserLogin is interface as a contract.
 *
 * @author Victor N. Skurikhin
 */
public interface UserLoginDto extends Dto<UUID>, Updating<UserLogin> {

    LocalDateTime getDateTime();

    void setDateTime(LocalDateTime dateTime);

    String getLogin();

    void setLogin(String login);

    String getPassword();

    void setPassword(String password);

    default UserLogin update(@NotNull UserLogin entity, Map<String, Object> values) {
        Objects.requireNonNull(entity);
        convertListIfContainsKey(UserRole.class, values, "roles").ifPresent(entity::setRoles);

        return update(entity);
    }
}
//EOF