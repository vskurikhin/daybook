/*
 * This file was last modified at 2020.03.15 22:43 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * LinkDescriptionDto.java
 * $Id$
 */

package su.svn.showcase.dto;

import su.svn.showcase.domain.*;
import su.svn.showcase.interfaces.Updating;

import javax.annotation.Nonnull;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

/**
 * The DTO of LinkDescription is interface as a contract.
 *
 * @author Victor N. Skurikhin
 */
public interface LinkDescriptionDto extends Dto<UUID>, Updating<LinkDescription> {

    LocalDateTime getDateTime();

    void setDateTime(LocalDateTime dateTime);

    String getDescription();

    void setDescription(String description);

    String getDetails();

    void setDetails(String details);

    LinkDescription update(@NotNull LinkDescription entity, UserLogin userLogin);

    default LinkDescription update(@Nonnull LinkDescription entity, @Nonnull Map<String, Object> values) {
        convertIfContainsKey(NewsLinks.class, values, "newsLinks").ifPresent(entity::setNewsLinks);
        convertIfContainsKey(Link.class, values, "link").ifPresent(entity::setLink);

        return update(entity);
    }
}
//EOF
