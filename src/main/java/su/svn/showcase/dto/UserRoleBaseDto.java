/*
 * This file was last modified at 2020.03.14 20:15 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * UserRoleBaseDto.java
 * $Id$
 */

package su.svn.showcase.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import su.svn.showcase.domain.Role;
import su.svn.showcase.domain.UserLogin;
import su.svn.showcase.domain.UserRole;

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
public class UserRoleBaseDto implements UserRoleDto, Serializable {

    private static final long serialVersionUID = 9210L;

    @NotNull
    private UUID id;

    private LocalDateTime dateTime;

    @Size(min = 1, max = 64)
    private String roleName;

    public UserRoleBaseDto(@Nonnull UserRole entity) {
        assert entity != null;
        this.id = entity.getId();
        this.dateTime = entity.getDateTime();
        this.roleName = entity.getRoleName();
    }

    @Override
    public Class<? extends Dto> getDtoClass() {
        return UserRoleBaseDto.class;
    }

    @Override
    public UserRole update(@Nonnull UserRole entity) {
        updateIfNotNull(entity::setDateTime, this.dateTime);
        updateIfNotNull(entity::setRoleName, this.roleName);

        return entity;
    }

    @Override
    public UserRole update(@NotNull UserRole entity, UserLogin userLogin) {
        return update(entity);
    }
}
//EOF
