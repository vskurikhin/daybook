/*
 * This file was last modified at 2020.04.15 22:24 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * RecordDto.java
 * $Id$
 */

package su.svn.showcase.dto;

import su.svn.showcase.dto.enums.ArticleDtoEnum;
import su.svn.showcase.dto.enums.NewsEntryDtoEnum;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

/**
 * The DTO of Record is interface as a contract.
 *
 * @author Victor N. Skurikhin
 */
public interface RecordDto extends Dto<UUID> {

    LocalDateTime getCreateDateTime();

    void setCreateDateTime(LocalDateTime createDateTime);

    LocalDateTime getEditDateTime();

    void setEditDateTime(LocalDateTime editDateTime);

    Integer getIndex();

    void setIndex(Integer index);

    String getType();

    void setType(String type);

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
