/*
 * This file was last modified at 2020.03.03 20:33 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * RecordBaseDto.java
 * $Id$
 */

package su.svn.showcase.dto;

import lombok.*;
import su.svn.showcase.domain.Record;

import javax.annotation.Nonnull;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
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

    private LocalDateTime createDateTime;

    private LocalDateTime editDateTime;

    private Integer index;

    private String type;

    public RecordBaseDto(@Nonnull Record entity) {
        this.id = entity.getId();
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
    public Record update(@Nonnull Record entity) {
        updateIfNotNull(entity::setCreateDateTime, this.createDateTime);
        updateIfNotNull(entity::setEditDateTime, this.editDateTime);
        updateIfNotNull(entity::setIndex, this.index);
        updateIfNotNull(entity::setType, this.type);

        return entity;
    }
}
//EOF
