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

@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@ToString
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
public class NewsEntry extends UUIDEntity implements Serializable {
    private static final long serialVersionUID = 250L;

    public static final String FIND_ALL = "NewsEntry.findAll";

    public static final String FIND_ALL_WHERE_ID_IN = "NewsEntry.findAllWhereIdIn";

    public static final String FIND_ALL_WHERE_TITLE = "NewsEntryDao.";

    public static final String COUNT = "SELECT COUNT(e.id) FROM NewsEntry e";

    @OneToOne(fetch = FetchType.EAGER,
            cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinColumn(name = "id")
    private Record record;

    @NotNull
    @Column(name = "date_time", nullable = false)
    private LocalDateTime dateTime;

    @NotNull
    @Column(name = "title", length = 128, nullable = false, unique = true)
    private String title;

    @Column(name = "content", length = 1024)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.DETACH})
    @JoinColumn(name = "db_news_group_id")
    private NewsGroup newsGroup;

    public NewsEntry() {
        this.record = new Record();
    }

    public NewsEntry(@NotNull Record record) {
        super(record.getId());
        this.record = record;
    }

    @Builder
    public NewsEntry(
            @NotNull UUID id,
            @NotNull LocalDateTime dateTime,
            @NotNull String title,
            String content,
            @NotNull NewsGroup newsGroup) {
        super(id);
        this.record = new Record(id, null);
        this.dateTime = dateTime;
        this.title = title;
        this.content = content;
        this.newsGroup = newsGroup;
    }
}
//EOF
