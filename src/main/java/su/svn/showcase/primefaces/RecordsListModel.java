/*
 * This file was last modified at 2020.03.01 18:24 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * RecordsListModel.java
 * $Id$
 */

package su.svn.showcase.primefaces;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import su.svn.showcase.dto.RecordFullDto;
import su.svn.showcase.services.RecordFullCrudService;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

public class RecordsListModel extends LazyDataModel<RecordFullDto> {
    @NotNull
    private final RecordFullCrudService recordFullCrudService;

    public RecordsListModel(@NotNull RecordFullCrudService service) {
        this.recordFullCrudService = service;
        this.setRowCount(service.count());
    }

    @Override
    public List<RecordFullDto> load(
           int first, int pageSize, String sortField,
           SortOrder sortOrder, Map<String, Object> filters) {
        return recordFullCrudService.readRange(first, pageSize);
    }
}
