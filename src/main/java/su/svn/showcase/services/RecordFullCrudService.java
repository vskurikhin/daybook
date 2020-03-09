/*
 * This file was last modified at 2020.03.05 13:50 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * RecordFullCrudService.java
 * $Id$
 */

package su.svn.showcase.services;

import su.svn.showcase.dto.RecordFullDto;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface RecordFullCrudService extends CrudService<UUID, RecordFullDto> {

    int countByDay(LocalDate localDate);

    List<RecordFullDto> readRangeByDay(LocalDate localDate, int first, int pageSize);
}
