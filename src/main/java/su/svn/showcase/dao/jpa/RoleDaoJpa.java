/*
 * This file was last modified at 2020.03.03 20:33 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * RoleDaoJpa.java
 * $Id$
 */

package su.svn.showcase.dao.jpa;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import su.svn.showcase.dao.RoleDao;
import su.svn.showcase.domain.Role;

import javax.ejb.Stateless;
import javax.persistence.*;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static su.svn.shared.Constants.Db.PERSISTENCE_UNIT_NAME;

/**
 * The Role DAO implementation.
 *
 * @author Victor N. Skurikhin
 */
@Stateless
public class RoleDaoJpa extends AbstractDaoJpa<UUID, Role> implements RoleDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(RoleDaoJpa.class);

    @PersistenceContext(unitName = PERSISTENCE_UNIT_NAME)
    private EntityManager entityManager;

    /**
     * {@inheritDoc }
     * @throws IllegalArgumentException if the first argument does
     *         not denote an entity type or the second argument is
     *         is not a valid type for that entitys primary key or
     *         is null
     */
    @Override
    public Optional<Role> findById(UUID id) {
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
    public Optional<Role> findWhereRole(String role) {
        return jpaFindWhereField(Role.FIND_WHERE_ROLE, "role", role);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public List<Role> findAll() {
        return abstractDaoFindAll(Role.FIND_ALL);
    }

    @Override
    public List<Role> findAllOrderByRoleAsc() {
        return abstractDaoFindAll(Role.FIND_ALL_ORDER_BY_ROLE_ASC);
    }

    @Override
    public List<Role> findAllOrderByRoleDesc() {
        return abstractDaoFindAll(Role.FIND_ALL_ORDER_BY_ROLE_DESC);
    }

    /**
     * TODO
     *
     * {@inheritDoc }
     */
    @Override
    public List<Role> findAllByIdIn(Iterable<UUID> ids) {
        return abstractDaoFindAllWhereIn(Role.FIND_ALL_WHERE_ID_IN, "ids", ids);
    }

    @Override
    public List<Role> findAllWhereRole(String role) {
        return abstractDaoFindAllWhereField(Role.FIND_WHERE_ROLE, "role", role);
    }

    @Override
    public List<Role> findAllByRoleIn(Iterable<String> roles) {
        return abstractDaoFindAllWhereIn(Role.FIND_ALL_WHERE_ROLE_IN, "roles", roles);
    }

    @Override
    public List<Role> range(int start, int size) {
        return jpaRange(Role.FIND_ALL, start, size);
    }

    @Override
    public List<Role> rangeOrderByRoleAsc(int start, int size) {
        return jpaRange(Role.FIND_ALL_ORDER_BY_ROLE_ASC, start, size);
    }

    @Override
    public List<Role> rangeOrderByRoleDesc(int start, int size) {
        return jpaRange(Role.FIND_ALL_ORDER_BY_ROLE_DESC, start, size);
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
    public Role save(Role entity) {
        return abstractDaoSave(entity);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public Iterable<Role> saveAll(Iterable<Role> entities) {
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
    public void deleteAll(Iterable<Role> entities) {
        abstractDaoDeleteAll(entities);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    EntityManager getEntityManager() {
        return this.entityManager;
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
    public Class<Role> getEClass() {
        return Role.class;
    }
}
//EOF
