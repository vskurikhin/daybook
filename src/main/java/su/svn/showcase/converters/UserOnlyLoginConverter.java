/*
 * This file was last modified at 2020.04.15 22:24 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * UserOnlyLoginConverter.java
 * $Id$
 */

package su.svn.showcase.converters;

import su.svn.showcase.domain.UserLogin;
import su.svn.showcase.dto.UserOnlyLoginDto;

import java.util.UUID;

public interface UserOnlyLoginConverter extends EntityConverter<UUID, UserLogin, UserOnlyLoginDto> {
}
