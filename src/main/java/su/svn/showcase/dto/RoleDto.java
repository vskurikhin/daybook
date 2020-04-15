/*
 * This file was last modified at 2020.04.14 22:15 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * RoleDto.java
 * $Id$
 */

package su.svn.showcase.dto;

import java.util.UUID;

/**
 * The DTO of Role is interface as a contract.
 *
 * @author Victor N. Skurikhin
 */
public interface RoleDto extends Dto<UUID> {

    String getRoleName();

    void setRoleName(String roleName);
}
//EOF
