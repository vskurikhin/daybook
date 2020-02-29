/*
 * This file was last modified at 2020.02.21 22:20 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * NewsEntryFullDto.java
 * $Id$
 */

package su.svn.showcase.dto;

import lombok.*;
import su.svn.showcase.domain.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;

/**
 * The extended DTO of NewsEntry.
 *
 * @author Victor N. Skurikhin
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"record"})
@ToString(exclude = {"record"})
public class NewsEntryFullDto implements NewsEntryDto, Serializable {

    private static final long serialVersionUID = 9251L;

    @NotNull
    private UUID id;

    @NotNull
    private LocalDateTime dateTime;

    @NotNull
    @Size(min = 1, max = 128)
    private String title;

    @Size(min = 1, max = 1024)
    private String content;

    @NotNull
    private RecordDto record;

    @NotNull
    private NewsGroupDto newsGroup;

    public NewsEntryFullDto(@NotNull NewsEntry entity) {
        assert entity != null;
        this.id = entity.getId();
        this.dateTime = entity.getDateTime();
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.record = new RecordFullDto(entity.getRecord());
        this.newsGroup = entity.getNewsGroup() != null
                ? new NewsGroupBaseDto(entity.getNewsGroup())
                : null;
    }

    @Override
    public Class<? extends Dto> getDtoClass() {
        return NewsEntryFullDto.class;
    }

    @Override
    public NewsEntry update(@NotNull NewsEntry entity) {
        assert entity != null;
        assert this.record != null;
        if (this.record instanceof RecordBaseDto) {
            entity.setRecord(updateRecord(this.record));
        }
        entity.getRecord().setNewsEntry(entity);
        entity.setDateTime(this.dateTime);
        entity.setTitle(this.title);
        entity.setContent(this.content);
        assert this.newsGroup != null;
        entity.setNewsGroup(this.newsGroup.update(new NewsGroup(this.newsGroup.getId())));

        return entity;
    }

    @Override
    public NewsEntry update(@NotNull NewsEntry entity, UserLogin userLogin) {
        assert entity != null;
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
