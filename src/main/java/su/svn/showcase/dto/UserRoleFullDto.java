/*
 * This file was last modified at 2020.02.15 14:30 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * UserRoleBaseDto.java$
 * $Id$
 */

package su.svn.showcase.dto;

import lombok.*;
import su.svn.showcase.domain.UserRole;

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

    @NotNull
    private LocalDateTime dateTime;

    @NotNull
    @Size(min = 1, max = 64)
    private String roleName;

    @NotNull
    private UserLoginDto userLogin;

    public UserRoleFullDto(@NotNull UserRole entity) {
        assert entity != null;
        this.id = entity.getId();
        this.roleName = entity.getRoleName();
        this.userLogin = new UserLoginBaseDto(entity.getUserLogin());
    }

    @Override
    public Class<? extends Dto> getDtoClass() {
        return UserRoleFullDto.class;
    }

    @Override
    public UserRole update(@NotNull UserRole entity) {
        assert entity != null;
        entity.setDateTime(this.dateTime);
        entity.setRoleName(this.roleName);
        assert this.userLogin != null;
        entity.setUserLogin(this.userLogin.update(entity.getUserLogin()));

        return entity;
    }
}
//EOF
