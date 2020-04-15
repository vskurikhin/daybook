/*
 * This file was last modified at 2020.04.10 21:25 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * UserOnlyLoginRoService.java
 * $Id$
 */

package su.svn.showcase.services;

import su.svn.showcase.dto.UserOnlyLoginDto;

import java.util.UUID;

public interface UserOnlyLoginRoService extends CrudService<UUID, UserOnlyLoginDto> {
    UserOnlyLoginDto readByLogin(String login);
}
