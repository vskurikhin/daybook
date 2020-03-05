/*
 * This file was last modified at 2020.03.01 16:57 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * UserOnlyLoginRoService.java
 * $Id$
 */

package su.svn.showcase.services;

import su.svn.showcase.dto.UserOnlyLoginBaseDto;

import java.util.Optional;
import java.util.UUID;

public interface UserOnlyLoginRoService extends CrudService<UUID, UserOnlyLoginBaseDto> {
    UserOnlyLoginBaseDto readByLogin(String login);
}
