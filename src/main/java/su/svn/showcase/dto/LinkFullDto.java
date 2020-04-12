/*
 * This file was last modified at 2020.04.12 11:21 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * LinkFullDto.java
 * $Id$
 */

package su.svn.showcase.dto;

import lombok.*;
import su.svn.showcase.domain.Article;
import su.svn.showcase.domain.LinkDescription;
import su.svn.showcase.domain.Link;
import su.svn.showcase.dto.jdo.ArticleJdo;
import su.svn.showcase.dto.jdo.LinkDescriptionJdo;

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
public class LinkFullDto implements LinkDto, Serializable {

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

    @Deprecated
    public LinkFullDto(@Nonnull Link entity) {
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

    @Override
    public Class<LinkFullDto> getDtoClass() {
        return LinkFullDto.class;
    }

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
