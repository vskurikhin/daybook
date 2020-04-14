/*
 * This file was last modified at 2020.04.14 21:45 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * UserRoleJdo.java
 * $Id$
 */

package su.svn.showcase.dto.jdo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import su.svn.showcase.domain.Role;
import su.svn.showcase.domain.UserLogin;
import su.svn.showcase.domain.UserRole;
import su.svn.showcase.dto.RoleDto;
import su.svn.showcase.dto.UserLoginDto;
import su.svn.showcase.dto.UserOnlyLoginDto;
import su.svn.showcase.dto.UserRoleDto;

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
public class UserRoleJdo implements UserRoleDto, Serializable {

    private static final long serialVersionUID = 9210L;

    @NotNull
    private UUID id;

    private RoleDto role;

    private LocalDateTime dateTime;

    @Size(min = 1, max = 64)
    private String roleName;

    private UserLoginDto userLogin;

    public UserRoleJdo(@Nonnull UUID id) {
        this.id = id;
    }

    @Override
    public Class<UserRoleJdo> getDtoClass() {
        return UserRoleJdo.class;
    }

    @Deprecated
    public UserRoleJdo(@Nonnull UserRole entity) {
        this.id = entity.getId();
        this.role = new RoleJdo(entity.getRole());
        this.dateTime = entity.getDateTime();
        this.roleName = entity.getRoleName();
        this.userLogin = new UserOnlyLoginDto(entity.getUserLogin());
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
