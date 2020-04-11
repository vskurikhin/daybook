/*
 * This file was last modified at 2020.04.11 11:07 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * ArticleBaseDto.java
 * $Id$
 */

package su.svn.showcase.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import su.svn.showcase.domain.Article;
import su.svn.showcase.domain.UserLogin;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * The base DTO of Article.
 *
 * @author Victor N. Skurikhin
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArticleBaseDto implements ArticleDto, Serializable {

    private static final long serialVersionUID = 9290L;

    @NotNull
    private UUID id;

    private LocalDateTime dateTime;

    @Size(max = 128)
    private String title;

    @Size(max = 128)
    private String include;

    @Size(max = 128)
    private String anchor;

    private String summary;

    public ArticleBaseDto(@Nonnull Article entity) {
        this.id = entity.getId();
        this.dateTime = entity.getDateTime();
        this.title = entity.getTitle();
        this.include = entity.getInclude();
        this.anchor = entity.getAnchor();
        this.summary = entity.getSummary();
    }

    @Override
    public Class<ArticleBaseDto> getDtoClass() {
        return ArticleBaseDto.class;
    }

    @Override
    public Article update(@Nonnull Article entity) {
        updateIfNotNull(entity::setDateTime, this.dateTime);
        updateIfNotNull(entity::setTitle, this.title);
        updateIfNotNull(entity::setInclude, this.include);
        updateIfNotNull(entity::setAnchor, this.anchor);
        updateIfNotNull(entity::setSummary, this.summary);

        return entity;
    }

    @Deprecated
    @Override
    public Article update(@Nonnull Article entity, @Nonnull UserLogin userLogin) {
        return update(entity);
    }
}
//EOF
