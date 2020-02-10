/*
 * This file was last modified at 2020.02.09 20:08 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * UserRoleBaseDto.java
 * $Id$
 */

package su.svn.showcase.dto;

import lombok.*;
import su.svn.showcase.domain.UserRole;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class UserRoleBaseDto extends UUIDDto implements UserRoleDto, Serializable {

    private static final long serialVersionUID = 9210L;

    @NotNull
    private LocalDateTime dateTime;

    @NotNull
    @Size(min = 1, max = 64)
    private String roleName;

    @Builder
    public UserRoleBaseDto(@NotNull UUID id, @NotNull LocalDateTime dateTime, @NotNull String roleName) {
        super(id);
        this.dateTime = dateTime;
        this.roleName = roleName;
    }

    public UserRoleBaseDto(@NotNull UserRole userRole) {
        super(Objects.requireNonNull(userRole).getId());
        this.dateTime = userRole.getDateTime();
        this.roleName = userRole.getRoleName();
    }

    @Override
    public Class<? extends Dto> getDtoClass() {
        return UserRoleBaseDto.class;
    }

    @Override
    public UserRole update(@NotNull UserRole entity) {
        Objects.requireNonNull(entity);
        entity.setId(getId());
        entity.setDateTime(getDateTime());
        entity.setRoleName(getRoleName());

        return entity;
    }
}
//EOF
