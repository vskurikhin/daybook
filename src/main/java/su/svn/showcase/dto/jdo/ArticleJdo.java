/*
 * This file was last modified at 2020.04.14 22:15 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * ArticleJdo.java
 * $Id$
 */

package su.svn.showcase.dto.jdo;

import lombok.*;
import su.svn.showcase.domain.Article;
import su.svn.showcase.domain.Link;
import su.svn.showcase.domain.Record;
import su.svn.showcase.domain.UserLogin;
import su.svn.showcase.dto.*;

import javax.annotation.Nonnull;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * The JDO of Article.
 *
 * @author Victor N. Skurikhin
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"record"})
@ToString(exclude = {"record"})
public class ArticleJdo implements ArticleDto, Serializable {

    private static final long serialVersionUID = 9291L;

    @NotNull
    private UUID id;

    private LocalDateTime dateTime;

    @Size(max = 128)
    private String title;

    @Size(max = 128)
    private String include;

    @Size(max = 128)
    private String anchor;

    private String summary;

    private RecordDto record;

    private LinkDto link;

    public ArticleJdo(@Nonnull UUID id) {
        this.id = id;
    }

    @Override
    public Class<ArticleJdo> getDtoClass() {
        return ArticleJdo.class;
    }
}
//EOF
