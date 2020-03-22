/*
 * This file was last modified at 2020.03.21 21:02 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * NewsLinks.java
 * $Id$
 */

package su.svn.showcase.domain;

import lombok.*;

import javax.annotation.Nonnull;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

import static su.svn.showcase.domain.NewsLinks.*;

@Builder
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"record"})
@ToString(exclude = {"record"})
@Entity
@Table(schema = "db", name = "db_news_links")
@NamedQueries({
    @NamedQuery(
        name = FIND_ALL,
        query = "SELECT DISTINCT e FROM NewsLinks e"
    ),
    @NamedQuery(
        name = FIND_ALL_ORDER_BY_TITLE_ASC,
        query = "SELECT DISTINCT e FROM NewsLinks e" +
                " ORDER BY e.title ASC"
    ),
    @NamedQuery(
        name = FIND_ALL_ORDER_BY_TITLE_DESC,
        query = "SELECT DISTINCT e FROM NewsLinks e" +
                " ORDER BY e.title DESC"
    ),
    @NamedQuery(
        name = FIND_ALL_WHERE_TITLE,
        query = "SELECT DISTINCT e FROM NewsLinks e" +
                " WHERE e.title LIKE :title"
    ),
    @NamedQuery(
        name = FIND_ALL_WHERE_ID_IN,
        query = "SELECT DISTINCT e FROM NewsLinks e" +
                " WHERE e.id IN :ids"
    ),
    @NamedQuery(
        name = FIND_WHERE_TITLE,
        query = "SELECT DISTINCT e FROM NewsLinks e" +
                " WHERE e.title = :title"
    ),
})
public class NewsLinks implements DBEntity<UUID>, Serializable {
    private static final long serialVersionUID = 260L;

    public static final String FIND_ALL = "NewsLinksDao.findAll";

    public static final String FIND_ALL_WHERE_ID_IN = "NewsLinksDao.findAllWhereIdIn";

    public static final String FIND_ALL_ORDER_BY_TITLE_ASC = "NewsLinksDao.findAllOrderByTitleAsc";

    public static final String FIND_ALL_ORDER_BY_TITLE_DESC = "NewsLinksDao.findAllOrderByTitleDesc";

    public static final String FIND_ALL_WHERE_TITLE = "NewsLinksDao.findAllWhereTitle";

    public static final String FIND_WHERE_TITLE = "NewsLinksDao.findWhereTitle";

    @Getter
    @NotNull
    @Id
    private UUID id;

    @Getter
    @Setter
    @OneToOne(fetch = FetchType.EAGER,
            cascade = {CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH, CascadeType.REMOVE})
    @JoinColumn(name = "id")
    private Record record;

    @Getter
    @Setter
    @ManyToOne(fetch = FetchType.LAZY,
             cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinColumn(name = "db_news_group_id")
    private NewsGroup newsGroup;

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
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "newsLinks")
    private Set<LinkDescription> links;

    public NewsLinks() {
        this.record = new Record();
        this.id = this.record.getId();
    }

    public NewsLinks(@Nonnull Record record) {
        this.id = record.getId();
        this.record = record;
    }

    public NewsLinks(@Nonnull UUID id) {
        this.id = id;
        this.record = new Record(id);
    }
}
//EOF
