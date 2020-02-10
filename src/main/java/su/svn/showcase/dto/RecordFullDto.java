/*
 * This file was last modified at 2020.02.10 21:22 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * RecordFullDto.java
 * $Id$
 */

package su.svn.showcase.dto;

import lombok.*;
import su.svn.showcase.domain.Record;
import su.svn.showcase.domain.Tag;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * The extended DTO of Record.
 *
 * @author Victor N. Skurikhin
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class RecordFullDto extends UUIDDto implements RecordDto, Serializable {

    private static final long serialVersionUID = 9241L;

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
    @Valid
    Set<TagDto> tags;

    @Builder
    public RecordFullDto(
            @NotNull UUID id,
            @NotNull LocalDateTime createDateTime,
            @NotNull LocalDateTime editDateTime,
            int index,
            @NotNull String type,
            @NotNull UserLoginBaseDto userLogin,
            @NotNull @Valid Set<TagDto> tags) {
        super(id);
        this.createDateTime = createDateTime;
        this.editDateTime = editDateTime;
        this.index = index;
        this.type = type;
        this.userLogin = userLogin;
        this.tags = tags;
    }

    public RecordFullDto(@NotNull Record entity) {
        super(Objects.requireNonNull(entity).getId());
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

    private Consumer<TagDto> updatingConsumer(Map<String, Tag> mapEntities) {
        return dto -> {
            Tag tag = mapEntities.get(dto.getId());
            if (tag != null) {
                dto.update(tag);
            }
        };
    }

    @Override
    public Record update(@NotNull Record entity) {
        Objects.requireNonNull(entity);
        entity.setId(getId());
        entity.setCreateDateTime(this.createDateTime);
        entity.setEditDateTime(this.editDateTime);
        entity.setIndex(this.index);
        entity.setType(this.type);

        this.userLogin.update(entity.getUserLogin());
        if (tags != null && ! tags.isEmpty() && entity.getTags() != null) {
            final Map<String, Tag> mapEntities = toEntitiesMap(entity.getTags());
            tags.forEach(updatingConsumer(mapEntities));
        }
        return entity;
    }
}
//EOF
