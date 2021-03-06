/*
 * This file was last modified at 2020.07.09 14:59 by Victor N. Skurikhin.
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
import su.svn.showcase.utils.OrderingQueryHibernate;

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
     */
    @Override
    public List<Record> findAllByDay(LocalDate date) {
        return jpaRecordQueryByDay(Record.FIND_ALL_BY_DAY, date);
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
        return rangeOrderBy(start, size, Record.getDefaultOrderMap());
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public List<Record> rangeWhereIdIn(int start, int size, Iterable<UUID> ids) {
        return jpaRangeIdIn(Record.FETCH_ALL_WHERE_ID_IN, start, size, ids);
    }

    public List<Record> rangeOrderBy(int start, int size, LinkedHashMap<String, Boolean> orderMap) {

        String sqlIds = OrderingQueryHibernate
                .getNamedQueryIdsOrderedBy(getEntityManager(), Record.FIND_ALL_IDS, orderMap);
        LOGGER.info("range ids from sql: {}", sqlIds);

        List<UUID> ids = jpaGetRangeIds(sqlIds, start, size);
        LOGGER.info("range from ids: {}", ids);

        if (ids.isEmpty()) {
            return Collections.emptyList();
        }

        String sql = OrderingQueryHibernate
                .getNamedQueryIdInOrderedBy(getEntityManager(), Record.FETCH_ALL_WHERE_ID_IN, orderMap);
        LOGGER.info("range values from sql: {}", sql);

        return jpaGetValuesByIds(sql, ids);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public List<Record> rangeByDay(int start, int size, LocalDate date) {
        return jpaRecordRange(Record.FETCH_ALL_BY_DAY, start, size, date);
    }


    /**
     * {@inheritDoc }
     */
    @Override
    public Integer countByDay(LocalDate date) {
        LocalDateTime startDateTime = LocalDateTime.of(date, LocalTime.MIN);
        LocalDateTime endDateTime = LocalDateTime.of(date.plusDays(1), LocalTime.MIN);
        Query query = getEntityManager().createQuery(Record.COUNT_BY_DAY);
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

    @Override
    public Class<UUID> getKClass() {
        return UUID.class;
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
