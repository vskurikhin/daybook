/*
 * This file was last modified at 2020.02.09 20:08 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * UserLoginBaseDto.java
 * $Id$
 */

package su.svn.showcase.dto;

import lombok.*;
import su.svn.showcase.domain.UserLogin;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class UserLoginBaseDto extends UUIDDto implements UserLoginDto, Serializable {

    private static final long serialVersionUID = 9200L;

    @NotNull
    private LocalDateTime dateTime;

    @NotNull
    @Size(min = 1, max = 64, message = "Size of code cannot be greater than {max} Characters")
    private String login;

    @Size(max = 256, message = "Size of code cannot be greater than {max} Characters")
    private String password;

    @Builder
    public UserLoginBaseDto(@NotNull UUID id, @NotNull LocalDateTime dateTime, @NotNull String login, String password) {
        super(id);
        this.dateTime = dateTime;
        this.login = login;
        this.password = password;
    }

    public UserLoginBaseDto(@NotNull UserLogin userLogin) {
        super(Objects.requireNonNull(userLogin).getId());
        this.dateTime = userLogin.getDateTime();
        this.login = userLogin.getLogin();
        this.password = userLogin.getPassword();
    }

    @Override
    public Class<? extends Dto> getDtoClass() {
        return UserRoleBaseDto.class;
    }

    @Override
    public UserLogin update(@NotNull UserLogin entity) {
        Objects.requireNonNull(entity);
        entity.setId(getId());
        entity.setDateTime(this.dateTime);
        entity.setLogin(this.login);
        entity.setPassword(this.password);

        return entity;
    }
}
//EOF
