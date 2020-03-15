/*
 * This file was last modified at 2020.03.15 22:43 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * LinkDto.java
 * $Id$
 */

package su.svn.showcase.dto;

import su.svn.showcase.domain.Link;
import su.svn.showcase.interfaces.Updating;

import javax.annotation.Nonnull;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

/**
 * The DTO of Link is interface as a contract.
 *
 * @author Victor N. Skurikhin
 */
public interface LinkDto extends Dto<UUID>, Updating<Link> {

    Boolean getVisible();

    void setVisible(Boolean visible);

    LocalDateTime getDateTime();

    void setDateTime(LocalDateTime dateTime);

    String getLink();

    void setLink(String link);

    default Link update(@Nonnull Link entity, @Nonnull Map<String, Object> values) {
        return update(entity);
    }
}
//EOF
