/*
 * This file was last modified at 2020.02.10 21:22 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * RoleBaseDto.java
 * $Id$
 */

package su.svn.showcase.dto;

import lombok.*;
import su.svn.showcase.domain.Role;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * The base DTO of Role.
 *
 * @author Victor N. Skurikhin
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoleBaseDto implements RoleDto, Serializable {

    private static final long serialVersionUID = 9210L;

    @NotNull
    private UUID id;

    @NotNull
    @Size(min = 1, max = 64)
    private String roleName;

    public RoleBaseDto(@NotNull Role entity) {
        this.id = Objects.requireNonNull(entity).getId();
        this.roleName = entity.getRoleName();
    }

    @Override
    public Class<? extends Dto> getDtoClass() {
        return RoleBaseDto.class;
    }

    @Override
    public Role update(@NotNull Role entity) {
        Objects.requireNonNull(entity);
        entity.setId(getId());
        entity.setRoleName(this.roleName);

        return entity;
    }
}
//EOF
