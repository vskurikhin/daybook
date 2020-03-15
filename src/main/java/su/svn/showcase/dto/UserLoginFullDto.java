/*
 * This file was last modified at 2020.03.14 13:49 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * UserLoginFullDto.java
 * $Id$
 */

package su.svn.showcase.dto;

import lombok.*;
import su.svn.showcase.domain.UserLogin;
import su.svn.showcase.domain.UserRole;

import javax.annotation.Nonnull;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class UserLoginFullDto implements UserLoginAuthDto, Serializable {

    private static final long serialVersionUID = 9201L;

    @NotNull
    private UUID id;

    @NotNull
    private LocalDateTime dateTime;

    @NotNull
    @Size(min = 1, max = 64, message = "Size of code cannot be greater than {max} Characters")
    private String login;

    @Size(max = 256, message = "Size of code cannot be greater than {max} Characters")
    private String password;

    @NotNull
    @Valid
    private List<UserRoleDto> roles;

    public UserLoginFullDto(@NotNull UserLogin entity) {
        assert entity != null;
        this.id = entity.getId();
        this.dateTime = entity.getDateTime();
        this.login = entity.getLogin();
        this.password = entity.getPassword();
        this.roles = entity.getRoles().stream()
                .map(UserRoleBaseDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<UserRoleDto> getRoles() {
        return this.roles;
    }

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

    @Override
    public UserLogin update(@Nonnull UserLogin entity, @Nonnull Map<String, Object> values) {
        convertListIfContainsKey(UserRole.class, values, "roles").ifPresent(entity::setRoles);
        return update(entity);
    }

    @Override
    public Class<? extends Dto> getDtoClass() {
        return UserLoginFullDto.class;
    }
}
//EOF
