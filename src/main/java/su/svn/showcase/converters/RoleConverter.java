/*
 * This file was last modified at 2020.04.01 15:09 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * RoleConverter.java
 * $Id$
 */

package su.svn.showcase.converters;

import su.svn.showcase.domain.Role;
import su.svn.showcase.dto.RoleFullDto;

import java.util.UUID;

public interface RoleConverter extends EntityConverter<UUID, Role, RoleFullDto> {
}
