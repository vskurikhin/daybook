/*
 * This file was last modified at 2020.03.09 16:35 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * NewsEntryDaoEjb.java
 * $Id$
 */

package su.svn.showcase.dao.jpa;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import su.svn.showcase.dao.NewsEntryDao;
import su.svn.showcase.domain.NewsEntry;
import su.svn.showcase.utils.StubEntityManager;

import javax.ejb.Stateless;
import javax.persistence.*;

import static su.svn.shared.Constants.Db.PERSISTENCE_UNIT_NAME;

/**
 * The NewsEntry DAO/EJB implementation.
 *
 * @author Victor N. Skurikhin
 */
@Stateless
public class NewsEntryDaoEjb extends NewsEntryDaoJpa implements NewsEntryDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(NewsEntryDaoEjb.class);

    @PersistenceContext(unitName = PERSISTENCE_UNIT_NAME)
    private EntityManager entityManager;

    public NewsEntryDaoEjb() {
        super(new StubEntityManager()); // TODO create bridge for EntityManager
    }

    @Override
    EntityManager getEntityManager() {
        return entityManager;
    }

    @Override
    Logger getLogger() {
        return LOGGER;
    }

    @Override
    public Class<NewsEntry> getEClass() {
        return NewsEntry.class;
    }
}
