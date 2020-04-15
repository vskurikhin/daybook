/*
 * This file was last modified at 2020.04.14 22:15 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * LinkDescriptionJdo.java
 * $Id$
 */

package su.svn.showcase.dto.jdo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import su.svn.showcase.dto.LinkDescriptionDto;
import su.svn.showcase.dto.LinkDto;
import su.svn.showcase.dto.NewsLinksDto;

import javax.annotation.Nonnull;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * The JDO of LinkDescription.
 *
 * @author Victor N. Skurikhin
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"newsLinks"})
@ToString(exclude = {"newsLinks"})
public class LinkDescriptionJdo implements LinkDescriptionDto, Serializable {

    private static final long serialVersionUID = 9210L;

    @NotNull
    private UUID id;

    private NewsLinksDto newsLinks;

    private LinkDto link;

    private LocalDateTime dateTime;

    @Size(max = 128)
    private String description;

    @Size(max = 8192)
    private String details;

    public LinkDescriptionJdo(@Nonnull UUID id) {
        this.id = id;
    }

    @Override
    public Class<LinkDescriptionJdo> getDtoClass() {
        return LinkDescriptionJdo.class;
    }
}
//EOF
