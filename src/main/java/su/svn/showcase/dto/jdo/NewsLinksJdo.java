/*
 * This file was last modified at 2020.04.14 21:45 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * NewsLinksJdo.java
 * $Id$
 */

package su.svn.showcase.dto.jdo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import su.svn.showcase.domain.LinkDescription;
import su.svn.showcase.domain.NewsGroup;
import su.svn.showcase.domain.NewsLinks;
import su.svn.showcase.domain.Record;
import su.svn.showcase.domain.UserLogin;
import su.svn.showcase.dto.LinkDescriptionDto;
import su.svn.showcase.dto.NewsGroupDto;
import su.svn.showcase.dto.NewsLinksDto;
import su.svn.showcase.dto.RecordDto;

import javax.annotation.Nonnull;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * The JDO of NewsLinks.
 *
 * @author Victor N. Skurikhin
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"record"})
@ToString(exclude = {"record"})
public class NewsLinksJdo implements NewsLinksDto, Serializable {

    private static final long serialVersionUID = 9251L;

    @NotNull
    private UUID id;

    private LocalDateTime dateTime;

    @Size(max = 128)
    private String title;

    private RecordDto record;

    private NewsGroupDto newsGroup;

    private Set<LinkDescriptionDto> descriptions;

    public NewsLinksJdo(UUID id) {
        this.id = id;
    }

    @Override
    public Class<NewsLinksJdo> getDtoClass() {
        return NewsLinksJdo.class;
    }

    @Deprecated
    public NewsLinksJdo(@Nonnull NewsLinks entity) {
        this.id = entity.getId();
        this.dateTime = entity.getDateTime();
        this.title = entity.getTitle();
        this.newsGroup = entity.getNewsGroup() != null
                ? new NewsGroupJdo(entity.getNewsGroup())
                : null;

        this.descriptions = entity.getDescriptions().stream()
                .map(LinkDescriptionJdo::new)
                .collect(Collectors.toSet());
    }

    @Deprecated
    @Override
    public NewsLinks update(@Nonnull NewsLinks entity) {
        updateIfNotNull(entity::setDateTime, this.dateTime);
        updateIfNotNull(entity::setTitle, this.title);
        if (this.newsGroup != null) {
            NewsGroup newsGroup = new NewsGroup(this.newsGroup.getId());
            entity.setNewsGroup(this.newsGroup.update(newsGroup));
        }
        if (this.descriptions != null) {
            Set<LinkDescription> links = this.descriptions.stream()
                    .map(dto -> dto.update(new LinkDescription(dto.getId())))
                    .collect(Collectors.toSet());
            entity.setDescriptions(links);
        } else {
            entity.setDescriptions(Collections.emptySet());
        }

        return entity;
    }

    @Deprecated
    @Override
    public NewsLinks update(@Nonnull NewsLinks entity, @Nonnull UserLogin userLogin) {
        assert this.record != null;
        if (this.record instanceof RecordJdo) {
            entity.setRecord(updateRecord((RecordJdo)this.record, userLogin));
        }

        return update(entity);
    }

    private Record updateRecord(RecordDto dto) {
        return dto.update(new Record(dto.getId()));
    }

    private Record updateRecord(RecordJdo dto, UserLogin userLogin) {
        return dto.update(new Record(dto.getId(), userLogin));
    }
}
//EOF
