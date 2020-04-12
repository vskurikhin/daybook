/*
 * This file was last modified at 2020.04.12 13:16 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * LinkJdo.java
 * $Id$
 */

package su.svn.showcase.dto.jdo;

import lombok.*;
import su.svn.showcase.domain.Article;
import su.svn.showcase.domain.LinkDescription;
import su.svn.showcase.domain.Link;
import su.svn.showcase.dto.ArticleDto;
import su.svn.showcase.dto.LinkDescriptionDto;
import su.svn.showcase.dto.LinkDto;

import javax.annotation.Nonnull;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * The extended DTO of Link.
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

    @Deprecated
    public LinkJdo(@Nonnull Link entity) {
        this.id = entity.getId();
        this.article = entity.getArticle() != null
                ? new ArticleJdo(entity.getArticle())
                : null;
        this.dateTime = entity.getDateTime();
        this.visible = entity.getVisible();
        this.link = entity.getLink();
        this.descriptions = entity.getDescriptions().stream()
                .map(LinkDescriptionJdo::new)
                .collect(Collectors.toSet());
    }

    @Deprecated
    @Override
    public Link update(@Nonnull Link entity) {
        updateIfNotNull(entity::setLink, this.link);
        updateIfNotNull(entity::setDateTime, this.dateTime);
        entity.setVisible(this.visible != null ? this.visible : false);
        if (this.article != null) {
            Article article = new Article(this.article.getId());
            entity.setArticle(this.article.update(article));
        }
        if (this.descriptions != null) {
            Set<LinkDescription> records = this.descriptions.stream()
                    .map(dto -> dto.update(new LinkDescription(dto.getId())))
                    .collect(Collectors.toSet());
            entity.setDescriptions(records);
        } else {
            entity.setDescriptions(Collections.emptySet());
        }
        return entity;
    }
}
//EOF
