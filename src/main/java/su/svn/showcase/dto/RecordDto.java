/*
 * This file was last modified at 2020.02.27 18:02 by Victor N. Skurikhin.
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
import su.svn.showcase.interfaces.Updating;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Objects;
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

    default Record update(@NotNull Record entity, Map<String, Object> values) {
        assert entity != null;
        assert values != null;
        convertIfContainsKey(UserLogin.class, values, "userLogin").ifPresent(entity::setUserLogin);
        convertIfContainsKey(NewsEntry.class, values, "newsEntry").ifPresent(entity::setNewsEntry);
        convertSetIfContainsKey(Tag.class, values, "tags").ifPresent(entity::setTags);

        return update(entity);
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
