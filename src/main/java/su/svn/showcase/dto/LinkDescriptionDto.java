/*
 * This file was last modified at 2020.04.14 22:15 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * LinkDescriptionDto.java
 * $Id$
 */

package su.svn.showcase.dto;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * The DTO of LinkDescription is interface as a contract.
 *
 * @author Victor N. Skurikhin
 */
public interface LinkDescriptionDto extends Dto<UUID> {

    LocalDateTime getDateTime();

    void setDateTime(LocalDateTime dateTime);

    String getDescription();

    void setDescription(String description);

    String getDetails();

    void setDetails(String details);
}
//EOF
