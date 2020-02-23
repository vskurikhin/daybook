/*
 * This file was last modified at 2020.02.18 11:22 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * UserRoleDaoJpa.java
 * $Id$
 */

package su.svn.showcase.dao.jpa;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import su.svn.showcase.dao.UserRoleDao;
import su.svn.showcase.domain.UserRole;

import javax.ejb.Stateless;
import javax.persistence.*;
import java.util.*;

import static su.svn.shared.Constants.Db.PERSISTENCE_UNIT_NAME;

/**
 * The UserRole DAO implementation.
 *
 * @author Victor N. Skurikhin
 */
@Stateless
public class UserRoleDaoJpa extends AbstractDaoJpa<UUID, UserRole> implements UserRoleDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserRoleDaoJpa.class);

    @PersistenceUnit(unitName = PERSISTENCE_UNIT_NAME)
    private EntityManagerFactory emf;

    /**
     * {@inheritDoc }
     */
    @Override
    public Optional<UserRole> findById(UUID id) {
        return abstractDaoFindById(id);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public Optional<UserRole> findWhereRole(String role) {
        return abstractDaoFindWhereField(UserRole.FIND_WHERE_ROLE, "role", role);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public List<UserRole> findAll() {
        return abstractDaoFindAll(UserRole.FIND_ALL);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public List<UserRole> fetchAllOrderByRoleAsc() {
        return abstractDaoFindAll(UserRole.FETCH_ALL_ORDER_BY_ROLE_ASC);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public List<UserRole> fetchAllOrderByRoleDesc() {
        return abstractDaoFindAll(UserRole.FETCH_ALL_ORDER_BY_ROLE_DESC);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public List<UserRole> findAllByIdIn(Iterable<UUID> uuids) {
        return abstractDaoFindAllWhereIn(UserRole.FIND_ALL_WHERE_ID_IN, "ids", uuids);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public List<UserRole> findAllWhereRole(String role) {
        return abstractDaoFindAllWhereField(UserRole.FIND_WHERE_ROLE, "role", role);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public List<UserRole> findAllByRoleIn(Iterable<String> roles) {
        return abstractDaoFindAllWhereIn(UserRole.FIND_ALL_WHERE_ROLE_IN, "roles", roles);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public List<UserRole> range(int start, int size) {
        return jpaRange(UserRole.RANGE, start, size);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public List<UserRole> rangeOrderByRoleAsc(int start, int size) {
        return jpaRange(UserRole.RANGE_ORDER_BY_ROLE_ASC, start, size);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public List<UserRole> rangeOrderByRoleDesc(int start, int size) {
        return jpaRange(UserRole.RANGE_ORDER_BY_ROLE_DESC, start, size);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public long count() {
        return abstractCount();
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public UserRole save(UserRole entity) {
        return abstractDaoSave(entity);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public Iterable<UserRole> saveAll(Iterable<UserRole> entities) {
        return abstractDaoSaveAll(entities);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void delete(UUID id) {
        abstractDaoDelete(id);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void deleteAll(Iterable<UserRole> entities) {
        abstractDaoDeleteAll(entities);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    EntityManager getEntityManager() {
        return this.emf.createEntityManager();
    }

    /**
     * {@inheritDoc }
     */
    @Override
    Logger getLogger() {
        return LOGGER;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public Class<UserRole> getEClass() {
        return UserRole.class;
    }
}
//EOF
