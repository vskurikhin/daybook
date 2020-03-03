/*
 * This file was last modified at 2020.03.03 22:49 by Victor N. Skurikhin.
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
     * @throws IllegalArgumentException if the first argument does
     *         not denote an entity type or the second argument is
     *         is not a valid type for that entitys primary key or
     *         is null
     */
    @Override
    public Optional<UserRole> findById(UUID id) {
        return jpaFindById(id);
    }

    /**
     * {@inheritDoc }
     * @throws IllegalArgumentException if a query has not been
     *         defined with the given name or if the query string is
     *         found to be invalid or if the query result is found to
     *         not be assignable to the specified type or if called for a Java
     *         Persistence query language UPDATE or DELETE statement
     * @throws QueryTimeoutException if the query execution exceeds
     *         the query timeout value set and only the statement is
     *         rolled back
     * @throws TransactionRequiredException if a lock mode has
     *         been set and there is no transaction
     * @throws PessimisticLockException if pessimistic locking
     *         fails and the transaction is rolled back
     * @throws LockTimeoutException if pessimistic locking
     *         fails and only the statement is rolled back
     * @throws PersistenceException if the query execution exceeds
     *         the query timeout value set and the transaction
     *         is rolled back
     */
    @Override
    public Optional<UserRole> findWhereRole(String role) {
        return jpaFindWhereField(UserRole.FIND_WHERE_ROLE, "role", role);
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
     * @param entity must not be {@literal null}.
     * @throws IllegalArgumentException if the instance is not an
     *          entity
     * @throws TransactionRequiredException if invoked on a
     *         container-managed entity manager of type
     *         <code>PersistenceContextType.TRANSACTION</code> and there is
     *         no transaction
     * @throws PersistenceException if the flush fails
     */
    @Override
    public UserRole save(UserRole entity) {
        return jpaDaoSave(entity);
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
