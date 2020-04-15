/*
 * This file was last modified at 2020.04.14 21:45 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * UserLoginJdo.java
 * $Id$
 */

package su.svn.showcase.dto.jdo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import su.svn.showcase.domain.UserLogin;
import su.svn.showcase.domain.UserRole;
import su.svn.showcase.dto.UserLoginAuthDto;
import su.svn.showcase.dto.UserRoleDto;

import javax.annotation.Nonnull;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class UserLoginJdo implements UserLoginAuthDto, Serializable {

    private static final long serialVersionUID = 9201L;

    @NotNull
    private UUID id;

    private LocalDateTime dateTime;

    @Size(min = 1, max = 64, message = "Size of code cannot be greater than {max} Characters")
    private String login;

    @Size(max = 256, message = "Size of code cannot be greater than {max} Characters")
    private String password;

    @Valid
    private List<UserRoleDto> roles;

    public UserLoginJdo(@Nonnull UUID id) {
        this.id = id;
    }

    @Deprecated
    public UserLoginJdo(@Nonnull UserLogin entity) {
        this.id = entity.getId();
        this.dateTime = entity.getDateTime();
        this.login = entity.getLogin();
        this.password = entity.getPassword();
        this.roles = entity.getRoles().stream()
                .map(UserRoleJdo::new)
                .collect(Collectors.toList());
    }

    @Deprecated
    @Override
    public UserLogin update(@Nonnull UserLogin entity) {
        updateIfNotNull(entity::setDateTime, this.dateTime);
        updateIfNotNull(entity::setLogin, this.login);
        updateIfNotNull(entity::setPassword, this.password);
        if (this.roles != null) {
            List<UserRole> userRoles = this.roles.stream()
                    .map(dto -> dto.update(new UserRole(dto.getId())))
                    .collect(Collectors.toList());
            entity.setRoles(userRoles);
        } else {
            entity.setRoles(Collections.emptyList());
        }
        return entity;
    }

    @Deprecated
    @Override
    public UserLogin update(@Nonnull UserLogin entity, @Nonnull Map<String, Object> values) {
        convertListIfContainsKey(UserRole.class, values, "roles").ifPresent(entity::setRoles);
        return update(entity);
    }

    @Deprecated
    @Override
    public Class<UserLoginJdo> getDtoClass() {
        return UserLoginJdo.class;
    }
}
//EOF
