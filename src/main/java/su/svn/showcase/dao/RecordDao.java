/*
 * This file was last modified at 2020.02.12 23:11 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * RecordDao.java
 * $Id$
 */

package su.svn.showcase.dao;

import su.svn.showcase.domain.Record;
import su.svn.showcase.exceptions.ErrorCase;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * This class provides Data Access methods for Record objects.
 *
 * @author Victor N. Skurikhin
 */
public interface RecordDao extends Dao<UUID, Record> {

    /**
     * {@inheritDoc }
     */
    @Override
    List<Record> findAll();

    /**
     * {@inheritDoc }
     */
    @Override
    List<Record> findAllByIdIn(Iterable<UUID> ids);

    /**
     * Retrieves all records in ordered mode.
     *
     * @return records of entity type by condition.
     */
    List<Record> findAllOrderByEditDateTimeDescIndex();

    /**
     * Retrieves all records of an Record type by the set of possible key values.
     *
     * @param ids - set of possible key values.
     * @return records of entity by condition.
     * @throws ErrorCase in case the given {@link Iterable} is {@literal null}.
     */
    List<Record> findAllWhereIdInOrderByEditDateTimeDescIndex(Iterable<UUID> ids);

    /**
     * Retrieves all records of an Record type by the specific day.
     *
     * @param date - the specific day.
     * @return records of entity by condition.
     */
    List<Record> findAllByDay(LocalDate date);

    /**
     * Retrieves all records of an Record type in ordered mode by the specific day.
     *
     * @param date - the specific day.
     * @return records of entity by condition.
     */
    List<Record> findAllByDayOrderByEditDateTimeDescIndex(LocalDate date);


    /**
     * Retrieves the record and fetch the related entities in a single query
     * instead of additional queries for each access of the object's lazy relationships
     * of entity by key.
     *
     * @param id - key.
     * @return record of entity by key.
     * @throws ErrorCase if {@code id} is {@literal null}.
     */
    Optional<Record> fetchById(UUID id);


    /**
     * Retrieves all records of the Record type and fetch fields of entity type in a single query.
     *
     * @return records of entity by condition.
     */
    List<Record> fetchAll();

    /**
     * Retrieves all records of the Record type in ordered mode and fetch fields of an entity type
     * in a single query.
     *
     * @return records of entity by condition.
     */
    List<Record> fetchAllOrderByEditDateTimeDescIndex();

    /**
     * Retrieves all records of the Record type and fetch a fields of entity type
     * by the set of possible key values.
     *
     * @param ids - set of possible key values.
     * @return records of entity by condition.
     * @throws ErrorCase in case the given {@link Iterable} is {@literal null}.
     */
    List<Record> fetchAllWhereIdIn(Iterable<UUID> ids);

    /**
     * Retrieves all records of the Record type in ordered mode by the set of possible key values
     * and fetch fields of an entity type in a single query.
     *
     * @param ids - set of possible key values.
     * @return records of entity by condition.
     * @throws ErrorCase in case the given {@link Iterable} is {@literal null}.
     */
    List<Record> fetchAllWhereIdInOrderByEditDateTimeDescIndex(Iterable<UUID> ids);

    /**
     * Retrieves all records of the Record type and fetch fields of entity type in a single query
     * by the specific day.
     *
     * @param date - the specific day.
     * @return records of entity by condition.
     */
    List<Record> fetchAllByDay(LocalDate date);

    /**
     * Retrieves all records of the Record type in ordered mode and fetch fields of entity type
     * in a single query by the specific day.
     *
     * @param date - the specific day.
     * @return records of entity by condition.
     */
    List<Record> fetchAllByDayOrderByEditDateTimeDescIndex(LocalDate date);


    /**
     * Returns all instances of Record type and falling within
     * the range specified in the records of the initial position and size.
     *
     * @param start - initial position.
     * @param size - size.
     * @return records of entity by condition.
     */
    List<Record> range(int start, int size);

    /**
     * Returns all instances of Record type from ordered query and
     * falling within the range specified in the arguments of the initial
     * position and size.
     *
     * @param start - initial position.
     * @param size - size.
     * @return records of entity by condition.
     */
    List<Record> rangeOrderByEditDateTimeDescIndex(int start, int size);

    /**
     * Returns all instances of Record type from query with the given IDs and
     * falling within the range specified in the arguments of the initial
     * position and size.
     *
     * @param start - initial position.
     * @param size - size.
     * @param ids - IDs.
     * @return records of entity by condition.
     * @throws ErrorCase in case the given {@link Iterable} is {@literal null}.
     */
    List<Record> rangeWhereIdIn(int start, int size, Iterable<UUID> ids);

    /**
     * Returns all instances of Record type from ordered query with the given IDs and
     * falling within the range specified in the arguments of the initial
     * position and size.
     *
     * @param start - initial position.
     * @param size - size.
     * @param ids - set of possible key values.
     * @return records of entity by condition.
     * @throws ErrorCase in case the given {@link Iterable} is {@literal null}.
     */
    List<Record> rangeWhereIdInOrderByEditDateTimeDescIndex(int start, int size, Iterable<UUID> ids);

    /**
     * Returns all instances of Record type from query with the given day and
     * falling within the range specified in the arguments of the initial
     * position and size.
     *
     * @param start - initial position.
     * @param size - size.
     * @param date -the given day
     * @return records of entity by condition.
     */
    List<Record> rangeByDay(int start, int size, LocalDate date);

    /**
     * Returns all instances of Record type from ordered query with the given day and
     * falling within the range specified in the arguments of the initial
     * position and size.
     *
     * @param start - initial position.
     * @param size - size.
     * @param date -the given day
     * @return records of entity by condition.
     */
    List<Record> rangeByDayOrderByEditDateTimeDescIndex(int start, int size, LocalDate date);


    /**
     * Returns the number of entities available in the specific day.
     *
     * @param date - the specific day.
     * @return records of entity by condition.
     */
    Integer countByDay(LocalDate date);
}
//EOF
