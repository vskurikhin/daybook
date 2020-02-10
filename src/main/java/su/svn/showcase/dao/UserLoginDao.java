/*
 * This file was last modified at 2020.02.09 23:39 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * UserLoginDao.java
 * $Id$
 */

package su.svn.showcase.dao;

import su.svn.showcase.domain.UserLogin;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * This class provides Data Access methods for UserLogin objects.
 *
 * @author Victor N. Skurikhin
 */
public interface UserLoginDao extends Dao<UUID, UserLogin> {

    /**
     * Retrieves all records of an entity type by the set of possible login values.
     *
     * @param login - the value.
     * @return record of entity by condition or empty.
     */
    Optional<UserLogin> findWhereLogin(String login);

    /**
     * {@inheritDoc }
     */
    @Override
    List<UserLogin> findAll();

    /**
     * {@inheritDoc }
     */
    @Override
    List<UserLogin> findAllByIdIn(Iterable<UUID> ids);

    /**
     * Retrieves all records where roles contains the value.
     *
     * @param name - the value.
     * @return records of entity by condition.
     */
    List<UserLogin> findAllInUserRoleByName(String name);
}
//EOF
