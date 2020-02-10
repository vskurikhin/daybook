/*
 * This file was last modified at 2020.02.09 20:38 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * NewsEntryBase.java
 * $Id$
 */

package su.svn.showcase.dto;

import lombok.*;
import su.svn.showcase.domain.NewsEntry;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class NewsEntryBaseDto extends UUIDDto implements NewsEntryDto, Serializable {

    private static final long serialVersionUID = 9240L;

    @NotNull
    private LocalDateTime dateTime;

    @NotNull
    @Size(min = 1, max = 128)
    private String title;

    @Size(min = 1, max = 1024)
    private String content;

    @Builder
    public NewsEntryBaseDto(
            @NotNull UUID id,
            @NotNull LocalDateTime dateTime,
            @NotNull String title,
            String content) {
        super(id);
        this.dateTime = dateTime;
        this.title = title;
        this.content = content;
    }

    public NewsEntryBaseDto(@NotNull NewsEntry entity) {
        super(Objects.requireNonNull(entity).getId());
        this.dateTime = entity.getDateTime();
        this.title = entity.getTitle();
        this.content = entity.getContent();
    }

    @Override
    public Class<? extends Dto> getDtoClass() {
        return NewsEntryDto.class;
    }

    @Override
    public NewsEntry update(@NotNull NewsEntry entity) {
        Objects.requireNonNull(entity);
        entity.setId(getId());
        entity.setDateTime(this.dateTime);
        entity.setTitle(this.title);
        entity.setContent(this.content);

        return entity;
    }
}
//EOF
