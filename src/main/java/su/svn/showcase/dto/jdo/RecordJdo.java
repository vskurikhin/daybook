/*
 * This file was last modified at 2020.04.14 21:45 by Victor N. Skurikhin.
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
import su.svn.showcase.domain.Article;
import su.svn.showcase.domain.NewsEntry;
import su.svn.showcase.domain.NewsLinks;
import su.svn.showcase.domain.Record;
import su.svn.showcase.domain.Tag;
import su.svn.showcase.dto.ArticleDto;
import su.svn.showcase.dto.NewsEntryDto;
import su.svn.showcase.dto.NewsLinksDto;
import su.svn.showcase.dto.RecordDto;
import su.svn.showcase.dto.TagDto;
import su.svn.showcase.dto.UserLoginDto;
import su.svn.showcase.dto.UserOnlyLoginDto;
import su.svn.showcase.dto.enums.RecordTypesEnum;
import su.svn.showcase.interfaces.Typing;

import javax.annotation.Nonnull;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

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

    @Deprecated
    public RecordJdo(@Nonnull Record entity) {
        this.id = entity.getId();
        this.createDateTime = entity.getCreateDateTime();
        this.editDateTime = entity.getEditDateTime();
        this.index = entity.getIndex();
        this.type = entity.getType();
        this.userLogin = new UserOnlyLoginDto(entity.getUserLogin());
        RecordTypesEnum type = RecordTypesEnum.valueOf(entity.getType());
        switch (type) {
            case NewsEntryJdo:
                NewsEntryJdo newsEntryDto = new NewsEntryJdo(entity.getNewsEntry());
                newsEntryDto.setRecord(this);
                this.newsEntry = newsEntryDto;
                break;
            case NewsLinksJdo:
                NewsLinksJdo newsLinksDto = new NewsLinksJdo(entity.getNewsLinks());
                newsLinksDto.setRecord(this);
                this.newsLinks = new NewsLinksJdo(entity.getNewsLinks());
                break;
            case ArticleJdo:
                ArticleJdo articleDto = new ArticleJdo(entity.getArticle());
                articleDto.setRecord(this);
                this.article = articleDto;
                break;
        }
        this.tags = entity.getTags().stream()
                .map(TagJdo::new)
                .collect(Collectors.toSet());
    }

    @Deprecated
    @Override
    public Record update(@Nonnull Record entity) {
        updateIfNotNull(entity::setCreateDateTime, this.createDateTime);
        updateIfNotNull(entity::setEditDateTime, this.editDateTime);
        updateIfNotNull(entity::setIndex, this.index);
        updateIfNotNull(entity::setType, this.type);

        assert this.userLogin != null;
        assert entity.getUserLogin() != null;
        entity.setUserLogin(this.userLogin.update(entity.getUserLogin()));

        if (this.tags != null) {
            Set<Tag> tags = this.tags.stream()
                    .map(dto -> dto.update(new Tag(dto.getId())))
                    .collect(Collectors.toSet());
            entity.setTags(tags);
        } else {
            entity.setTags(Collections.emptySet());
        }
        return updateByType(entity);
    }

    private Record updateByType(@Nonnull Record entity) {
        RecordTypesEnum type = RecordTypesEnum.valueOf(entity.getType());
        switch (type) {
            case NewsEntryJdo:
                return updateByNewsEntryBaseDto(entity);
            case NewsLinksJdo:
                return updateByNewsLinksBaseDto(entity);
            case ArticleJdo:
                return updateByArticleBaseDto(entity);
        }
        return entity;
    }

    private Record updateByNewsLinksBaseDto(Record entity) {
        if (this.newsLinks != null) {
            NewsLinks newsLinks = new NewsLinks(this.newsLinks.getId());
            entity.setNewsLinks(this.newsLinks.update(newsLinks));
            entity.getNewsLinks().setRecord(entity);
        }
        return entity;
    }

    private Record updateByNewsEntryBaseDto(@Nonnull Record entity) {
        if (this.newsEntry != null) {
            NewsEntry newsEntry = new NewsEntry(this.newsEntry.getId());
            entity.setNewsEntry(this.newsEntry.update(newsEntry));
            entity.getNewsEntry().setRecord(entity);
        }
        return entity;
    }

    private Record updateByArticleBaseDto(@Nonnull Record entity) {
        if (this.article != null) {
            Article article = new Article(this.article.getId());
            entity.setArticle(this.article.update(article));
            entity.getArticle().setRecord(entity);
        }
        return entity;
    }
}
//EOF
