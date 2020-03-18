/*
 * This file was last modified at 2020.03.15 23:13 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * NewsLinksDto.java
 * $Id$
 */

package su.svn.showcase.dto;

import su.svn.showcase.domain.NewsLinks;
import su.svn.showcase.domain.NewsGroup;
import su.svn.showcase.domain.Record;
import su.svn.showcase.domain.UserLogin;
import su.svn.showcase.interfaces.Updating;

import javax.annotation.Nonnull;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.UUID;

/**
 * The DTO of NewsLinks is interface as a contract.
 *
 * @author Victor N. Skurikhin
 */
public interface NewsLinksDto extends Dto<UUID>, Updating<NewsLinks> {

    LocalDateTime getDateTime();

    void setDateTime(LocalDateTime dateTime);

    String getTitle();

    void setTitle(String title);

    NewsLinks update(@Nonnull NewsLinks entity, @Nonnull UserLogin userLogin);

    default NewsLinks update(@Nonnull NewsLinks entity, @Nonnull Map<String, Object> values) {
        convertIfContainsKey(Record.class, values, "record").ifPresent(entity::setRecord);
        convertIfContainsKey(NewsGroup.class, values, "newsGroup").ifPresent(entity::setNewsGroup);

        return update(entity);
    }

    default String toDateDDMMYYYY() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        return getDateTime().format(formatter);
    }
}
//EOF
