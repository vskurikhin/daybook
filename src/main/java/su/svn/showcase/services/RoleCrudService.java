/*
 * This file was last modified at 2020.04.14 20:12 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * RoleCrudService.java
 * $Id$
 */

package su.svn.showcase.services;

import su.svn.showcase.dto.jdo.RoleJdo;

import java.util.UUID;

public interface RoleCrudService extends CrudService<UUID, RoleJdo> {
}
