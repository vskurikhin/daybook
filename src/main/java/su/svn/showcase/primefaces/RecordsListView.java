/*
 * This file was last modified at 2020.04.14 21:45 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * RecordsListView.java
 * $Id$
 */

package su.svn.showcase.primefaces;

import org.primefaces.model.LazyDataModel;
import su.svn.showcase.dto.jdo.RecordJdo;
import su.svn.showcase.services.RecordCrudService;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean
@ViewScoped
public class RecordsListView {
    @EJB
    private RecordCrudService recordCrudService;

    private RecordsListModel recordsListModel;

    @PostConstruct
    private void init() {
        recordsListModel = new RecordsListModel(recordCrudService);
    }

    public LazyDataModel<RecordJdo> getModel() {
        return recordsListModel;
    }
}
