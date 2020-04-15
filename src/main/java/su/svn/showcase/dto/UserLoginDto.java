/*
 * This file was last modified at 2020.04.14 21:45 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * UserLoginDto.java
 * $Id$
 */

package su.svn.showcase.dto;

import su.svn.showcase.domain.UserLogin;
import su.svn.showcase.domain.UserRole;
import su.svn.showcase.interfaces.Updating;

import javax.annotation.Nonnull;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

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

    default UserLogin update(@Nonnull UserLogin entity, @Nonnull Map<String, Object> values) {
        convertListIfContainsKey(UserRole.class, values, "roles").ifPresent(entity::setRoles);
        return update(entity);
    }
}
//EOF
