/*
 * This file was last modified at 2020.02.10 21:20 by Victor N. Skurikhin.
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
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class NewsEntryFullDto  extends UUIDDto implements NewsEntryDto, Serializable {

    private static final long serialVersionUID = 9241L;

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

    @Builder
    public NewsEntryFullDto(
            @NotNull UUID id,
            @NotNull LocalDateTime dateTime,
            @NotNull RecordDto record,
            @NotNull String title,
            String content,
            @NotNull NewsGroupDto newsGroup) {
        super(id);
        this.dateTime = dateTime;
        this.title = title;
        this.content = content;
        this.record = record;
        this.newsGroup = newsGroup;
    }

    public NewsEntryFullDto(@NotNull NewsEntry entity) {
        super(Objects.requireNonNull(entity).getId());
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
        Objects.requireNonNull(entity);
        entity.setId(getId());
        entity.setDateTime(this.dateTime);
        entity.setTitle(this.title);
        entity.setContent(this.content);
        this.record.update(entity.getRecord());
        if (entity.getNewsGroup() != null) {
            this.newsGroup.update(entity.getNewsGroup());
        }
        return entity;
    }
}
//EOF
