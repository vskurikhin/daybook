/*
 * This file was last modified at 2020.04.14 17:33 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * NewsEntryJdo.java
 * $Id$
 */

package su.svn.showcase.dto.jdo;

import lombok.*;
import su.svn.showcase.domain.*;
import su.svn.showcase.dto.*;

import javax.annotation.Nonnull;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;

/**
 * The extended JDO of NewsEntry.
 *
 * @author Victor N. Skurikhin
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"record"})
@ToString(exclude = {"record"})
public class NewsEntryJdo implements NewsEntryDto, Serializable {

    private static final long serialVersionUID = 9251L;

    @NotNull
    private UUID id;

    private LocalDateTime dateTime;

    @Size(min = 1, max = 128)
    private String title;

    @Size(min = 1, max = 1024)
    private String content;

    private RecordDto record;

    private NewsGroupDto newsGroup;

    public NewsEntryJdo(@NotNull UUID id) {
        this.id = id;
    }

    @Override
    public Class<NewsEntryJdo> getDtoClass() {
        return NewsEntryJdo.class;
    }

    @Deprecated
    public NewsEntryJdo(@NotNull NewsEntry entity) {
        assert entity != null;
        this.id = entity.getId();
        this.dateTime = entity.getDateTime();
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.newsGroup = entity.getNewsGroup() != null
                ? new NewsGroupBaseDto(entity.getNewsGroup())
                : null;
    }

    @Deprecated
    @Override
    public NewsEntry update(@Nonnull NewsEntry entity) {
        if (this.record instanceof RecordBaseDto) {
            entity.setRecord(updateRecord(this.record));
            entity.getRecord().setNewsEntry(entity);
        }
        updateIfNotNull(entity::setDateTime, this.dateTime);
        updateIfNotNull(entity::setTitle, this.title);
        entity.setContent(this.content);
        if (this.newsGroup != null) {
            NewsGroup newsGroup = new NewsGroup(this.newsGroup.getId());
            entity.setNewsGroup(this.newsGroup.update(newsGroup));
        }

        return entity;
    }

    @Deprecated
    @Override
    public NewsEntry update(@Nonnull NewsEntry entity, @Nonnull UserLogin userLogin) {
        assert this.record != null;
        if (this.record instanceof RecordFullDto) {
            entity.setRecord(updateRecord((RecordFullDto)this.record, userLogin));
        }

        return update(entity);
    }

    @Deprecated
    private Record updateRecord(RecordDto dto) {
        return dto.update(new Record(dto.getId()));
    }

    @Deprecated
    private Record updateRecord(RecordFullDto dto, UserLogin userLogin) {
        return dto.update(new Record(dto.getId(), userLogin));
    }
}
//EOF
