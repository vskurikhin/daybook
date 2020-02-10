/*
 * This file was last modified at 2020.02.10 21:22 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * NewsGroupBaseDto.java
 * $Id$
 */

package su.svn.showcase.dto;

import lombok.*;
import su.svn.showcase.domain.NewsGroup;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;

/**
 * The base DTO of NewsGroup.
 *
 * @author Victor N. Skurikhin
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class NewsGroupBaseDto extends UUIDDto implements NewsGroupDto, Serializable {

    private static final long serialVersionUID = 9230L;

    @NotNull
    private LocalDateTime dateTime;

    @NotNull
    @Size(min = 1, max = 64)
    private String group;

    @Builder
    public NewsGroupBaseDto(@NotNull UUID id, @NotNull LocalDateTime dateTime, @NotNull String group) {
        super(id);
        this.dateTime = dateTime;
        this.group = group;
    }

    public NewsGroupBaseDto(@NotNull NewsGroup entity) {
        super(Objects.requireNonNull(entity).getId());
        this.dateTime = entity.getDateTime();
        this.group = entity.getGroup();
    }

    @Override
    public Class<? extends Dto> getDtoClass() {
        return NewsGroupBaseDto.class;
    }

    @Override
    public NewsGroup update(@NotNull NewsGroup entity) {
        Objects.requireNonNull(entity);
        entity.setId(getId());
        entity.setDateTime(this.dateTime);
        entity.setGroup(this.group);

        return entity;
    }
}
//EOF
