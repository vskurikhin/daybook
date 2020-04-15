/*
 * This file was last modified at 2020.04.14 21:45 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * RecordCrudService.java
 * $Id$
 */

package su.svn.showcase.services;

import su.svn.showcase.dto.jdo.RecordJdo;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface RecordCrudService extends CrudService<UUID, RecordJdo> {

    int countByDay(LocalDate localDate);

    List<RecordJdo> readRangeByDay(LocalDate localDate, int first, int pageSize);
}
