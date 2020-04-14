/*
 * This file was last modified at 2020.04.14 22:15 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * UserOnlyLoginDto.java
 * $Id$
 */

package su.svn.showcase.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import su.svn.showcase.domain.UserLogin;

import javax.annotation.Nonnull;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * The base DTO of UserLogin.
 *
 * @author Victor N. Skurikhin
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserOnlyLoginDto implements UserLoginDto, Serializable {

    private static final long serialVersionUID = 9209L;

    @NotNull
    private UUID id;

    @NotNull
    @Size(min = 1, max = 64, message = "Size of code cannot be greater than {max} Characters")
    private String login;

    public UserOnlyLoginDto(@Nonnull UserLogin entity) {
        this.id = entity.getId();
        this.login = entity.getLogin();
    }

    @Override
    public Class<UserOnlyLoginDto> getDtoClass() {
        return UserOnlyLoginDto.class;
    }

    @Override
    public LocalDateTime getDateTime() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setDateTime(LocalDateTime dateTime) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getPassword() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setPassword(String password) {
        throw new UnsupportedOperationException();
    }
}
//EOF
