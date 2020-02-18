/*
 * This file was last modified at 2020.02.18 10:55 by Victor N. Skurikhin.
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
     * Retrieves all records of an entity type by the set of possible role values.
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
     * Retrieves all records in ordered mode.
     *
     * @return records of entity type by condition.
     */
    List<UserRole> findAllOrderByRoleAsc();

    /**
     * Retrieves all records in ordered mode.
     *
     * @return records of entity type by condition.
     */
    List<UserRole> findAllOrderByRoleDesc();

    /**
     * {@inheritDoc }
     */
    @Override
    List<UserRole> findAllByIdIn(Iterable<UUID> ids);

    /**
     * Retrieves all records where Role equals the value.
     *
     * @param role - the value.
     * @return records of entity by condition.
     */
    List<UserRole> findAllWhereRole(String role);

    /**
     * Retrieves all records of an entity type by the set of possible tag values.
     *
     * @param tags - possible values.
     * @return records of entity by condition.
     */
    List<UserRole> findAllByRoleIn(Iterable<String> tags);

    /**
     * Returns all instances of Tag type and falling within
     * the range specified in the records of the initial position and size.
     *
     * @param start - initial position.
     * @param size - size.
     * @return records of entity by condition.
     */
    List<UserRole> range(int start, int size);

    /**
     * Returns all instances of Record type from ordered query and
     * falling within the range specified in the arguments of the initial
     * position and size.
     *
     * @return records of entity type by condition.
     */
    List<UserRole> rangeOrderByRoleAsc(int start, int size);

    /**
     * Returns all instances of Record type from ordered query and
     * falling within the range specified in the arguments of the initial
     * position and size.
     *
     * @return records of entity type by condition.
     */
    List<UserRole> rangeOrderByRoleDesc(int start, int size);
}
//EOF
