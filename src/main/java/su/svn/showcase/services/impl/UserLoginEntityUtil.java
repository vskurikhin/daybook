/*
 * This file was last modified at 2020.03.03 22:49 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * UserLoginEntityUtil.java
 * $Id$
 */

package su.svn.showcase.services.impl;

import su.svn.showcase.domain.UserLogin;
import su.svn.showcase.dto.UserLoginDto;

import javax.persistence.EntityManager;

public class UserLoginEntityUtil {

    public static UserLogin get(EntityManager em, UserLoginDto dto) {
        if (dto.getLogin() == null) {
            return em.find(UserLogin.class, dto.getId());
        }
        return em.createNamedQuery(UserLogin.FIND_WHERE_LOGIN, UserLogin.class)
                .setParameter("login", dto.getLogin())
                .getSingleResult();
    }
}
