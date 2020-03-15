/*
 * This file was last modified at 2020.03.14 13:49 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * UserLoginAuthDto.java
 * $Id$
 */

package su.svn.showcase.dto;

/**
 * The DTO of UserLogin is interface as a contract.
 *
 * @author Victor N. Skurikhin
 */
public interface UserLoginAuthDto extends UserLoginDto {
    Iterable<UserRoleDto> getRoles();
}
//EOF
