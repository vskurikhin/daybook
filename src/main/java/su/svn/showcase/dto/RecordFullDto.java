/*
 * This file was last modified at 2020.03.01 00:04 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * RecordFullDto.java
 * $Id$
 */

package su.svn.showcase.dto;

import lombok.*;
import su.svn.showcase.domain.NewsEntry;
import su.svn.showcase.domain.Record;
import su.svn.showcase.domain.Tag;

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
public class RecordFullDto implements RecordDto, Serializable {

    private static final long serialVersionUID = 9241L;

    @NotNull
    private UUID id;

    @NotNull
    private LocalDateTime createDateTime;

    @NotNull
    private LocalDateTime editDateTime;

    private Integer index;

    @NotNull
    private String type;

    @NotNull
    private UserLoginDto userLogin;

    @NotNull
    private NewsEntryDto newsEntry;

    @Valid
    @NotNull
    private Set<TagDto> tags;

    public RecordFullDto(@NotNull Record entity) {
        assert entity != null;
        this.id = entity.getId();
        this.createDateTime = entity.getCreateDateTime();
        this.editDateTime = entity.getEditDateTime();
        this.index = entity.getIndex();
        this.type = entity.getType();
        this.userLogin = new UserOnlyLoginBaseDto(entity.getUserLogin());
        this.newsEntry = new NewsEntryBaseDto(entity.getNewsEntry());
        this.tags = entity.getTags().stream()
                .map(TagBaseDto::new)
                .collect(Collectors.toSet());
    }

    @Override
    public Class<? extends Dto> getDtoClass() {
        return RecordFullDto.class;
    }

    @Override
    public Record update(@Nonnull Record entity) {
        updateIfNotNull(() -> entity.setCreateDateTime(this.createDateTime), this.createDateTime);
        updateIfNotNull(() -> entity.setEditDateTime(this.editDateTime), this.editDateTime);
        updateIfNotNull(() -> entity.setIndex(this.index), this.index);
        updateIfNotNull(() -> entity.setType(this.type), this.type);

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
        }
        return entity;
    }

    private Record updateByNewsEntryBaseDto(@Nonnull Record entity) {
        assert this.newsEntry != null;
        entity.setNewsEntry(this.newsEntry.update(new NewsEntry(this.newsEntry.getId())));
        entity.getNewsEntry().setRecord(entity);

        return entity;
    }
}
//EOF
