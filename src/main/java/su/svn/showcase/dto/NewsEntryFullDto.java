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
        return null;
    }

    @Override
    public NewsEntry update(@NotNull NewsEntry entity) {
        assert entity != null;
        entity.setDateTime(this.dateTime);
        entity.setTitle(this.title);
        entity.setContent(this.content);
        assert this.record != null;
        entity.setRecord(this.record.update(new Record(this.record.getId())));
        entity.getRecord().setNewsEntry(entity);
        assert this.newsGroup != null;
        entity.setNewsGroup(this.newsGroup.update(new NewsGroup(this.newsGroup.getId())));

        return entity;
    }
}
//EOF
