/*
 * This file was last modified at 2020.04.01 15:09 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * NewsGroupFullDto.java
 * $Id$
 */

package su.svn.showcase.dto;

import lombok.*;
import su.svn.showcase.domain.NewsGroup;

import javax.annotation.Nonnull;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

/**
 * The base DTO of NewsGroup.
 *
 * @author Victor N. Skurikhin
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NewsGroupFullDto implements NewsGroupDto, Serializable {

    private static final long serialVersionUID = 9230L;

    @NotNull
    private UUID id;

    private LocalDateTime dateTime;

    @Size(min = 1, max = 64)
    private String group;

    @Getter
    @Setter
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "newsGroup")
    private Set<NewsEntryDto> newsEntries;

    public NewsGroupFullDto(@Nonnull NewsGroup entity) {
        this.id = entity.getId();
        this.dateTime = entity.getDateTime();
        this.group = entity.getGroup();
    }

    @Override
    public Class<NewsGroupFullDto> getDtoClass() {
        return NewsGroupFullDto.class;
    }

    @Override
    public NewsGroup update(@Nonnull NewsGroup entity) {
        updateIfNotNull(entity::setDateTime, this.dateTime);
        updateIfNotNull(entity::setGroup, this.group);

        return entity;
    }
}
//EOF
