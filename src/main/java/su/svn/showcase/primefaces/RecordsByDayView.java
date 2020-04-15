/*
 * This file was last modified at 2020.04.14 21:45 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * RecordsByDayView.java
 * $Id$
 */

package su.svn.showcase.primefaces;

import org.primefaces.model.LazyDataModel;
import su.svn.showcase.dto.jdo.RecordJdo;
import su.svn.showcase.services.RecordCrudService;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import java.time.LocalDate;

@ManagedBean
@ViewScoped
public class RecordsByDayView {
    @EJB
    private RecordCrudService recordCrudService;

    @ManagedProperty(value="#{calendarView}")
    private CalendarView calendarView;

    public LazyDataModel<RecordJdo> getModel() {
        LocalDate date = new java.sql.Date(calendarView.getDate().getTime()).toLocalDate();
        return new RecordsByDayModel(recordCrudService, date);
    }

    // must povide the setter method
    public void setCalendarView(CalendarView calendarView) {
        this.calendarView = calendarView;
    }
}
