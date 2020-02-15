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
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NewsGroupBaseDto implements NewsGroupDto, Serializable {

    private static final long serialVersionUID = 9230L;

    @NotNull
    private UUID id;

    @NotNull
    private LocalDateTime dateTime;

    @NotNull
    @Size(min = 1, max = 64)
    private String group;

    public NewsGroupBaseDto(@NotNull NewsGroup entity) {
        assert entity != null;
        this.id = entity.getId();
        this.dateTime = entity.getDateTime();
        this.group = entity.getGroup();
    }

    @Override
    public Class<? extends Dto> getDtoClass() {
        return NewsGroupBaseDto.class;
    }

    @Override
    public NewsGroup update(@NotNull NewsGroup entity) {
        assert entity != null;
        entity.setDateTime(this.dateTime);
        entity.setGroup(this.group);

        return entity;
    }
}
//EOF
