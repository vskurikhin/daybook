/*
 * This file was last modified at 2020.04.24 22:15 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * Article.java
 * $Id$
 */

package su.svn.showcase.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.annotation.Nonnull;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

import static su.svn.showcase.domain.Article.*;

@Builder
@AllArgsConstructor
@ToString(exclude = {"link", "record"})
@Entity
@Table(schema = "db", name = "db_article")
@NamedQueries({
    @NamedQuery(
        name = FIND_ALL,
        query = "SELECT DISTINCT e FROM Article e"
    ),
    @NamedQuery(
        name = FIND_ALL_ORDER_BY_TITLE_ASC,
        query = "SELECT DISTINCT e FROM Article e" +
                " ORDER BY e.title ASC"
    ),
    @NamedQuery(
        name = FIND_ALL_ORDER_BY_TITLE_DESC,
        query = "SELECT DISTINCT e FROM Article e" +
                " ORDER BY e.title DESC"
    ),
    @NamedQuery(
        name = FIND_ALL_WHERE_TITLE,
        query = "SELECT DISTINCT e FROM Article e" +
                " WHERE e.title LIKE :title"
    ),
    @NamedQuery(
        name = FIND_ALL_WHERE_ID_IN,
        query = "SELECT DISTINCT e FROM Article e" +
                " WHERE e.id IN :ids"
    ),
    @NamedQuery(
        name = FIND_WHERE_TITLE,
        query = "SELECT DISTINCT e FROM Article e" +
                " WHERE e.title = :title"
    ),
})
public class Article implements DBEntity<UUID>, Serializable {

    private static final long serialVersionUID = 290L;

    public static final String FIND_ALL = "ArticleDao.findAll";

    public static final String FIND_ALL_WHERE_ID_IN = "ArticleDao.findAllWhereIdIn";

    public static final String FIND_ALL_ORDER_BY_TITLE_ASC = "ArticleDao.findAllOrderByTitleAsc";

    public static final String FIND_ALL_ORDER_BY_TITLE_DESC = "ArticleDao.findAllOrderByTitleDesc";

    public static final String FIND_ALL_WHERE_TITLE = "ArticleDao.findAllWhereTitle";

    public static final String FIND_WHERE_TITLE = "ArticleDao.findWhereTitle";

    @Getter
    @NotNull
    @Id
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @Getter
    @Setter
    @PrimaryKeyJoinColumn
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, optional = false)
    private Record record;

    @Getter
    @Setter
    @PrimaryKeyJoinColumn
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Link link;

    @Getter
    @Setter
    @NotNull
    @Column(name = "date_time", nullable = false)
    private LocalDateTime dateTime;

    @Getter
    @Setter
    @NotNull
    @Column(name = "title", length = 128, nullable = false)
    private String title;

    @Getter
    @Setter
    @NotNull
    @Column(name = "include", length = 128, nullable = false)
    private String include;

    @Getter
    @Setter
    @NotNull
    @Column(name = "anchor", length = 128, nullable = false)
    private String anchor;

    @Getter
    @Setter
    @NotNull
    @Column(name = "summary")
    private String summary;

    public Article() {
        this.record = new Record();
        this.id = this.record.getId();
    }

    public Article(@Nonnull Record record) {
        this.id = record.getId();
        this.record = record;
    }

    public Article(@Nonnull UUID id) {
        this.id = id;
        this.record = new Record(id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Article article = (Article) o;
        return Objects.equals(id, article.id) &&
               Objects.equals(record, article.record);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, record);
    }

    protected boolean canEqual(final Object other) {
        return other instanceof Article;
    }
}
//EOF
