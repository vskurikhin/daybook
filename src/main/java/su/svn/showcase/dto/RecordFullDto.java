/*
 * This file was last modified at 2020.02.21 22:20 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * RecordFullDto.java
 * $Id$
 */

package su.svn.showcase.dto;

import lombok.*;
import su.svn.showcase.domain.Record;
import su.svn.showcase.domain.Tag;
import su.svn.showcase.domain.UserLogin;

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
        this.userLogin = new UserLoginBaseDto(entity.getUserLogin());
        this.tags = entity.getTags().stream()
                .map(TagBaseDto::new)
                .collect(Collectors.toSet());
    }

    @Override
    public Class<? extends Dto> getDtoClass() {
        return null;
    }

    @Override
    public Record update(@NotNull Record entity) {
        assert entity != null;
        entity.setCreateDateTime(this.createDateTime);
        entity.setEditDateTime(this.editDateTime);
        entity.setIndex(this.index);
        entity.setType(this.type);
        assert this.userLogin != null;
        entity.setUserLogin(this.userLogin.update(new UserLogin(this.userLogin.getId())));
        if (this.tags != null) {
            Set<Tag> tags = this.tags.stream()
                    .map(dto -> dto.update(new Tag(dto.getId())))
                    .collect(Collectors.toSet());
            entity.setTags(tags);
        } else {
            entity.setTags(Collections.emptySet());
        }
        return entity;
    }
}
//EOF
