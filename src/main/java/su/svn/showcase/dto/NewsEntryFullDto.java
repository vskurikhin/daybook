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
import su.svn.showcase.utils.MapUtil;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

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
        return null;
    }

    @Override
    public NewsEntry update(@NotNull NewsEntry entity) {
        assert entity != null;
        entity.setDateTime(this.dateTime);
        entity.setTitle(this.title);
        entity.setContent(this.content);
        assert this.record != null;
        if (this.record instanceof RecordBaseDto) {
            entity.setRecord(updateRecord(this.record));
        } else if (this.record instanceof RecordFullDto) {
            UserLoginDto userLoginDto = ((RecordFullDto) this.record).getUserLogin();
            UserLogin userLogin = updateUserLogin(userLoginDto);
            Set<Tag> tags = getTags((RecordFullDto) this.record);
            Record record = Record.builder()
                    .id(this.record.getId())
                    .index(this.record.getIndex())
                    .type(this.record.getType())
                    .userLogin(userLogin)
                    .createDateTime(this.record.getCreateDateTime())
                    .editDateTime(this.record.getEditDateTime())
                    .tags(tags)
                    .build();
            entity.setRecord(record);
        }
        entity.getRecord().setNewsEntry(entity);
        assert this.newsGroup != null;
        entity.setNewsGroup(this.newsGroup.update(new NewsGroup(this.newsGroup.getId())));

        return entity;
    }

    private Set<Tag> getTags(@NotNull RecordFullDto record) {
        return record.getTags().stream()
                        .map(this::updateTag)
                        .collect(Collectors.toSet());
    }

    private Tag updateTag(TagDto dto) {
        return dto.update(new Tag(dto.getId()));
    }

    private Record updateRecord(RecordDto dto) {
        return dto.update(new Record(dto.getId()));
    }

    private UserLogin updateUserLogin(UserLoginDto dto) {
        return dto.update(new UserLogin(dto.getId()));
    }
}
//EOF
