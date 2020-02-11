/*
 * This file was last modified at 2020.02.11 22:12 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * RecordDaoJpa.java
 * $Id$
 */

package su.svn.showcase.dao.jpa;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import su.svn.showcase.dao.RecordDao;
import su.svn.showcase.domain.Record;
import su.svn.showcase.utils.CollectionUtil;

import javax.ejb.Stateless;
import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

import static su.svn.shared.Constants.Db.PERSISTENCE_UNIT_NAME;

/**
 * The Record DAO implementation.
 *
 * @author Victor N. Skurikhin
 */
@Stateless
public class RecordDaoJpa extends AbstractDaoJpa<UUID, Record> implements RecordDao {
    private static final Logger LOGGER = LoggerFactory.getLogger(RecordDaoJpa.class);

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
    public Class<Record> getEClass() {
        return Record.class;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public Optional<Record> findById(UUID id) {
        return abstractDaoFindById(id);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public Optional<Record> findFetchById(UUID id) {
        return abstractDaoFindWhereField(Record.FIND_FETCH_BY_ID, "id", id);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public List<Record> findAll() {
        return abstractDaoFindAll(Record.FIND_ALL);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public List<Record> findAllByIdIn(Iterable<UUID> ids) {
        List<UUID> list = CollectionUtil.iterableToList(ids);
        return abstractDaoFindAllWhereIn(Record.FIND_ALL_WHERE_ID_IN, "ids", list);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public List<Record> findAllOrderByEditDateTime() {
        return abstractDaoFindAll(Record.FIND_ALL_ORDER_BY_EDIT_DATE_TIME);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public List<Record> findRangeWithTags(int start, int size) {
        TypedQuery<Record> query = entityManager.createQuery(Record.RANGE_WITH_TAGS, Record.class);
        query.setFirstResult(start);
        query.setMaxResults(size);

        return query.getResultList();
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public List<Record> findRangeByDayWithTags(LocalDate date, int start, int size) {
        LocalDateTime startDateTime = LocalDateTime.of(date, LocalTime.MIN);
        LocalDateTime endDateTime = LocalDateTime.of(date.plusDays(1), LocalTime.MIN);
        try {
            TypedQuery<Record> query = entityManager.createQuery(Record.RANGE_BY_DAY_FETCH_TAGS, Record.class);
            query.setParameter("startDate", startDateTime);
            query.setParameter("endDate", endDateTime);
            query.setFirstResult(start);
            query.setMaxResults(size);

            return query.getResultList();
        } catch (IllegalArgumentException | IllegalStateException | PersistenceException e) {
            LOGGER.error("Can't search all by day because had the exception ", e);
            return Collections.emptyList();
        }
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public List<Record> findAllByDay(LocalDate date) {
        LocalDateTime startDateTime = LocalDateTime.of(date, LocalTime.MIN);
        LocalDateTime endDateTime = LocalDateTime.of(date.plusDays(1), LocalTime.MIN);
        try {
            return entityManager.createNamedQuery(Record.FIND_ALL_BY_DAY, Record.class)
                    .setParameter("startDate", startDateTime)
                    .setParameter("endDate", endDateTime)
                    .getResultList();
        } catch (IllegalArgumentException | IllegalStateException | PersistenceException e) {
            LOGGER.debug("Can't search all by day {}", startDateTime);
            LOGGER.error("Can't search all by day because had the exception ", e);
            return Collections.emptyList();
        }
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public List<Record> findAllWhereTagId(UUID id) {
        return abstractDaoFindAllWhereField(Record.FIND_ALL_WHERE_TAG_ID, "id", id);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public List<Record> findAllWithTags() {
        return abstractDaoFindAll(Record.FIND_ALL_WITH_TAGS);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public long count() {
        return abstractCount();
    }

    /**
     * TODO
     *
     * @param date
     * @return
     */
    @Override
    public Integer countByDay(LocalDate date) {
        LocalDateTime startDateTime = LocalDateTime.of(date, LocalTime.MIN);
        LocalDateTime endDateTime = LocalDateTime.of(date.plusDays(1), LocalTime.MIN);
        Query query = entityManager.createQuery(Record.COUNT_BY_DAY);;
        query.setParameter("startDate", startDateTime);
        query.setParameter("endDate", endDateTime);
        Long count = (Long) query.getSingleResult();
        if (null == count || count > Integer.MAX_VALUE) {
            LOGGER.error("Can't get count : {}", count);
        }
        return count != null ? count.intValue() : null;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public boolean save(Record entity) {
        return abstractDaoSave(entity);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public boolean saveAll(Iterable<Record> entities) {
        return abstractDaoSaveAll(entities);
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
    public boolean deleteAll(Iterable<Record> entities) {
        return abstractDaoDeleteAll(entities);
    }
}
//EOF
