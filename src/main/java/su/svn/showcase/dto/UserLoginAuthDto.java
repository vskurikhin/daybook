/*
 * This file was last modified at 2020.03.10 22:56 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * UserLoginAuthDto.java
 * $Id$
 */

package su.svn.showcase.dto;

import java.util.Collection;

/**
 * The DTO of UserLogin is interface as a contract.
 *
 * @author Victor N. Skurikhin
 */
public interface UserLoginAuthDto extends UserLoginDto {
    Collection<UserRoleDto> getRoles();
}
//EOF
