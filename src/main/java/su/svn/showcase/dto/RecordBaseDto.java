/*
 * This file was last modified at 2020.02.10 21:22 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * RecordBaseDto.java
 * $Id$
 */

package su.svn.showcase.dto;

import lombok.*;
import su.svn.showcase.domain.Record;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

/**
 * The base DTO of Record.
 *
 * @author Victor N. Skurikhin
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecordBaseDto implements RecordDto, Serializable {

    private static final long serialVersionUID = 9240L;

    @NotNull
    private UUID id;

    @NotNull
    private LocalDateTime createDateTime;

    @NotNull
    private LocalDateTime editDateTime;

    private Integer index;

    @NotNull
    private String type;

    public RecordBaseDto(@NotNull Record entity) {
        this.id = Objects.requireNonNull(entity).getId();
        this.createDateTime = entity.getCreateDateTime();
        this.editDateTime = entity.getEditDateTime();
        this.index = entity.getIndex();
        this.type = entity.getType();
    }

    @Override
    public Class<? extends Dto> getDtoClass() {
        return RecordBaseDto.class;
    }

    @Override
    public Record update(@NotNull Record entity) {
        Objects.requireNonNull(entity);
        entity.setId(this.id);
        entity.setCreateDateTime(this.createDateTime);
        entity.setEditDateTime(this.editDateTime);
        entity.setIndex(this.index);
        entity.setType(this.type);

        return entity;
    }
}
//EOF
