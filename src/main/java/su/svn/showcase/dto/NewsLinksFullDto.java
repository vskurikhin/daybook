/*
 * This file was last modified at 2020.03.21 23:39 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * NewsLinksFullDto.java
 * $Id$
 */

package su.svn.showcase.dto;

import lombok.*;
import su.svn.showcase.domain.NewsLinks;
import su.svn.showcase.domain.NewsGroup;
import su.svn.showcase.domain.Record;
import su.svn.showcase.domain.UserLogin;

import javax.annotation.Nonnull;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * The extended DTO of NewsLinks.
 *
 * @author Victor N. Skurikhin
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"record"})
@ToString(exclude = {"record"})
public class NewsLinksFullDto implements NewsLinksDto, Serializable {

    private static final long serialVersionUID = 9251L;

    @NotNull
    private UUID id;

    private LocalDateTime dateTime;

    @Size(max = 128)
    private String title;

    private RecordDto record;

    private NewsGroupDto newsGroup;

    public NewsLinksFullDto(@Nonnull NewsLinks entity) {
        this.id = entity.getId();
        this.dateTime = entity.getDateTime();
        this.title = entity.getTitle();
        this.newsGroup = entity.getNewsGroup() != null
                ? new NewsGroupBaseDto(entity.getNewsGroup())
                : null;
    }

    @Override
    public Class<? extends Dto> getDtoClass() {
        return NewsLinksFullDto.class;
    }

    @Override
    public NewsLinks update(@Nonnull NewsLinks entity) {
        if (this.record instanceof RecordBaseDto) {
            entity.setRecord(updateRecord(this.record));
            entity.getRecord().setNewsLinks(entity);
        }
        updateIfNotNull(entity::setDateTime, this.dateTime);
        updateIfNotNull(entity::setTitle, this.title);
        if (this.newsGroup != null) {
            NewsGroup newsGroup = new NewsGroup(this.newsGroup.getId());
            entity.setNewsGroup(this.newsGroup.update(newsGroup));
        }

        return entity;
    }

    @Override
    public NewsLinks update(@Nonnull NewsLinks entity, @Nonnull UserLogin userLogin) {
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
}
//EOF
