/*
 * This file was last modified at 2020.03.03 22:49 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * UserLoginDaoJpa.java
 * $Id$
 */

package su.svn.showcase.dao.jpa;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import su.svn.showcase.dao.UserLoginDao;
import su.svn.showcase.domain.UserLogin;

import javax.ejb.Stateless;
import javax.persistence.*;
import java.util.*;

import static su.svn.shared.Constants.Db.PERSISTENCE_UNIT_NAME;

/**
 * The UserLogin DAO implementation.
 *
 * @author Victor N. Skurikhin
 */
@Stateless
public class UserLoginDaoJpa extends AbstractDaoJpa<UUID, UserLogin> implements UserLoginDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserLoginDaoJpa.class);

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
    public Optional<UserLogin> findById(UUID id) {
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
    public Optional<UserLogin> fetchById(UUID id) {
        return jpaFindWhereField(UserLogin.FETCH_BY_ID, "id", id);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public Optional<UserLogin> findWhereLogin(String login) {
        return jpaFindWhereField(UserLogin.FIND_WHERE_LOGIN, "login", login);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public List<UserLogin> findAll() {
        return abstractDaoFindAll(UserLogin.FIND_ALL);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public List<UserLogin> findAllByIdIn(Iterable<UUID> ids) {
        return abstractDaoFindAllWhereIn(UserLogin.FIND_ALL_WHERE_ID_IN, "ids", ids);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public List<UserLogin> findAllInUserRoleByName(String name) {
        return abstractDaoFindAllWhereField(UserLogin.FIND_ALL_IN_USER_ROLE , "name", name);
    }

    @Override
    public List<UserLogin> range(int start, int size) {
        return jpaRange(UserLogin.RANGE, start, size);
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
    public UserLogin save(UserLogin entity) {
        return jpaDaoSave(entity);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public Iterable<UserLogin> saveAll(Iterable<UserLogin> entities) {
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
    public void  deleteAll(Iterable<UserLogin> entities) {
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
    public Class<UserLogin> getEClass() {
        return UserLogin.class;
    }
}
//EOF
