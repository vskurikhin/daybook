/*
 * This file was last modified at 2020.07.09 14:59 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * RecordDao.java
 * $Id$
 */

package su.svn.showcase.dao;

import su.svn.showcase.domain.Record;
import su.svn.showcase.exceptions.ErrorCase;
import su.svn.showcase.interfaces.Typing;

import java.time.LocalDate;
import java.util.LinkedHashMap;
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
     * Retrieves all records of an Record type by the specific day.
     *
     * @param date - the specific day.
     * @return records of entity by condition.
     */
    @Deprecated
    List<Record> findAllByDay(LocalDate date);

    /**
     * Retrieves the record and fetch the related entities in a single query
     * instead of additional queries for each access of the object's lazy relationships
     * of entity by key.
     *
     * @param id - key.
     * @return record of entity by key.
     */
    Optional<Record> fetchById(UUID id);

    /**
     * Retrieves all records of the Record type and fetch fields of entity type in a single query.
     *
     * @return records of entity by condition.
     */
    @Deprecated
    List<Record> fetchAll();

    /**
     * Retrieves all records of the Record type and fetch a fields of entity type
     * by the set of possible key values.
     *
     * @param ids - set of possible key values.
     * @return records of entity by condition.
     */
    List<Record> fetchAllWhereIdIn(Iterable<UUID> ids);

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
     * Returns all instances of Record type and falling within
     * the range specified in the records of the initial position and size.
     *
     * @param start - initial position.
     * @param size - size.
     * @param orderMap - order map.
     * @return records of entity by condition.
     */
    List<Record> rangeOrderBy(int start, int size, LinkedHashMap<String, Boolean> orderMap);

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
     * Returns the number of entities available in the specific day.
     *
     * @param date - the specific day.
     * @return records of entity by condition.
     */
    Integer countByDay(LocalDate date);
}
//EOF
