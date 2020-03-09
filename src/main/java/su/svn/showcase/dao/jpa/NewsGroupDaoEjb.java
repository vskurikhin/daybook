/*
 * This file was last modified at 2020.03.09 15:28 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * NewsGroupDaoEjb.java
 * $Id$
 */

package su.svn.showcase.dao.jpa;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import su.svn.showcase.dao.NewsGroupDao;
import su.svn.showcase.domain.NewsGroup;
import su.svn.showcase.utils.StubEntityManager;

import javax.ejb.Stateless;
import javax.persistence.*;

import static su.svn.shared.Constants.Db.PERSISTENCE_UNIT_NAME;

@Stateless
public class NewsGroupDaoEjb extends NewsGroupDaoJpa implements NewsGroupDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(NewsGroupDaoEjb.class);

    @PersistenceContext(unitName = PERSISTENCE_UNIT_NAME)
    private EntityManager entityManager;

    public NewsGroupDaoEjb() {
        super(new StubEntityManager()); // TODO create bridge for EntityManager
    }

    @Override
    EntityManager getEntityManager() {
        return this.entityManager;
    }

    @Override
    Logger getLogger() {
        return LOGGER;
    }

    @Override
    public Class<NewsGroup> getEClass() {
        return NewsGroup.class;
    }
}
//EOF
