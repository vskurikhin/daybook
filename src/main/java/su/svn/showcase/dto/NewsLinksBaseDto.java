/*
 * This file was last modified at 2020.04.01 15:09 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * NewsLinksBaseDto.java
 * $Id$
 */

package su.svn.showcase.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import su.svn.showcase.domain.NewsLinks;
import su.svn.showcase.domain.UserLogin;

import javax.annotation.Nonnull;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * The base DTO of NewsLinks.
 *
 * @author Victor N. Skurikhin
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NewsLinksBaseDto implements NewsLinksDto, Serializable {

    private static final long serialVersionUID = 9260L;

    @NotNull
    private UUID id;

    private LocalDateTime dateTime;

    @Size(max = 128)
    private String title;

    public NewsLinksBaseDto(@Nonnull NewsLinks entity) {
        this.id = entity.getId();
        this.dateTime = entity.getDateTime();
        this.title = entity.getTitle();
    }

    @Override
    public Class<NewsLinksBaseDto> getDtoClass() {
        return NewsLinksBaseDto.class;
    }

    @Override
    public NewsLinks update(@Nonnull NewsLinks entity) {
        updateIfNotNull(entity::setDateTime, this.dateTime);
        updateIfNotNull(entity::setTitle, this.title);

        return entity;
    }

    @Override
    public NewsLinks update(@Nonnull NewsLinks entity, @Nonnull UserLogin userLogin) {
        return update(entity);
    }
}
//EOF
