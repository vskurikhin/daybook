/*
 * This file was last modified at 2020.04.01 15:09 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * LinkBaseDto.java
 * $Id$
 */

package su.svn.showcase.dto;

import lombok.*;
import su.svn.showcase.domain.Link;

import javax.annotation.Nonnull;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * The extended DTO of Link.
 *
 * @author Victor N. Skurikhin
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LinkBaseDto implements LinkDto, Serializable {

    private static final long serialVersionUID = 9271L;

    @NotNull
    private UUID id;

    private LocalDateTime dateTime;

    private Boolean visible;

    @Size(max = 512)
    private String link;

    public LinkBaseDto(@Nonnull Link entity) {
        this.id = entity.getId();
        this.visible = entity.getVisible();
        this.dateTime = entity.getDateTime();
        this.link = entity.getLink();
    }

    @Override
    public Class<LinkBaseDto> getDtoClass() {
        return LinkBaseDto.class;
    }

    @Override
    public Link update(@Nonnull Link entity) {
        updateIfNotNull(entity::setDateTime, this.dateTime);
        entity.setVisible(this.visible != null ? this.visible : false);
        updateIfNotNull(entity::setLink, this.link);

        return entity;
    }
}
//EOF
