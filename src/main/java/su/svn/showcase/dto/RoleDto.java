/*
 * This file was last modified at 2020.02.09 20:07 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * RoleDto.java
 * $Id$
 */

package su.svn.showcase.dto;

import su.svn.showcase.domain.Role;
import su.svn.showcase.interfaces.Updating;

import javax.validation.constraints.NotNull;
import java.util.Map;
import java.util.UUID;

public interface RoleDto extends Dto<UUID>, Updating<Role> {

    String getRoleName();

    void setRoleName(String roleName);

    default Role update(@NotNull Role entity, Map<String, Object> values) {
        return update(entity);
    }
}
//EOF
