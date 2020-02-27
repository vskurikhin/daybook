/*
 * This file was last modified at 2020.02.27 18:02 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * UserLoginDao.java
 * $Id$
 */

package su.svn.showcase.dao;

import su.svn.showcase.domain.UserLogin;
import su.svn.showcase.exceptions.ErrorCase;

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
     * Retrieves the record and fetch the related entities in a single query
     * instead of additional queries for each access of the object's lazy relationships
     * of entity by key.
     *
     * @param id - key.
     * @return record of entity by key.
     * @throws ErrorCase if {@code id} is {@literal null}.
     */
    Optional<UserLogin> fetchById(UUID id);

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

    /**
     * Returns all instances of Tag type and falling within
     * the range specified in the records of the initial position and size.
     *
     * @param start - initial position.
     * @param size - size.
     * @return records of entity by condition.
     */
    List<UserLogin> range(int start, int size);
}
//EOF
