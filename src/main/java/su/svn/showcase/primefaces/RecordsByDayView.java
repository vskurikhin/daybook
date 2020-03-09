/*
 * This file was last modified at 2020.03.09 17:22 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * RecordsByDayView.java
 * $Id$
 */

package su.svn.showcase.primefaces;

import org.primefaces.model.LazyDataModel;
import su.svn.showcase.dto.RecordFullDto;
import su.svn.showcase.services.RecordFullCrudService;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import java.time.LocalDate;

@ManagedBean
@ViewScoped
public class RecordsByDayView {
    @EJB
    private RecordFullCrudService recordFullCrudService;

    @ManagedProperty(value="#{calendarView}")
    private CalendarView calendarView;

    public LazyDataModel<RecordFullDto> getModel() {
        LocalDate date = new java.sql.Date(calendarView.getDate().getTime()).toLocalDate();
        return new RecordsByDayModel(recordFullCrudService, date);
    }

    // must povide the setter method
    public void setCalendarView(CalendarView calendarView) {
        this.calendarView = calendarView;
    }
}
