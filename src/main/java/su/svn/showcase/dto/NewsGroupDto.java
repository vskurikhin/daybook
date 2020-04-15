/*
 * This file was last modified at 2020.04.14 22:15 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * NewsGroupDto.java
 * $Id$
 */

package su.svn.showcase.dto;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * The DTO of NewsGroup is interface as a contract.
 *
 * @author Victor N. Skurikhin
 */
public interface NewsGroupDto extends Dto<UUID> {

    LocalDateTime getDateTime();

    void setDateTime(LocalDateTime dateTime);

    String getGroup();

    void setGroup(String group);
}
//EOF
