/*
 * This file was last modified at 2020.02.09 15:53 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * RoleDao.java
 * $Id$
 */

package su.svn.showcase.dao;

import su.svn.showcase.domain.Role;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * This class provides Data Access methods for Role objects.
 *
 * @author Victor N. Skurikhin
 */
public interface RoleDao extends Dao<UUID, Role> {

    /**
     * Retrieves all records where role equals the value.
     *
     * @param role - the value.
     * @return records of entity by condition.
     */
    Optional<Role> findWhereRole(String role);

    /**
     * {@inheritDoc }
     */
    @Override
    List<Role> findAll();

    /**
     * {@inheritDoc }
     */
    @Override
    List<Role> findAllByIdIn(Iterable<UUID> ids);
}
//EOF
