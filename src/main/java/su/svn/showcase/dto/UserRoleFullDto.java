/*
 * This file was last modified at 2020.04.14 20:12 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * UserRoleFullDto.java
 * $Id$
 */

package su.svn.showcase.dto;

import lombok.*;
import su.svn.showcase.domain.Role;
import su.svn.showcase.domain.UserLogin;
import su.svn.showcase.domain.UserRole;
import su.svn.showcase.dto.jdo.RoleJdo;

import javax.annotation.Nonnull;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * The base DTO of UserRole.
 *
 * @author Victor N. Skurikhin
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRoleFullDto implements UserRoleDto, Serializable {

    private static final long serialVersionUID = 9210L;

    @NotNull
    private UUID id;

    private RoleDto role;

    private LocalDateTime dateTime;

    @Size(min = 1, max = 64)
    private String roleName;

    private UserLoginDto userLogin;

    public UserRoleFullDto(@Nonnull UUID id) {
        this.id = id;
    }

    @Deprecated
    public UserRoleFullDto(@Nonnull UserRole entity) {
        this.id = entity.getId();
        this.role = new RoleJdo(entity.getRole());
        this.dateTime = entity.getDateTime();
        this.roleName = entity.getRoleName();
        this.userLogin = new UserOnlyLoginDto(entity.getUserLogin());
    }

    @Deprecated
    @Override
    public Class<UserRoleFullDto> getDtoClass() {
        return UserRoleFullDto.class;
    }

    @Deprecated
    @Override
    public UserRole update(@Nonnull UserRole entity) {
        if (this.role != null) {
            entity.setRole(this.role.update(new Role(this.role.getId())));
        }
        updateIfNotNull(entity::setDateTime, this.dateTime);
        updateIfNotNull(entity::setRoleName, this.roleName);
        assert this.userLogin != null;
        assert entity.getUserLogin() != null;
        UserLogin userLogin = new UserLogin(this.userLogin.getId());
        if ( ! (this.userLogin instanceof UserOnlyLoginDto)) {
            entity.setUserLogin(this.userLogin.update(userLogin));
        }

        return entity;
    }

    @Deprecated
    @Override
    public UserRole update(@NotNull UserRole entity, UserLogin userLogin) {
        return update(entity);
    }
}
//EOF
