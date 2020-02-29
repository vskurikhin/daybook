/*
 * This file was last modified at 2020.03.01 00:04 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * RoleDto.java
 * $Id$
 */

package su.svn.showcase.dto;

import su.svn.showcase.domain.Role;
import su.svn.showcase.interfaces.Updating;

import javax.annotation.Nonnull;
import java.util.Map;
import java.util.UUID;

/**
 * The DTO of Role is interface as a contract.
 *
 * @author Victor N. Skurikhin
 */
public interface RoleDto extends Dto<UUID>, Updating<Role> {

    String getRoleName();

    void setRoleName(String roleName);

    default Role update(@Nonnull Role entity, @Nonnull Map<String, Object> values) {
        return update(entity);
    }
}
//EOF
