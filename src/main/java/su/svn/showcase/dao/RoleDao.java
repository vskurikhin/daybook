/*
 * This file was last modified at 2020.02.18 10:54 by Victor N. Skurikhin.
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
     * Retrieves all records of an entity type by the set of possible role values.
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
     * Retrieves all records in ordered mode.
     *
     * @return records of entity type by condition.
     */
    List<Role> findAllOrderByRoleAsc();

    /**
     * Retrieves all records in ordered mode.
     *
     * @return records of entity type by condition.
     */
    List<Role> findAllOrderByRoleDesc();

    /**
     * {@inheritDoc }
     */
    @Override
    List<Role> findAllByIdIn(Iterable<UUID> ids);


    /**
     * Retrieves all records where Role equals the value.
     *
     * @param role - the value.
     * @return records of entity by condition.
     */
    List<Role> findAllWhereRole(String role);

    /**
     * Retrieves all records of an entity type by the set of possible tag values.
     *
     * @param tags - possible values.
     * @return records of entity by condition.
     */
    List<Role> findAllByRoleIn(Iterable<String> roles);

    /**
     * Returns all instances of Tag type and falling within
     * the range specified in the records of the initial position and size.
     *
     * @param start - initial position.
     * @param size - size.
     * @return records of entity by condition.
     */
    List<Role> range(int start, int size);

    /**
     * Returns all instances of Record type from ordered query and
     * falling within the range specified in the arguments of the initial
     * position and size.
     *
     * @return records of entity type by condition.
     */
    List<Role> rangeOrderByRoleAsc(int start, int size);

    /**
     * Returns all instances of Record type from ordered query and
     * falling within the range specified in the arguments of the initial
     * position and size.
     *
     * @return records of entity type by condition.
     */
    List<Role> rangeOrderByRoleDesc(int start, int size);
}
//EOF
