/*
 * This file was last modified at 2020.04.14 21:45 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * ArticleDto.java
 * $Id$
 */

package su.svn.showcase.dto;

import su.svn.showcase.domain.Article;
import su.svn.showcase.domain.Link;
import su.svn.showcase.domain.Record;
import su.svn.showcase.domain.UserLogin;
import su.svn.showcase.interfaces.Updating;

import javax.annotation.Nonnull;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.UUID;

/**
 * The DTO of Article is interface as a contract.
 *
 * @author Victor N. Skurikhin
 */
public interface ArticleDto extends Dto<UUID>, Updating<Article> {

    LocalDateTime getDateTime();

    void setDateTime(LocalDateTime dateTime);

    String getTitle();

    void setTitle(String title);

    String getInclude();

    void setInclude(String include);

    String getSummary();

    void setSummary(String summary);

    @Deprecated
    Article update(@Nonnull Article entity, @Nonnull UserLogin userLogin);

    @Deprecated
    default Article update(@Nonnull Article entity, @Nonnull Map<String, Object> values) {
        convertIfContainsKey(Record.class, values, "record").ifPresent(entity::setRecord);
        convertIfContainsKey(Link.class, values, "link").ifPresent(entity::setLink);

        return update(entity);
    }

    default String toDateDDMMYYYY() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        return getDateTime().format(formatter);
    }
}
//EOF
