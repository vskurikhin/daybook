/*
 * This file was last modified at 2020.02.06 21:57 by Victor N. Skurikhin.
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
        name = FIND_ALL_WHERE_ID_IN,
        query = "SELECT DISTINCT e FROM NewsEntry e WHERE e.id IN :ids"
    ),
    @NamedQuery(
        name = FIND_ALL_WHERE_TITLE,
        query = "SELECT DISTINCT e FROM NewsEntry e WHERE e.title LIKE :title ORDER BY e.id"
    ),
})
public class NewsEntry implements DBEntity<UUID>, Serializable {
    private static final long serialVersionUID = 250L;

    public static final String FIND_ALL = "NewsEntry.findAll";

    public static final String FIND_ALL_WHERE_ID_IN = "NewsEntry.findAllWhereIdIn";

    public static final String FIND_ALL_WHERE_TITLE = "NewsEntryDao.";

    public static final String COUNT = "SELECT COUNT(e.id) FROM NewsEntry e";

    @Getter
    @Setter // TODO remove
    @Id
    @NotNull
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
    @Column(name = "title", length = 128, nullable = false, unique = true)
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
