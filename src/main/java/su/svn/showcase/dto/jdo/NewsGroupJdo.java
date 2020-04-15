/*
 * This file was last modified at 2020.04.14 22:15 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * NewsGroupJdo.java
 * $Id$
 */

package su.svn.showcase.dto.jdo;

import lombok.*;
import su.svn.showcase.domain.NewsGroup;
import su.svn.showcase.dto.NewsEntryDto;
import su.svn.showcase.dto.NewsGroupDto;

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
public class NewsGroupJdo implements NewsGroupDto, Serializable {

    private static final long serialVersionUID = 9230L;

    @NotNull
    private UUID id;

    private LocalDateTime dateTime;

    @Size(min = 1, max = 64)
    private String group;

    private Set<NewsEntryDto> newsEntries;

    public NewsGroupJdo(@Nonnull UUID id) {
        this.id = id;
    }

    @Override
    public Class<NewsGroupJdo> getDtoClass() {
        return NewsGroupJdo.class;
    }
}
//EOF
