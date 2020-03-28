/*
 * This file was last modified at 2020.03.28 19:35 by Victor N. Skurikhin.
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

import javax.annotation.Nonnull;
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
public class RecordDaoJpa extends AbstractRecordDaoJpa implements RecordDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(RecordDaoJpa.class);

    private final EntityManager entityManager;

    public RecordDaoJpa(@Nonnull EntityManager entityManager) {
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
     * @throws IllegalArgumentException if a query has not been
     *         defined with the given name or if the query string is
     *         found to be invalid or if the query result is found to
     *         not be assignable to the specified type
     * @throws QueryTimeoutException if the query execution exceeds
     *         the query timeout value set and only the statement is
     *         rolled back
     * @throws TransactionRequiredException if a lock mode has
     *         been set and there is no transaction
     * @throws PessimisticLockException if pessimistic locking
     *         fails and the transaction is rolled back
     * @throws LockTimeoutException if pessimistic locking
     *         fails and only the statement is rolled back
     * @throws PersistenceException if the query execution exceeds
     *         the query timeout value set and the transaction
     *         is rolled back
     * @return
     */
    @Override
    public List<Record> findAll() {
        return jpaDaoFindAll(Record.FIND_ALL);
    }

    /**
     * {@inheritDoc }
     * @throws IllegalArgumentException if a query has not been
     *         defined with the given name or if the query string is
     *         found to be invalid or if the query result is found to
     *         not be assignable to the specified type
     * @throws QueryTimeoutException if the query execution exceeds
     *         the query timeout value set and only the statement is
     *         rolled back
     * @throws TransactionRequiredException if a lock mode has
     *         been set and there is no transaction
     * @throws PessimisticLockException if pessimistic locking
     *         fails and the transaction is rolled back
     * @throws LockTimeoutException if pessimistic locking
     *         fails and only the statement is rolled back
     * @throws PersistenceException if the query execution exceeds
     *         the query timeout value set and the transaction
     *         is rolled back
     * @return
     */
    @Override
    public List<Record> findAllByIdIn(Iterable<UUID> ids) {
        return jpaDaoFindAllWhereIn(Record.FIND_ALL_WHERE_ID_IN, "ids", ids);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public long count() {
        return jpaCount();
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
     * @param entities must not be {@literal null}.
     */
    @Override
    public Iterable<Record> saveAll(Iterable<Record> entities) {
        return jpaDaoSaveAll(entities);
    }

    /**
     * {@inheritDoc }
     * @param id must not be {@literal null}.
     * @throws IllegalArgumentException if instance is not an
     *         entity or is a removed entity
     * @throws TransactionRequiredException if invoked on a
     *         container-managed entity manager of type
     *         <code>PersistenceContextType.TRANSACTION</code> and there is
     *         no transaction
     * @throws IllegalArgumentException if the instance is not an
     *         entity or is a detached entity
     * @throws TransactionRequiredException if invoked on a
     *         container-managed entity manager of type
     *         <code>PersistenceContextType.TRANSACTION</code> and there is
     *         no transaction
     * @throws TransactionRequiredException if there is
     *         no transaction
     * @throws PersistenceException if the flush fails
     */
    @Override
    public void delete(UUID id) {
        jpaDaoDelete(id);
    }

    /**
     * {@inheritDoc }
     * @throws IllegalArgumentException if instance is not an
     *         entity or is a removed entity
     * @throws TransactionRequiredException if invoked on a
     *         container-managed entity manager of type
     *         <code>PersistenceContextType.TRANSACTION</code> and there is
     *         no transaction
     * @throws IllegalArgumentException if the instance is not an
     *         entity or is a detached entity
     * @throws TransactionRequiredException if invoked on a
     *         container-managed entity manager of type
     *         <code>PersistenceContextType.TRANSACTION</code> and there is
     *         no transaction
     * @throws TransactionRequiredException if there is
     *         no transaction
     * @throws PersistenceException if the flush fails
     */
    @Override
    public void deleteAll(Iterable<Record> entities) {
        jpaDaoDeleteAll(entities);
    }

    /**
     * {@inheritDoc }
     * @throws IllegalArgumentException if a query has not been
     *         defined with the given name or if the query string is
     *         found to be invalid or if the query result is found to
     *         not be assignable to the specified type
     * @throws QueryTimeoutException if the query execution exceeds
     *         the query timeout value set and only the statement is
     *         rolled back
     * @throws TransactionRequiredException if a lock mode has
     *         been set and there is no transaction
     * @throws PessimisticLockException if pessimistic locking
     *         fails and the transaction is rolled back
     * @throws LockTimeoutException if pessimistic locking
     *         fails and only the statement is rolled back
     * @throws PersistenceException if the query execution exceeds
     *         the query timeout value set and the transaction
     *         is rolled back
     * @return
     */
    @Override
    public List<Record> findAllOrderByEditDateTimeDescIndex() {
        return jpaDaoFindAll(Record.FIND_ALL_ORDER_BY_EDIT_DATE_TIME_DESC_INDEX);
    }

    /**
     * {@inheritDoc }
     * @param ids - possible values.
     * @return list of Records
     * @throws IllegalArgumentException if a query has not been
     *         defined with the given name or if the query string is
     *         found to be invalid or if the query result is found to
     *         not be assignable to the specified type
     *         or if iterable is null
     * @throws QueryTimeoutException if the query execution exceeds
     *         the query timeout value set and only the statement is
     *         rolled back
     * @throws TransactionRequiredException if a lock mode has
     *         been set and there is no transaction
     * @throws PessimisticLockException if pessimistic locking
     *         fails and the transaction is rolled back
     * @throws LockTimeoutException if pessimistic locking
     *         fails and only the statement is rolled back
     * @throws PersistenceException if the query execution exceeds
     *         the query timeout value set and the transaction
     *         is rolled back
     */
    @Override
    public List<Record> findAllWhereIdInOrderByEditDateTimeDescIndex(Iterable<UUID> ids) {
        final String query = Record.FIND_ALL_WHERE_ID_IN_ORDER_BY_EDIT_DATE_TIME_DESC_INDEX;
        return jpaDaoFindAllWhereIn(query, "ids", toList(ids));
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
        return jpaFindWhereField(Record.FETCH_BY_ID, "id", id);
    }

    /**
     * {@inheritDoc }
     * @throws IllegalArgumentException if a query has not been
     *         defined with the given name or if the query string is
     *         found to be invalid or if the query result is found to
     *         not be assignable to the specified type
     * @throws QueryTimeoutException if the query execution exceeds
     *         the query timeout value set and only the statement is
     *         rolled back
     * @throws TransactionRequiredException if a lock mode has
     *         been set and there is no transaction
     * @throws PessimisticLockException if pessimistic locking
     *         fails and the transaction is rolled back
     * @throws LockTimeoutException if pessimistic locking
     *         fails and only the statement is rolled back
     * @throws PersistenceException if the query execution exceeds
     *         the query timeout value set and the transaction
     *         is rolled back
     * @return
     */
    @Override
    public List<Record> fetchAll() {
        return jpaDaoFindAll(Record.FETCH_ALL);
    }

    /**
     * {@inheritDoc }
     * @throws IllegalArgumentException if a query has not been
     *         defined with the given name or if the query string is
     *         found to be invalid or if the query result is found to
     *         not be assignable to the specified type
     * @throws QueryTimeoutException if the query execution exceeds
     *         the query timeout value set and only the statement is
     *         rolled back
     * @throws TransactionRequiredException if a lock mode has
     *         been set and there is no transaction
     * @throws PessimisticLockException if pessimistic locking
     *         fails and the transaction is rolled back
     * @throws LockTimeoutException if pessimistic locking
     *         fails and only the statement is rolled back
     * @throws PersistenceException if the query execution exceeds
     *         the query timeout value set and the transaction
     *         is rolled back
     * @return
     */
    @Override
    public List<Record> fetchAllOrderByEditDateTimeDescIndex() {
        return jpaDaoFindAll(Record.FETCH_ALL_ORDER_BY_EDIT_DATE_TIME_DESC_INDEX);
    }

    /**
     * {@inheritDoc }
     * @param ids - possible values.
     * @return list of Records
     * @throws IllegalArgumentException if a query has not been
     *         defined with the given name or if the query string is
     *         found to be invalid or if the query result is found to
     *         not be assignable to the specified type
     *         or if iterable is null
     * @throws QueryTimeoutException if the query execution exceeds
     *         the query timeout value set and only the statement is
     *         rolled back
     * @throws TransactionRequiredException if a lock mode has
     *         been set and there is no transaction
     * @throws PessimisticLockException if pessimistic locking
     *         fails and the transaction is rolled back
     * @throws LockTimeoutException if pessimistic locking
     *         fails and only the statement is rolled back
     * @throws PersistenceException if the query execution exceeds
     *         the query timeout value set and the transaction
     *         is rolled back
     */
    @Override
    public List<Record> fetchAllWhereIdIn(Iterable<UUID> ids) {
        return jpaDaoFindAllWhereIn(Record.FETCH_ALL_WHERE_ID_IN, "ids", toList(ids));
    }

    /**
     * {@inheritDoc }
     * @param ids - possible values.
     * @return list of Records
     * @throws IllegalArgumentException if a query has not been
     *         defined with the given name or if the query string is
     *         found to be invalid or if the query result is found to
     *         not be assignable to the specified type
     *         or if iterable is null
     * @throws QueryTimeoutException if the query execution exceeds
     *         the query timeout value set and only the statement is
     *         rolled back
     * @throws TransactionRequiredException if a lock mode has
     *         been set and there is no transaction
     * @throws PessimisticLockException if pessimistic locking
     *         fails and the transaction is rolled back
     * @throws LockTimeoutException if pessimistic locking
     *         fails and only the statement is rolled back
     * @throws PersistenceException if the query execution exceeds
     *         the query timeout value set and the transaction
     *         is rolled back
     */
    @Override
    public List<Record> fetchAllWhereIdInOrderByEditDateTimeDescIndex(Iterable<UUID> ids) {
        final String query = Record.FIND_ALL_WHERE_ID_IN_ORDER_BY_EDIT_DATE_TIME_DESC_INDEX;
        return jpaDaoFindAllWhereIn(query, "ids", toList(ids));
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
     *  @throws IllegalArgumentException if a query has not been
     *          defined with the given name or if the query string is
     *          found to be invalid or if the query result is found to
     *          not be assignable to the specified type
     *  @throws IllegalArgumentException if the argument is negative
     *  @throws IllegalStateException if called for a Java
     *          Persistence query language UPDATE or DELETE statement
     *  @throws QueryTimeoutException if the query execution exceeds
     *          the query timeout value set and only the statement is
     *          rolled back
     *  @throws TransactionRequiredException if a lock mode has
     *          been set and there is no transaction
     *  @throws PessimisticLockException if pessimistic locking
     *          fails and the transaction is rolled back
     *  @throws LockTimeoutException if pessimistic locking
     *          fails and only the statement is rolled back
     *  @throws PersistenceException if the query execution exceeds
     *          the query timeout value set and the transaction
     *          is rolled back
     */
    @Override
    public List<Record> range(int start, int size) {
        return jpaRange(Record.RANGE, start, size);
    }

    /**
     * {@inheritDoc }
     *  @throws IllegalArgumentException if a query has not been
     *          defined with the given name or if the query string is
     *          found to be invalid or if the query result is found to
     *          not be assignable to the specified type
     *  @throws IllegalArgumentException if the argument is negative
     *  @throws IllegalStateException if called for a Java
     *          Persistence query language UPDATE or DELETE statement
     *  @throws QueryTimeoutException if the query execution exceeds
     *          the query timeout value set and only the statement is
     *          rolled back
     *  @throws TransactionRequiredException if a lock mode has
     *          been set and there is no transaction
     *  @throws PessimisticLockException if pessimistic locking
     *          fails and the transaction is rolled back
     *  @throws LockTimeoutException if pessimistic locking
     *          fails and only the statement is rolled back
     *  @throws PersistenceException if the query execution exceeds
     *          the query timeout value set and the transaction
     *          is rolled back
     */
    @Override
    public List<Record> rangeOrderByEditDateTimeDescIndex(int start, int size) {
        return jpaRange(Record.RANGE_ORDER_BY_EDIT_DATE_TIME_DESC_INDEX, start, size);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public List<Record> rangeWhereIdIn(int start, int size, Iterable<UUID> ids) {
        return jpaRangeIdIn(Record.RANGE_ORDER_BY_EDIT_DATE_TIME_DESC_INDEX, start, size, ids);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public List<Record> rangeWhereIdInOrderByEditDateTimeDescIndex(int start, int size, Iterable<UUID> ids) {
        return jpaRangeIdIn(Record.RANGE_WHERE_ID_IN_ORDER_BY_EDIT_DATE_TIME_DESC_INDEX, start, size, ids);
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
