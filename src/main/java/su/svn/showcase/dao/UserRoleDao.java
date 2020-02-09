/*
 * This file was last modified at 2020.02.09 15:54 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * UserRoleDao.java
 * $Id$
 */

package su.svn.showcase.dao;

import su.svn.showcase.domain.UserRole;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * This class provides Data Access methods for UserRole objects.
 *
 * @author Victor N. Skurikhin
 */
public interface UserRoleDao extends Dao<UUID, UserRole> {

    /**
     * Retrieves all records where role equals the value.
     *
     * @param role - the value.
     * @return records of entity by condition.
     */
    Optional<UserRole> findWhereRole(String role);

    /**
     * {@inheritDoc }
     */
    @Override
    List<UserRole> findAll();

    /**
     * {@inheritDoc }
     */
    @Override
    List<UserRole> findAllByIdIn(Iterable<UUID> ids);
}
//EOF
