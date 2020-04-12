/*
 * This file was last modified at 2020.04.12 13:16 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * LinkDescriptionJdo.java
 * $Id$
 */

package su.svn.showcase.dto.jdo;

import lombok.*;
import su.svn.showcase.domain.*;
import su.svn.showcase.dto.*;

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
@EqualsAndHashCode(exclude = {"newsLinks"})
@ToString(exclude = {"newsLinks"})
public class LinkDescriptionJdo implements LinkDescriptionDto, Serializable {

    private static final long serialVersionUID = 9210L;

    @NotNull
    private UUID id;

    private NewsLinksDto newsLinks;

    private LinkDto link;

    private LocalDateTime dateTime;

    @Size(max = 128)
    private String description;

    @Size(max = 8192)
    private String details;

    public LinkDescriptionJdo(@Nonnull UUID id) {
        this.id = id;
    }

    @Override
    public Class<LinkDescriptionJdo> getDtoClass() {
        return LinkDescriptionJdo.class;
    }

    @Deprecated
    public LinkDescriptionJdo(@Nonnull LinkDescription entity) {
        this.id = entity.getId();
        this.newsLinks = new NewsLinksBaseDto(entity.getNewsLinks());
        this.link = new LinkJdo(entity.getLink());
        this.dateTime = entity.getDateTime();
        this.description = entity.getDescription();
        this.details = entity.getDescription();
    }

    @Deprecated
    @Override
    public LinkDescription update(@Nonnull LinkDescription entity) {
        if (this.newsLinks != null) {
            NewsLinks newsLinks = new NewsLinks(this.newsLinks.getId());
            entity.setNewsLinks(this.newsLinks.update(newsLinks));
        }
        if (this.link != null) {
            Link link = new Link(this.link.getId());
            entity.setLink(this.link.update(link));
        }
        updateIfNotNull(entity::setDateTime, this.dateTime);
        updateIfNotNull(entity::setDescription, this.description);
        updateIfNotNull(entity::setDetails, this.details);

        return entity;
    }

    @Deprecated
    @Override
    public LinkDescription update(@Nonnull LinkDescription entity, UserLogin userLogin) {
        return update(entity);
    }
}
//EOF
