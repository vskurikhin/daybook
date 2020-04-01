/*
 * This file was last modified at 2020.04.01 15:09 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * UserRoleConverter.java
 * $Id$
 */

package su.svn.showcase.converters;

import su.svn.showcase.domain.UserRole;
import su.svn.showcase.dto.UserRoleFullDto;

import java.util.UUID;

public interface UserRoleConverter extends EntityConverter<UUID, UserRole, UserRoleFullDto> {
}
