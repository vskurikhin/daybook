/*
 * This file was last modified at 2020.04.14 22:15 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * TagDto.java
 * $Id$
 */

package su.svn.showcase.dto;

import java.time.LocalDateTime;

/**
 * The DTO of Tag is interface as a contract.
 *
 * @author Victor N. Skurikhin
 */
public interface TagDto extends Dto<String> {

    String getTag();

    void setTag(String tag);

    Boolean getVisible();

    void setVisible(Boolean visible);

    LocalDateTime getDateTime();

    void setDateTime(LocalDateTime dateTime);
}
//EOF
