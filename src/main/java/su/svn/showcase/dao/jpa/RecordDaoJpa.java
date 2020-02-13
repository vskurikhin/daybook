/*
 * This file was last modified at 2020.02.12 23:08 by Victor N. Skurikhin.
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
public class RecordDaoJpa extends AbstractRecordDaoJpa implements RecordDao {
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
    public long count() {
        return abstractCount();
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

    /**
     * {@inheritDoc }
     */
    @Override
    public List<Record> findAllOrderByEditDateTimeDescIndex() {
        return abstractDaoFindAll(Record.FIND_ALL_ORDER_BY_EDIT_DATE_TIME_DESC_INDEX);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public List<Record> findAllWhereIdInOrderByEditDateTimeDescIndex(Iterable<UUID> ids) {
        final String query = Record.FIND_ALL_WHERE_ID_IN_ORDER_BY_EDIT_DATE_TIME_DESC_INDEX;
        return abstractDaoFindAllWhereIn(query, "ids", toList(ids));
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public List<Record> findAllByDay(LocalDate date) {
        return jpaRecordQueryByDay(Record.FIND_ALL_BY_DAY, date);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public List<Record> findAllByDayOrderByEditDateTimeDescIndex(LocalDate date) {
        return jpaRecordQueryByDay(Record.FETCH_ALL_BY_DAY_ORDER_BY_EDIT_DATE_TIME_DESC_INDEX, date);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public Optional<Record> fetchById(UUID id) {
        return abstractDaoFindWhereField(Record.FETCH_BY_ID, "id", id);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public List<Record> fetchAll() {
        return abstractDaoFindAll(Record.FETCH_ALL);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public List<Record> fetchAllOrderByEditDateTimeDescIndex() {
        return abstractDaoFindAll(Record.FETCH_ALL_ORDER_BY_EDIT_DATE_TIME_DESC_INDEX);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public List<Record> fetchAllWhereIdIn(Iterable<UUID> ids) {
        return abstractDaoFindAllWhereIn(Record.FETCH_ALL_WHERE_ID_IN, "ids", toList(ids));
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public List<Record> fetchAllWhereIdInOrderByEditDateTimeDescIndex(Iterable<UUID> ids) {
        final String query = Record.FIND_ALL_WHERE_ID_IN_ORDER_BY_EDIT_DATE_TIME_DESC_INDEX;
        return abstractDaoFindAllWhereIn(query, "ids", toList(ids));
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public List<Record> fetchAllByDay(LocalDate date) {
        return jpaRecordQueryByDay(Record.FETCH_ALL_BY_DAY, date);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public List<Record> fetchAllByDayOrderByEditDateTimeDescIndex(LocalDate date) {
        return jpaRecordQueryByDay(Record.FETCH_ALL_BY_DAY_ORDER_BY_EDIT_DATE_TIME_DESC_INDEX, date);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public List<Record> range(int start, int size) {
        return jpaRecordRange(Record.RANGE, start, size);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public List<Record> rangeOrderByEditDateTimeDescIndex(int start, int size) {
        return jpaRecordRange(Record.RANGE_ORDER_BY_EDIT_DATE_TIME_DESC_INDEX, start, size);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public List<Record> rangeWhereIdIn(int start, int size, Iterable<UUID> ids) {
        return jpaRecordRange(Record.RANGE_ORDER_BY_EDIT_DATE_TIME_DESC_INDEX, start, size, ids);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public List<Record> rangeWhereIdInOrderByEditDateTimeDescIndex(int start, int size, Iterable<UUID> ids) {
        return jpaRecordRange(Record.RANGE_WHERE_ID_IN_ORDER_BY_EDIT_DATE_TIME_DESC_INDEX, start, size, ids);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public List<Record> rangeByDay(int start, int size, LocalDate date) {
        return jpaRecordRange(Record.RANGE_ALL_BY_DAY, start, size, date);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public List<Record> rangeByDayOrderByEditDateTimeDescIndex(int start, int size, LocalDate date) {
        return jpaRecordRange(Record.RANGE_ALL_BY_DAY_ORDER_BY_EDIT_DATE_TIME_DESC_INDEX, start, size, date);
    }


    /**
     * {@inheritDoc }
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
}
//EOF
