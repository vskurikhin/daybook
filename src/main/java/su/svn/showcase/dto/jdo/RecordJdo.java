/*
 * This file was last modified at 2020.04.14 22:15 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * RecordJdo.java
 * $Id$
 */

package su.svn.showcase.dto.jdo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import su.svn.showcase.dto.ArticleDto;
import su.svn.showcase.dto.NewsEntryDto;
import su.svn.showcase.dto.NewsLinksDto;
import su.svn.showcase.dto.RecordDto;
import su.svn.showcase.dto.TagDto;
import su.svn.showcase.dto.UserLoginDto;
import su.svn.showcase.interfaces.Typing;

import javax.annotation.Nonnull;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

/**
 * The extended DTO of Record.
 *
 * @author Victor N. Skurikhin
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"tags"})
@ToString(exclude = {"tags"})
public class RecordJdo implements RecordDto, Serializable, Typing {

    private static final long serialVersionUID = 9241L;

    @NotNull
    private UUID id;

    private LocalDateTime createDateTime;

    private LocalDateTime editDateTime;

    private Integer index;

    private String type;

    private UserLoginDto userLogin;

    private NewsEntryDto newsEntry;

    private NewsLinksDto newsLinks;

    private ArticleDto article;

    @Valid
    private Set<TagDto> tags;

    public RecordJdo(@Nonnull UUID id) {
        this.id = id;
    }

    @Override
    public Class<RecordJdo> getDtoClass() {
        return RecordJdo.class;
    }
}
//EOF
