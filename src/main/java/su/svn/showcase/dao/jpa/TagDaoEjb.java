/*
 * This file was last modified at 2020.03.04 23:17 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * TagDaoEjb.java
 * $Id$
 */

package su.svn.showcase.dao.jpa;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import su.svn.showcase.dao.TagDao;
import su.svn.showcase.domain.Tag;
import su.svn.showcase.utils.StubEntityManager;

import javax.ejb.Stateless;
import javax.persistence.*;

import static su.svn.shared.Constants.Db.PERSISTENCE_UNIT_NAME;

/**
 * The Tag DAO implementation.
 *
 * @author Victor N. Skurikhin
 */
@Stateless
public class TagDaoEjb extends TagDaoJpa implements TagDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(TagDaoEjb.class);

    @PersistenceContext(unitName = PERSISTENCE_UNIT_NAME)
    private EntityManager entityManager;

    public TagDaoEjb() {
        super(new StubEntityManager()); // TODO create stub proxy for EntityManager
    }

    /**
     * {@inheritDoc }
     */
    @Override
    EntityManager getEntityManager() {
        return this.entityManager;
    }

    @Override
    Logger getLogger() {
        return LOGGER;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public Class<Tag> getEClass() {
        return Tag.class;
    }
}
//EOF
