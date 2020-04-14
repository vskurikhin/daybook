/*
 * This file was last modified at 2020.04.14 22:15 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * LinkDto.java
 * $Id$
 */

package su.svn.showcase.dto;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * The DTO of Link is interface as a contract.
 *
 * @author Victor N. Skurikhin
 */
public interface LinkDto extends Dto<UUID> {

    Boolean getVisible();

    void setVisible(Boolean visible);

    LocalDateTime getDateTime();

    void setDateTime(LocalDateTime dateTime);

    String getLink();

    void setLink(String link);
}
//EOF
