/*
 * This file was last modified at 2020.04.12 15:34 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * RecordDto.java
 * $Id$
 */

package su.svn.showcase.dto;

import su.svn.showcase.domain.NewsEntry;
import su.svn.showcase.domain.Record;
import su.svn.showcase.domain.Tag;
import su.svn.showcase.domain.UserLogin;
import su.svn.showcase.dto.enums.ArticleDtoEnum;
import su.svn.showcase.dto.enums.NewsEntryDtoEnum;
import su.svn.showcase.interfaces.Updating;

import javax.annotation.Nonnull;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.UUID;

/**
 * The DTO of Record is interface as a contract.
 *
 * @author Victor N. Skurikhin
 */
public interface RecordDto extends Dto<UUID>, Updating<Record> {

    LocalDateTime getCreateDateTime();

    void setCreateDateTime(LocalDateTime createDateTime);

    LocalDateTime getEditDateTime();

    void setEditDateTime(LocalDateTime editDateTime);

    Integer getIndex();

    void setIndex(Integer index);

    String getType();

    void setType(String type);

    default Record update(@Nonnull Record entity, @Nonnull Map<String, Object> values) {
        convertIfContainsKey(UserLogin.class, values, "userLogin").ifPresent(entity::setUserLogin);
        convertIfContainsKey(NewsEntry.class, values, "newsEntry").ifPresent(entity::setNewsEntry);
        convertSetIfContainsKey(Tag.class, values, "tags").ifPresent(entity::setTags);

        return update(entity);
    }

    default boolean isTypeOfNewsEntry() {
        return NewsEntryDtoEnum.containsValue(getType());
    }

    default boolean isTypeOfArticle() {
        return ArticleDtoEnum.containsValue(getType());
    }

    default String toDateHourMinute() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        return getEditDateTime().format(formatter);
    }

    default String toDateDDMMYYYY() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        return getEditDateTime().format(formatter);
    }
}
//EOF
