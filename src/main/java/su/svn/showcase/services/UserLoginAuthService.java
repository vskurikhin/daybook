/*
 * This file was last modified at 2020.03.10 22:57 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * UserLoginAuthService.java
 * $Id$
 */

package su.svn.showcase.services;

import su.svn.showcase.dto.UserLoginAuthDto;

import java.util.UUID;

public interface UserLoginAuthService extends CrudService<UUID, UserLoginAuthDto> {
    UserLoginAuthDto readByLogin(String login);
}
