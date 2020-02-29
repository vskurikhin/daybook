/*
 * This file was last modified at 2020.03.01 00:04 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * NewsEntryBaseDto.java
 * $Id$
 */

package su.svn.showcase.dto;

import lombok.*;
import su.svn.showcase.domain.NewsEntry;
import su.svn.showcase.domain.UserLogin;

import javax.annotation.Nonnull;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * The base DTO of NewsEntry.
 *
 * @author Victor N. Skurikhin
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NewsEntryBaseDto implements NewsEntryDto, Serializable {

    private static final long serialVersionUID = 9250L;

    @NotNull
    private UUID id;

    @NotNull
    private LocalDateTime dateTime;

    @NotNull
    @Size(min = 1, max = 128)
    private String title;

    @Size(min = 1, max = 1024)
    private String content;

    public NewsEntryBaseDto(@NotNull NewsEntry entity) {
        assert entity != null;
        this.id = entity.getId();
        this.dateTime = entity.getDateTime();
        this.title = entity.getTitle();
        this.content = entity.getContent();
    }

    @Override
    public Class<? extends Dto> getDtoClass() {
        return NewsEntryDto.class;
    }

    @Override
    public NewsEntry update(@Nonnull NewsEntry entity) {
        updateIfNotNull(() -> entity.setDateTime(this.dateTime), this.dateTime);
        updateIfNotNull(() -> entity.setTitle(this.title), this.title);
        entity.setContent(this.content);

        return entity;
    }

    @Override
    public NewsEntry update(@Nonnull NewsEntry entity, @Nonnull UserLogin userLogin) {
        return update(entity);
    }
}
//EOF
