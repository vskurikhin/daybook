/*
 * This file was last modified at 2020.02.10 22:25 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * RecordDao.java
 * $Id$
 */

package su.svn.showcase.dao;

import su.svn.showcase.domain.Record;

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
     * Retrieves the record and fetch the related entities in a single query
     * instead of additional queries for each access of the object's lazy relationships
     * of entity by key.
     *
     * @param id - key.
     * @return record of entity by key.
     */
    Optional<Record> findFetchById(UUID id); // TODO rename fetchById

    /**
     * Retrieves all records and fetch the tag entities in a single query.
     *
     * @return records of entity.
     */
    List<Record> findAllWithTags();

    /**
     * Returns all instances of the type ordered by editDateTime.
     *
     * @return all entities.
     */
    List<Record> findAllOrderByEditDateTime();

    /**
     * Returns all instances of the type sorted using editDateTime and
     * falling within the range specified in the records of the initial
     * position and size.
     *
     * @param start - initial position.
     * @param size - size.
     * @return records of entity by condition.
     */
    List<Record> findRangeWithTags(int start, int size);

    /**
     * Returns all instances of the type sorted using editDateTime and
     * falling within the range specified in the records of the initial
     * position and size in the specific day.
     *
     * @param localDate - the specific day.
     * @param start - initial position.
     * @param size - size.
     * @return records of entity by condition.
     */
    List<Record> findRangeByDayWithTags(LocalDate localDate, int start, int size);

    /**
     * Returns all instances of the type sorted using editDateTime and
     * falling within the range specified in the records of the initial
     * position and size in the specific day.
     *
     * @param date - the specific day.
     * @return records of entity by condition.
     */
    List<Record> findAllByDay(LocalDate date);

    /**
     * Retrieves all records and fetch the tag entities in a single query
     * of entity by key.
     *
     * @param id - key.
     * @return record of entity by key.
     */
    List<Record> findAllWhereTagId(UUID id);

    /**
     * Returns the number of entities available in the specific day.
     *
     * @return the number of entities.
     */
    Integer countByDay(LocalDate localDate);
}
//EOF
