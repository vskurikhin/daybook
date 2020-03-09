/*
 * This file was last modified at 2020.03.05 13:49 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * RecordsByDayModel.java
 * $Id$
 */

package su.svn.showcase.primefaces;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import su.svn.showcase.dto.RecordFullDto;
import su.svn.showcase.services.RecordFullCrudService;

import javax.annotation.Nonnull;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class RecordsByDayModel extends LazyDataModel<RecordFullDto> {
    @NotNull
    private final RecordFullCrudService recordFullCrudService;

    private final LocalDate localDate;

    public RecordsByDayModel(@NotNull RecordFullCrudService service, @Nonnull LocalDate date) {
        this.recordFullCrudService = service;
        this.setRowCount(service.countByDay(date));
        this.localDate = date;
    }

    @Override
    public List<RecordFullDto> load(
           int first, int pageSize, String sortField,
           SortOrder sortOrder, Map<String, Object> filters) {
        return recordFullCrudService.readRangeByDay(localDate, first, pageSize);
    }
}
