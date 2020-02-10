/*
 * This file was last modified at 2020.02.09 23:39 by Victor N. Skurikhin.
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
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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

    @PersistenceContext(unitName = PERSISTENCE_UNIT_NAME)
    private EntityManager entityManager;

    /**
     * {@inheritDoc }
     */
    @Override
    public EntityManager getEntityManager() {
        return this.entityManager;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public Logger getLogger() {
        return LOGGER;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public Class<UserLogin> getEClass() {
        return UserLogin.class;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public Optional<UserLogin> findById(UUID id) {
        return abstractDaoFindById(id);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public Optional<UserLogin> findWhereLogin(String login) {
        return abstractDaoFindWhereField(UserLogin.FIND_WHERE_LOGIN, "login", login);
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
    public boolean save(UserLogin entity) {
        return abstractDaoSave(entity);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public boolean saveAll(Iterable<UserLogin> entities) {
        return false;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public boolean delete(UUID id) {
        return abstractDaoDelete(id);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public boolean deleteAll(Iterable<UserLogin> entities) {
        return abstractDaoDeleteAll(entities);
    }
}
//EOF
