/*
 * This file was last modified at 2020.02.26 16:30 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * RecordsListView.java
 * $Id$
 */

package su.svn.showcase.primefaces;

import org.primefaces.model.LazyDataModel;
import su.svn.showcase.dto.RecordFullDto;
import su.svn.showcase.services.RecordFullCrudService;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class RecordsListView {
    @EJB
    private RecordFullCrudService recordFullCrudService;

    private RecordsListModel recordsListModel;

    @PostConstruct
    private void init() {
        recordsListModel = new RecordsListModel(recordFullCrudService);
    }

    public LazyDataModel<RecordFullDto> getModel() {
        return recordsListModel;
    }
}
