/*
 * This file was last modified at 2020.04.01 15:09 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * RoleBaseDto.java
 * $Id$
 */

package su.svn.showcase.dto;

import lombok.*;
import su.svn.showcase.domain.Role;

import javax.annotation.Nonnull;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import java.io.Serializable;
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

    @Size(min = 1, max = 64)
    private String roleName;

    public RoleBaseDto(@Nonnull Role entity) {
        this.id = entity.getId();
        this.roleName = entity.getRoleName();
    }

    @Override
    public Class<RoleBaseDto> getDtoClass() {
        return RoleBaseDto.class;
    }

    @Override
    public Role update(@Nonnull Role entity) {
        updateIfNotNull(entity::setRoleName, this.roleName);
        return entity;
    }
}
//EOF
