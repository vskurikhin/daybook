/*
 * This file was last modified at 2020.04.12 15:34 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * RecordFullDto.java
 * $Id$
 */

package su.svn.showcase.dto;

import lombok.*;
import su.svn.showcase.domain.*;
import su.svn.showcase.dto.enums.RecordTypesEnum;
import su.svn.showcase.dto.jdo.ArticleJdo;
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
public class RecordFullDto implements RecordDto, Serializable, Typing {

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

    public RecordFullDto(@Nonnull Record entity) {
        this.id = entity.getId();
        this.createDateTime = entity.getCreateDateTime();
        this.editDateTime = entity.getEditDateTime();
        this.index = entity.getIndex();
        this.type = entity.getType();
        this.userLogin = new UserOnlyLoginDto(entity.getUserLogin());
        RecordTypesEnum type = RecordTypesEnum.valueOf(entity.getType());
        switch (type) {
            case NewsEntryBaseDto:
            case NewsEntryFullDto:
                NewsEntryFullDto newsEntryDto = new NewsEntryFullDto(entity.getNewsEntry());
                newsEntryDto.setRecord(this);
                this.newsEntry = newsEntryDto;
                break;
            case NewsLinksBaseDto:
            case NewsLinksFullDto:
                NewsLinksFullDto newsLinksDto = new NewsLinksFullDto(entity.getNewsLinks());
                newsLinksDto.setRecord(this);
                this.newsLinks = new NewsLinksFullDto(entity.getNewsLinks());
                break;
            case ArticleJdo:
                ArticleJdo articleDto = new ArticleJdo(entity.getArticle());
                articleDto.setRecord(this);
                this.article = articleDto;
                break;
        }
        this.tags = entity.getTags().stream()
                .map(TagBaseDto::new)
                .collect(Collectors.toSet());
    }

    public RecordFullDto(UUID id) {
        this.id = id;
    }

    @Override
    public Class<RecordFullDto> getDtoClass() {
        return RecordFullDto.class;
    }

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
            case NewsEntryBaseDto:
            case NewsEntryFullDto:
                return updateByNewsEntryBaseDto(entity);
            case NewsLinksBaseDto:
            case NewsLinksFullDto:
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
