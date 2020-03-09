/*
 * This file was last modified at 2020.03.09 14:58 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * UserLoginDaoEjb.java
 * $Id$
 */

package su.svn.showcase.dao.jpa;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import su.svn.showcase.dao.UserLoginDao;
import su.svn.showcase.domain.UserLogin;
import su.svn.showcase.utils.StubEntityManager;

import javax.ejb.Stateless;
import javax.persistence.*;

import static su.svn.shared.Constants.Db.PERSISTENCE_UNIT_NAME;

/**
 * The UserLogin DAO/EJB implementation.
 *
 * @author Victor N. Skurikhin
 */
@Stateless
public class UserLoginDaoEjb extends UserLoginDaoJpa implements UserLoginDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserLoginDaoEjb.class);

    @PersistenceContext(unitName = PERSISTENCE_UNIT_NAME)
    private EntityManager entityManager;

    public UserLoginDaoEjb() {
        super(new StubEntityManager()); // TODO create bridge for EntityManager
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
    public Class<UserLogin> getEClass() {
        return UserLogin.class;
    }
}
//EOF
