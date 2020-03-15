/*
 * This file was last modified at 2020.03.15 17:19 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * NewsLinksDaoEjb.java
 * $Id$
 */

package su.svn.showcase.dao.jpa;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import su.svn.showcase.dao.NewsLinksDao;
import su.svn.showcase.domain.NewsLinks;
import su.svn.showcase.utils.StubEntityManager;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static su.svn.shared.Constants.Db.PERSISTENCE_UNIT_NAME;

/**
 * The NewsLinks DAO/EJB implementation.
 *
 * @author Victor N. Skurikhin
 */
@Stateless
public class NewsLinksDaoEjb extends NewsLinksDaoJpa implements NewsLinksDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(NewsLinksDaoEjb.class);

    @PersistenceContext(unitName = PERSISTENCE_UNIT_NAME)
    private EntityManager entityManager;

    public NewsLinksDaoEjb() {
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
    public Class<NewsLinks> getEClass() {
        return NewsLinks.class;
    }
}
