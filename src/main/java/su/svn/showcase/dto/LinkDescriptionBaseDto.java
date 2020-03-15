/*
 * This file was last modified at 2020.03.15 22:43 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * LinkDescriptionBaseDto.java
 * $Id$
 */

package su.svn.showcase.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import su.svn.showcase.domain.Link;
import su.svn.showcase.domain.LinkDescription;
import su.svn.showcase.domain.NewsLinks;
import su.svn.showcase.domain.UserLogin;

import javax.annotation.Nonnull;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * The base DTO of LinkDescription.
 *
 * @author Victor N. Skurikhin
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LinkDescriptionBaseDto implements LinkDescriptionDto, Serializable {

    private static final long serialVersionUID = 9210L;

    @NotNull
    private UUID id;

    @NotNull
    private LocalDateTime dateTime;

    @NotNull
    @Size(min = 1, max = 128)
    private String description;

    @NotNull
    @Size(min = 1, max = 8192)
    private String details;

    public LinkDescriptionBaseDto(@Nonnull LinkDescription entity) {
        this.id = entity.getId();
        this.dateTime = entity.getDateTime();
        this.description = entity.getDescription();
        this.details = entity.getDescription();
    }

    @Override
    public Class<? extends Dto> getDtoClass() {
        return LinkDescriptionBaseDto.class;
    }

    @Override
    public LinkDescription update(@Nonnull LinkDescription entity) {
        updateIfNotNull(entity::setDateTime, this.dateTime);
        updateIfNotNull(entity::setDescription, this.description);
        updateIfNotNull(entity::setDetails, this.details);

        return entity;
    }

    @Override
    public LinkDescription update(@NotNull LinkDescription entity, UserLogin userLogin) {
        return update(entity);
    }
}
//EOF
