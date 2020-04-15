/*
 * This file was last modified at 2020.04.14 22:15 by Victor N. Skurikhin.
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
}
//EOF
