/*
 * This file was last modified at 2020.03.04 18:20 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * RecordDaoJpa.java
 * $Id$
 */

package su.svn.showcase.dao.jpa;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import su.svn.showcase.domain.Record;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

/**
 * The Record DAO implementation.
 *
 * @author Victor N. Skurikhin
 */
public class RecordDaoJpa extends AbstractDaoJpa<UUID, Record> {

    private static final Logger LOGGER = LoggerFactory.getLogger(RecordDaoJpa.class);

    private EntityManager entityManager;

    public RecordDaoJpa(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * {@inheritDoc }
     * @throws IllegalArgumentException if the first argument does
     *         not denote an entity type or the second argument is
     *         is not a valid type for that entitys primary key or
     *         is null
     */
    @Override
    public Optional<Record> findById(UUID id) {
        return jpaFindById(id);
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
        return abstractDaoFindAllWhereIn(Record.FIND_ALL_WHERE_ID_IN, "ids", ids);
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
    public Record save(Record entity) {
        return jpaDaoSave(entity);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public Iterable<Record> saveAll(Iterable<Record> entities) {
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
    public void deleteAll(Iterable<Record> entities) {
        abstractDaoDeleteAll(entities);
    }

    public List<Record> findAllOrderByEditDateTimeDescIndex() {
        return abstractDaoFindAll(Record.FIND_ALL_ORDER_BY_EDIT_DATE_TIME_DESC_INDEX);
    }

    /**
     * {@inheritDoc }
     */
    public List<Record> findAllWhereIdInOrderByEditDateTimeDescIndex(Iterable<UUID> ids) {
        final String query = Record.FIND_ALL_WHERE_ID_IN_ORDER_BY_EDIT_DATE_TIME_DESC_INDEX;
        return abstractDaoFindAllWhereIn(query, "ids", toList(ids));
    }

    public Optional<Record> fetchById(UUID id) {
        return jpaFindWhereField(Record.FETCH_BY_ID, "id", id);
    }

    public List<Record> fetchAll() {
        return abstractDaoFindAll(Record.FETCH_ALL);
    }

    public List<Record> fetchAllOrderByEditDateTimeDescIndex() {
        return abstractDaoFindAll(Record.FETCH_ALL_ORDER_BY_EDIT_DATE_TIME_DESC_INDEX);
    }

    public List<Record> fetchAllWhereIdIn(Iterable<UUID> ids) {
        return abstractDaoFindAllWhereIn(Record.FETCH_ALL_WHERE_ID_IN, "ids", toList(ids));
    }

    public List<Record> fetchAllWhereIdInOrderByEditDateTimeDescIndex(Iterable<UUID> ids) {
        final String query = Record.FIND_ALL_WHERE_ID_IN_ORDER_BY_EDIT_DATE_TIME_DESC_INDEX;
        return abstractDaoFindAllWhereIn(query, "ids", toList(ids));
    }

    public List<Record> range(int start, int size) {
        return jpaRange(Record.RANGE, start, size);
    }

    public List<Record> rangeOrderByEditDateTimeDescIndex(int start, int size) {
        return jpaRange(Record.RANGE_ORDER_BY_EDIT_DATE_TIME_DESC_INDEX, start, size);
    }

    public List<Record> rangeWhereIdIn(int start, int size, Iterable<UUID> ids) {
        return jpaRangeIdIn(Record.RANGE_ORDER_BY_EDIT_DATE_TIME_DESC_INDEX, start, size, ids);
    }

    public List<Record> rangeWhereIdInOrderByEditDateTimeDescIndex(int start, int size, Iterable<UUID> ids) {
        return jpaRangeIdIn(Record.RANGE_WHERE_ID_IN_ORDER_BY_EDIT_DATE_TIME_DESC_INDEX, start, size, ids);
    }

    public Integer countByDay(LocalDate date) {
        LocalDateTime startDateTime = LocalDateTime.of(date, LocalTime.MIN);
        LocalDateTime endDateTime = LocalDateTime.of(date.plusDays(1), LocalTime.MIN);
        Query query = getEntityManager().createQuery(Record.COUNT_BY_DAY);;
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
