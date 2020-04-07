/*
 * This file was last modified at 2020.04.07 23:20 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * RecordsByDayModel.java
 * $Id$
 */

package su.svn.showcase.primefaces;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import su.svn.showcase.dto.RecordFullDto;
import su.svn.showcase.services.RecordCrudService;

import javax.annotation.Nonnull;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class RecordsByDayModel extends LazyDataModel<RecordFullDto> {
    @NotNull
    private final RecordCrudService recordCrudService;

    private final LocalDate localDate;

    public RecordsByDayModel(@NotNull RecordCrudService service, @Nonnull LocalDate date) {
        this.recordCrudService = service;
        this.setRowCount(service.countByDay(date));
        this.localDate = date;
    }

    @Override
    public List<RecordFullDto> load(
           int first, int pageSize, String sortField,
           SortOrder sortOrder, Map<String, Object> filters) {
        return recordCrudService.readRangeByDay(localDate, first, pageSize);
    }
}
