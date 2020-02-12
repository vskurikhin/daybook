/*
 * This file was last modified at 2020.02.12 23:08 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * AbstractRecordDaoJpa.java
 * $Id$
 */

package su.svn.showcase.dao.jpa;

import su.svn.showcase.domain.Record;
import su.svn.showcase.utils.CollectionUtil;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * Abstract Record DAO class to be implemented by all DAO's.
 *
 * @author Victor N. Skurikhin
 */
abstract class AbstractRecordDaoJpa extends AbstractDaoJpa<UUID, Record> {

    List<Record> jpaRecordQueryByDay(String query, LocalDate date) {
        LocalDateTime startDateTime = LocalDateTime.of(date, LocalTime.MIN);
        LocalDateTime endDateTime = LocalDateTime.of(date.plusDays(1), LocalTime.MIN);
        try {
            return getEntityManager().createNamedQuery(query, Record.class)
                    .setParameter("startDate", startDateTime)
                    .setParameter("endDate", endDateTime)
                    .getResultList();
        } catch (IllegalArgumentException | IllegalStateException | PersistenceException e) {
            getLogger().error("Can't search all by day because had the exception ", e);
            return Collections.emptyList();
        }
    }

    List<Record> jpaRecordRange(String query, int start, int size) {
        try {
            TypedQuery<Record> typedQuery = getEntityManager().createQuery(query, Record.class);
            typedQuery.setFirstResult(start);
            typedQuery.setMaxResults(size);

            return typedQuery.getResultList();
        } catch (IllegalArgumentException | IllegalStateException | PersistenceException e) {
            getLogger().error("Can't search all by day because had the exception ", e);
            return Collections.emptyList();
        }
    }

    List<Record> jpaRecordRange(String query, int start, int size, Iterable<UUID> ids) {
        try {
            TypedQuery<Record> typedQuery = getEntityManager().createQuery(query, Record.class);
            typedQuery.setFirstResult(start);
            typedQuery.setMaxResults(size);
            List<UUID> list = CollectionUtil.iterableToList(ids);
            typedQuery.setParameter("ids", list);

            return typedQuery.getResultList();
        } catch (IllegalArgumentException | IllegalStateException | PersistenceException e) {
            getLogger().error("Can't search all by day because had the exception ", e);
            return Collections.emptyList();
        }
    }

    List<Record> jpaRecordRange(String query, int start, int size, LocalDate date) {
        LocalDateTime startDateTime = LocalDateTime.of(date, LocalTime.MIN);
        LocalDateTime endDateTime = LocalDateTime.of(date.plusDays(1), LocalTime.MIN);
        try {
            TypedQuery<Record> typedQuery = getEntityManager().createQuery(query, Record.class);
            typedQuery.setFirstResult(start);
            typedQuery.setMaxResults(size);
            typedQuery.setParameter("startDate", startDateTime);
            typedQuery.setParameter("endDate", endDateTime);

            return typedQuery.getResultList();
        } catch (IllegalArgumentException | IllegalStateException | PersistenceException e) {
            getLogger().error("Can't search all by day because had the exception ", e);
            return Collections.emptyList();
        }
    }
}
//EOF
