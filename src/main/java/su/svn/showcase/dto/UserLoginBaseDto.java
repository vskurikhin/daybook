/*
 * This file was last modified at 2020.02.24 20:09 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * UserLoginBaseDto.java
 * $Id$
 */

package su.svn.showcase.dto;

import lombok.*;
import su.svn.showcase.domain.UserLogin;
import su.svn.showcase.exceptions.ErrorCase;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;

/**
 * The base DTO of UserLogin.
 *
 * @author Victor N. Skurikhin
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginBaseDto implements UserLoginDto, Serializable {

    private static final long serialVersionUID = 9200L;

    @NotNull
    private UUID id;

    @NotNull
    private LocalDateTime dateTime;

    @NotNull
    @Size(min = 1, max = 64, message = "Size of code cannot be greater than {max} Characters")
    private String login;

    @Size(max = 256, message = "Size of code cannot be greater than {max} Characters")
    private String password;

    public UserLoginBaseDto(@NotNull UserLogin entity) {
        assert entity != null;
        this.id = entity.getId();
        this.dateTime = entity.getDateTime();
        this.login = entity.getLogin();
        this.password = entity.getPassword();
    }

    @Override
    public Class<? extends Dto> getDtoClass() {
        return UserRoleFullDto.class;
    }

    @Override
    public UserLogin update(@NotNull UserLogin entity) {
        assert entity != null;
        entity.setDateTime(this.dateTime);
        entity.setLogin(this.login);
        entity.setPassword(this.password);

        return entity;
    }
}
//EOF
