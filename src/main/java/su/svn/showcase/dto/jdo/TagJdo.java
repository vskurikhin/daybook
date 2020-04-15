/*
 * This file was last modified at 2020.04.14 22:15 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * TagJdo.java
 * $Id$
 */

package su.svn.showcase.dto.jdo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import su.svn.showcase.dto.RecordDto;
import su.svn.showcase.dto.TagDto;

import javax.annotation.Nonnull;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

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
}
//EOF
