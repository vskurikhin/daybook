/*
 * This file was last modified at 2020.02.10 21:23 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * TagFullDto.java
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
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class TagFullDto extends StringDto implements TagDto, Serializable {

    private static final long serialVersionUID = 9131L;

    @NotNull
    @Size(min = 1, max = 128)
    private String tag;

    private Boolean visible;

    @NotNull
    private LocalDateTime dateTime;

    @Valid
    @NotNull
    Set<RecordDto> records;

    @Builder
    public TagFullDto(
            @NotNull String id,
            @NotNull String tag,
            Boolean visible,
            @NotNull LocalDateTime dateTime,
            @NotNull @Valid Set<RecordDto> records) {
        super(id);
        this.tag = tag;
        this.visible = visible;
        this.dateTime = dateTime;
        this.records = records;
    }

    public TagFullDto(@NotNull Tag tag) {
        super(Objects.requireNonNull(tag).getId());
        this.tag = tag.getTag();
        this.visible = tag.getVisible();
        this.dateTime = tag.getDateTime();
        this.records = tag.getRecords().stream()
                .map(RecordBaseDto::new)
                .collect(Collectors.toSet());
    }

    @Override
    public Class<? extends Dto> getDtoClass() {
        return TagFullDto.class;
    }

    private Consumer<RecordDto> updatingConsumer(Map<UUID, Record> mapEntities) {
        return dto -> dto.update(mapEntities.get(dto.getId()));
    }

    @Override
    public Tag update(@NotNull Tag entity) {
        Objects.requireNonNull(entity);
        entity.setId(getId());
        entity.setTag(this.tag);
        entity.setDateTime(this.dateTime);
        entity.setVisible(this.visible != null ? this.visible : false);

        if (records != null && entity.getRecords() != null) {
            final Map<UUID, Record> mapEntities = toEntitiesMap(entity.getRecords());
            this.records.forEach(updatingConsumer(mapEntities));
        }

        return entity;
    }
}
//EOF
