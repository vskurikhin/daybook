/*
 * This file was last modified at 2020.03.16 17:13 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * LinkFullDto.java
 * $Id$
 */

package su.svn.showcase.dto;

import lombok.*;
import su.svn.showcase.domain.LinkDescription;
import su.svn.showcase.domain.Link;

import javax.annotation.Nonnull;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * The extended DTO of Link.
 *
 * @author Victor N. Skurikhin
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"descriptions"})
@ToString(exclude = {"descriptions"})
public class LinkFullDto implements LinkDto, Serializable {

    private static final long serialVersionUID = 9270L;

    @NotNull
    private UUID id;

    @Size(max = 128)
    private String tag;

    private Boolean visible;

    private LocalDateTime dateTime;

    private String link;

    @Valid
    private Set<LinkDescriptionDto> descriptions;

    public LinkFullDto(@Nonnull Link entity) {
        this.id = entity.getId();
        this.tag = entity.getLink();
        this.visible = entity.getVisible();
        this.dateTime = entity.getDateTime();
        this.descriptions = entity.getDescriptions().stream()
                .map(LinkDescriptionBaseDto::new)
                .collect(Collectors.toSet());
    }

    @Override
    public Class<? extends Dto> getDtoClass() {
        return LinkFullDto.class;
    }

    @Override
    public Link update(@Nonnull Link entity) {
        updateIfNotNull(entity::setLink, this.tag);
        updateIfNotNull(entity::setDateTime, this.dateTime);
        entity.setVisible(this.visible != null ? this.visible : false);
        if (this.descriptions != null) {
            Set<LinkDescription> records = this.descriptions.stream()
                    .map(dto -> dto.update(new LinkDescription(dto.getId())))
                    .collect(Collectors.toSet());
            entity.setDescriptions(records);
        } else {
            entity.setDescriptions(Collections.emptySet());
        }
        return entity;
    }
}
//EOF
