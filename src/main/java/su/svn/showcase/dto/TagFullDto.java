/*
 * This file was last modified at 2020.02.15 14:30 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * TagFullDto.java$
 * $Id$
 */

package su.svn.showcase.dto;

import lombok.*;
import su.svn.showcase.domain.Record;
import su.svn.showcase.domain.Tag;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * The extended DTO of Tag.
 *
 * @author Victor N. Skurikhin
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TagFullDto implements TagDto, Serializable {

    private static final long serialVersionUID = 9131L;

    @NotNull
    private String id;

    @NotNull
    @Size(min = 1, max = 128)
    private String tag;

    private Boolean visible;

    @NotNull
    private LocalDateTime dateTime;

    @Valid
    @NotNull
    Set<RecordDto> records;

    public TagFullDto(@NotNull Tag entity) {
        assert entity != null;
        this.id = entity.getId();
        this.tag = entity.getTag();
        this.visible = entity.getVisible();
        this.dateTime = entity.getDateTime();
        this.records = entity.getRecords().stream()
                .map(RecordBaseDto::new)
                .collect(Collectors.toSet());
    }

    @Override
    public Class<? extends Dto> getDtoClass() {
        return TagFullDto.class;
    }

    @Override
    public Tag update(@NotNull Tag entity) {
        assert entity != null;
        entity.setTag(this.tag);
        entity.setDateTime(this.dateTime);
        entity.setVisible(this.visible != null ? this.visible : false);

        if (records != null) {
            this.records.forEach(dto -> dto.update(new Record(dto.getId())));
        }
        return entity;
    }
}
//EOF
