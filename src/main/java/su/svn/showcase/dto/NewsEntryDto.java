/*
 * This file was last modified at 2020.03.01 00:04 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * NewsEntryDto.java
 * $Id$
 */

package su.svn.showcase.dto;

import su.svn.showcase.domain.NewsEntry;
import su.svn.showcase.domain.NewsGroup;
import su.svn.showcase.domain.Record;
import su.svn.showcase.domain.UserLogin;
import su.svn.showcase.interfaces.Updating;

import javax.annotation.Nonnull;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

/**
 * The DTO of NewsEntry is interface as a contract.
 *
 * @author Victor N. Skurikhin
 */
public interface NewsEntryDto extends Dto<UUID>, Updating<NewsEntry> {

    LocalDateTime getDateTime();

    void setDateTime(LocalDateTime dateTime);

    String getTitle();

    void setTitle(String title);

    String getContent();

    void setContent(String content);

    NewsEntry update(@NotNull NewsEntry entity, @NotNull UserLogin userLogin);

    default NewsEntry update(@Nonnull NewsEntry entity, @Nonnull Map<String, Object> values) {
        convertIfContainsKey(Record.class, values, "record").ifPresent(entity::setRecord);
        convertIfContainsKey(NewsGroup.class, values, "newsGroup").ifPresent(entity::setNewsGroup);

        return update(entity);
    }
}
//EOF
