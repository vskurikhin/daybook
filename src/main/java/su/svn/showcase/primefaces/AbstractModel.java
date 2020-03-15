/*
 * This file was last modified at 2020.03.15 12:34 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * AbstractModel.java
 * $Id$
 */

package su.svn.showcase.primefaces;

import org.slf4j.Logger;

import javax.annotation.Nullable;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.ResolverStyle;

abstract class AbstractModel {

    abstract Logger getLogger();

    public LocalDateTime parseLocalDateTime(String date) {
        DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                .parseStrict()
                .appendPattern("dd/MM/uuuu")
                .toFormatter()
                .withResolverStyle(ResolverStyle.STRICT);
        LocalDate localDate = null;
        try {
            getLogger().warn("date: {}", date);
            localDate = LocalDate.parse(date, formatter);
        } catch (Exception e) {
            getLogger().error("dateTime exception: ", e);
            localDate = LocalDate.now();
        }
        LocalDateTime dateTime = LocalDateTime.of(localDate, LocalTime.now());
        getLogger().warn("dateTime: {}", dateTime);

        return dateTime;
    }
}
//EOF
