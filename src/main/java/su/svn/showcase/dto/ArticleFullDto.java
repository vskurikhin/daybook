/*
 * This file was last modified at 2020.03.20 19:57 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * ArticleFullDto.java
 * $Id$
 */

package su.svn.showcase.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import su.svn.showcase.domain.Article;
import su.svn.showcase.domain.Link;
import su.svn.showcase.domain.Record;
import su.svn.showcase.domain.UserLogin;

import javax.annotation.Nonnull;
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
public class ArticleFullDto implements ArticleDto, Serializable {

    private static final long serialVersionUID = 9291L;

    @NotNull
    private UUID id;

    private LocalDateTime dateTime;

    @Size(max = 128)
    private String title;

    @Size(max = 128)
    private String include;

    private String summary;

    private RecordDto record;

    private LinkDto link;

    public ArticleFullDto(@Nonnull Article entity) {
        this.id = entity.getId();
        this.dateTime = entity.getDateTime();
        this.title = entity.getTitle();
        this.include = entity.getInclude();
        this.summary = entity.getSummary();
        this.record = new RecordFullDto(entity.getRecord());
        this.link = new LinkBaseDto(entity.getLink());
    }

    @Override
    public Class<? extends Dto> getDtoClass() {
        return ArticleDto.class;
    }

    @Override
    public Article update(@Nonnull Article entity) {
        if (this.record instanceof RecordBaseDto) {
            entity.setRecord(updateRecord(this.record));
            entity.getRecord().setArticle(entity);
        }
        if (this.link instanceof LinkBaseDto) {
            entity.setLink(updateLink(this.link));
            entity.getLink().setArticle(entity);
        }
        updateIfNotNull(entity::setDateTime, this.dateTime);
        updateIfNotNull(entity::setTitle, this.title);
        updateIfNotNull(entity::setInclude, this.include);
        updateIfNotNull(entity::setSummary, this.summary);

        return entity;
    }

    @Override
    public Article update(@Nonnull Article entity, @Nonnull UserLogin userLogin) {
        assert this.record != null;
        if (this.record instanceof RecordFullDto) {
            entity.setRecord(updateRecord((RecordFullDto)this.record, userLogin));
        }

        return update(entity);
    }

    private Record updateRecord(RecordDto dto) {
        return dto.update(new Record(dto.getId()));
    }

    private Record updateRecord(RecordFullDto dto, UserLogin userLogin) {
        return dto.update(new Record(dto.getId(), userLogin));
    }

    private Link updateLink(LinkDto dto) {
        return dto.update(new Link(dto.getId()));
    }
}
//EOF
