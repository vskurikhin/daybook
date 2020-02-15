/*
 * This file was last modified at 2020.02.10 21:23 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * TagBaseDto.java
 * $Id$
 */

package su.svn.showcase.dto;

import lombok.*;
import su.svn.showcase.domain.Tag;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;

/**
 * The base DTO of Tag.
 *
 * @author Victor N. Skurikhin
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TagBaseDto implements TagDto, Serializable {

    private static final long serialVersionUID = 9130L;

    @NotNull
    private String id;

    @NotNull
    @Size(min = 1, max = 128)
    private String tag;

    private Boolean visible;

    @NotNull
    private LocalDateTime dateTime;

    public TagBaseDto(@NotNull Tag tag) {
        this.id = Objects.requireNonNull(tag).getId();
        this.tag = tag.getTag();
        this.visible = tag.getVisible();
        this.dateTime = tag.getDateTime();
    }

    @Override
    public Class<TagBaseDto> getDtoClass() {
        return TagBaseDto.class;
    }

    @Override
    public Tag update(@NotNull Tag entity) {
        Objects.requireNonNull(entity);
        entity.setId(this.id);
        entity.setTag(this.tag);
        entity.setDateTime(this.dateTime);
        entity.setVisible(this.visible != null ? this.visible : false);

        return entity;
    }
}
//EOF
