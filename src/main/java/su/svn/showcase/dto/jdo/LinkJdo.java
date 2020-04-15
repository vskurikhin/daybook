/*
 * This file was last modified at 2020.04.14 22:15 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * LinkJdo.java
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
import su.svn.showcase.dto.LinkDescriptionDto;
import su.svn.showcase.dto.LinkDto;

import javax.annotation.Nonnull;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

/**
 * The JDO of Link.
 *
 * @author Victor N. Skurikhin
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"descriptions"})
@ToString(exclude = {"descriptions"})
public class LinkJdo implements LinkDto, Serializable {

    private static final long serialVersionUID = 9270L;

    @NotNull
    private UUID id;

    private ArticleDto article;

    private LocalDateTime dateTime;

    private Boolean visible;

    @Size(max = 512)
    private String link;

    @Valid
    private Set<LinkDescriptionDto> descriptions;

    public LinkJdo(@Nonnull UUID id) {
        this.id = id;
    }

    @Override
    public Class<LinkJdo> getDtoClass() {
        return LinkJdo.class;
    }
}
//EOF
