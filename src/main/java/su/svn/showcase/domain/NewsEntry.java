/*
 * This file was last modified at 2020.02.21 22:20 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * NewsEntry.java
 * $Id$
 */

package su.svn.showcase.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

import static su.svn.showcase.domain.NewsEntry.*;

@Builder
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"record"})
@ToString(exclude = {"record"})
@Entity
@Table(schema = "db", name = "db_news_entry")
@NamedQueries({
    @NamedQuery(
        name = FIND_ALL,
        query = "SELECT DISTINCT e FROM NewsEntry e"
    ),
    @NamedQuery(
        name = FIND_ALL_ORDER_BY_TITLE_ASC,
        query = "SELECT DISTINCT e FROM NewsEntry e" +
                " ORDER BY e.title ASC"
    ),
    @NamedQuery(
        name = FIND_ALL_ORDER_BY_TITLE_DESC,
        query = "SELECT DISTINCT e FROM NewsEntry e" +
                " ORDER BY e.title DESC"
    ),
    @NamedQuery(
        name = FIND_ALL_WHERE_TITLE,
        query = "SELECT DISTINCT e FROM NewsEntry e" +
                " WHERE e.title LIKE :title"
    ),
    @NamedQuery(
        name = FIND_ALL_WHERE_ID_IN,
        query = "SELECT DISTINCT e FROM NewsEntry e" +
                " WHERE e.id IN :ids"
    ),
    @NamedQuery(
        name = FIND_WHERE_TITLE,
        query = "SELECT DISTINCT e FROM NewsEntry e" +
                " WHERE e.title = :title"
    ),
})
public class NewsEntry implements DBEntity<UUID>, Serializable {
    private static final long serialVersionUID = 240L;

    public static final String FIND_ALL = "NewsEntryDao.findAll";

    public static final String FIND_ALL_WHERE_ID_IN = "NewsEntryDao.findAllWhereIdIn";

    public static final String FIND_ALL_ORDER_BY_TITLE_ASC = "NewsEntryDao.findAllOrderByTitleAsc";

    public static final String FIND_ALL_ORDER_BY_TITLE_DESC = "NewsEntryDao.findAllOrderByTitleDesc";

    public static final String FIND_ALL_WHERE_TITLE = "NewsEntryDao.findAllWhereTitle";

    public static final String FIND_WHERE_TITLE = "NewsEntryDao.findWhereTitle";

    @Getter
    @NotNull
    @Id
    private UUID id;

    @Getter
    @Setter
    @OneToOne(fetch = FetchType.EAGER,
            cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinColumn(name = "id")
    private Record record;

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
    @Column(name = "content", length = 1024)
    private String content;

    @Getter
    @Setter
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.DETACH})
    @JoinColumn(name = "db_news_group_id")
    private NewsGroup newsGroup;

    public NewsEntry() {
        this.record = new Record();
        this.id = this.record.getId();
    }

    public NewsEntry(@NotNull Record record) {
        this.id = record.getId();
        this.record = record;
    }

    public NewsEntry(@NotNull UUID id) {
        this.id = id;
        this.record = new Record(id, null);
    }
}
//EOF
