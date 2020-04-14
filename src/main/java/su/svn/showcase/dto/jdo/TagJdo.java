/*
 * This file was last modified at 2020.04.14 20:47 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * TagJdo.java
 * $Id$
 */

package su.svn.showcase.dto.jdo;

import lombok.*;
import su.svn.showcase.domain.Record;
import su.svn.showcase.domain.Tag;
import su.svn.showcase.dto.RecordBaseDto;
import su.svn.showcase.dto.RecordDto;
import su.svn.showcase.dto.TagDto;

import javax.annotation.Nonnull;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;
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
@EqualsAndHashCode(exclude = {"records"})
@ToString(exclude = {"records"})
public class TagJdo implements TagDto, Serializable {

    private static final long serialVersionUID = 9131L;

    @NotNull
    private String id;

    @Size(min = 1, max = 128)
    private String tag;

    private Boolean visible;

    private LocalDateTime dateTime;

    @Valid
    private Set<RecordDto> records;

    public TagJdo(@Nonnull String id) {
        this.id = id;
    }

    @Override
    public Class<TagJdo> getDtoClass() {
        return TagJdo.class;
    }

    @Deprecated
    public TagJdo(@Nonnull Tag entity) {
        this.id = entity.getId();
        this.tag = entity.getTag();
        this.visible = entity.getVisible();
        this.dateTime = entity.getDateTime();
        this.records = entity.getRecords().stream()
                .map(RecordBaseDto::new)
                .collect(Collectors.toSet());
    }

    @Deprecated
    @Override
    public Tag update(@Nonnull Tag entity) {
        updateIfNotNull(entity::setTag, this.tag);
        updateIfNotNull(entity::setDateTime, this.dateTime);
        entity.setVisible(this.visible != null ? this.visible : false);
        if (this.records != null) {
            Set<Record> records = this.records.stream()
                    .map(dto -> dto.update(new Record(dto.getId())))
                    .collect(Collectors.toSet());
            entity.setRecords(records);
        } else {
            entity.setRecords(Collections.emptySet());
        }
        return entity;
    }
}
//EOF
