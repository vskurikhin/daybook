/*
 * This file was last modified at 2020.04.14 22:15 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * ArticleDto.java
 * $Id$
 */

package su.svn.showcase.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

/**
 * The DTO of Article is interface as a contract.
 *
 * @author Victor N. Skurikhin
 */
public interface ArticleDto extends Dto<UUID> {

    LocalDateTime getDateTime();

    void setDateTime(LocalDateTime dateTime);

    String getTitle();

    void setTitle(String title);

    String getInclude();

    void setInclude(String include);

    String getSummary();

    void setSummary(String summary);

    default String toDateDDMMYYYY() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        return getDateTime().format(formatter);
    }
}
//EOF
