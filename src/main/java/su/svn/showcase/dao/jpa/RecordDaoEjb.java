/*
 * This file was last modified at 2020.03.09 16:35 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * RecordDaoEjb.java
 * $Id$
 */

package su.svn.showcase.dao.jpa;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import su.svn.showcase.dao.RecordDao;
import su.svn.showcase.domain.Record;
import su.svn.showcase.utils.StubEntityManager;

import javax.ejb.Stateless;
import javax.persistence.*;

import static su.svn.shared.Constants.Db.PERSISTENCE_UNIT_NAME;

/**
 * The Record DAO/EJB implementation.
 *
 * @author Victor N. Skurikhin
 */
@Stateless
public class RecordDaoEjb extends RecordDaoJpa implements RecordDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(RecordDaoEjb.class);

    @PersistenceContext(unitName = PERSISTENCE_UNIT_NAME)
    private EntityManager entityManager;

    public RecordDaoEjb() {
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
    public Class<Record> getEClass() {
        return Record.class;
    }
}
//EOF
