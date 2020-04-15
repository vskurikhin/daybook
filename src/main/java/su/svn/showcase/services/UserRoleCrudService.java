/*
 * This file was last modified at 2020.04.14 21:45 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * UserRoleCrudService.java
 * $Id$
 */

package su.svn.showcase.services;

import su.svn.showcase.dto.jdo.UserRoleJdo;

import java.util.UUID;

public interface UserRoleCrudService extends CrudService<UUID, UserRoleJdo> {
}
