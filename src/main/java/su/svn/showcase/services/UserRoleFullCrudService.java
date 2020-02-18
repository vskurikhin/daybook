/*
 * This file was last modified at 2020.02.17 22:38 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * UserRoleFullCrudService.java
 * $Id$
 */

package su.svn.showcase.services;

import su.svn.showcase.dto.RoleBaseDto;
import su.svn.showcase.dto.UserRoleFullDto;

import java.util.UUID;

public interface UserRoleFullCrudService extends CrudService<UUID, UserRoleFullDto> {
}
