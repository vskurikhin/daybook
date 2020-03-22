/*
 * This file was last modified at 2020.03.03 20:33 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * NewsGroupBaseDto.java
 * $Id$
 */

package su.svn.showcase.dto;

import lombok.*;
import su.svn.showcase.domain.NewsGroup;

import javax.annotation.Nonnull;
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
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NewsGroupBaseDto implements NewsGroupDto, Serializable {

    private static final long serialVersionUID = 9230L;

    @NotNull
    private UUID id;

    private LocalDateTime dateTime;

    @Size(min = 1, max = 64)
    private String group;

    public NewsGroupBaseDto(@Nonnull NewsGroup entity) {
        this.id = entity.getId();
        this.dateTime = entity.getDateTime();
        this.group = entity.getGroup();
    }

    @Override
    public Class<? extends Dto> getDtoClass() {
        return NewsGroupBaseDto.class;
    }

    @Override
    public NewsGroup update(@Nonnull NewsGroup entity) {
        updateIfNotNull(entity::setDateTime, this.dateTime);
        updateIfNotNull(entity::setGroup, this.group);

        return entity;
    }
}
//EOF
