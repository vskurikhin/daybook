/*
 * This file was last modified at 2020.03.03 20:33 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * TagBaseDto.java
 * $Id$
 */

package su.svn.showcase.dto;

import lombok.*;
import su.svn.showcase.domain.Tag;

import javax.annotation.Nonnull;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * The base DTO of Tag.
 *
 * @author Victor N. Skurikhin
 */
@Data
@Builder
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

    public TagBaseDto(@NotNull Tag entity) {
        assert entity != null;
        this.id = entity.getId();
        this.tag = entity.getTag();
        this.visible = entity.getVisible();
        this.dateTime = entity.getDateTime();
    }

    @Override
    public Class<TagBaseDto> getDtoClass() {
        return TagBaseDto.class;
    }

    @Override
    public Tag update(@Nonnull Tag entity) {
        updateIfNotNull(entity::setTag, this.tag);
        updateIfNotNull(entity::setDateTime, this.dateTime);
        entity.setVisible(this.visible != null ? this.visible : false);

        return entity;
    }
}
//EOF
