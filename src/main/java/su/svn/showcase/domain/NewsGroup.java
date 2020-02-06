/*
 * This file was last modified at 2020.02.06 21:56 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * NewsGroup.java
 * $Id$
 */

package su.svn.showcase.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static su.svn.showcase.domain.NewsGroup.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true, exclude = {"newsEntries"})
@ToString(callSuper = true, exclude = {"newsEntries"})
@Entity
@Table(schema = "db", name = "db_news_group")
@NamedQueries({
    @NamedQuery(
        name = FIND_ALL,
        query = "SELECT DISTINCT e FROM NewsGroup e ORDER BY e.dateTime DESC"
    ),
    @NamedQuery(
        name = FIND_ALL_WHERE_GROUP,
        query = "SELECT DISTINCT e FROM NewsGroup e" +
                " LEFT JOIN FETCH e.newsEntries n" +
                " WHERE e.group LIKE :group ORDER BY e.dateTime DESC"
    ),
})
public class NewsGroup extends UUIDEntity implements Serializable {
    private static final long serialVersionUID = 230L;

    public static final String FIND_ALL = "NewsGroupDao.findAll";

    public static final String FIND_ALL_WHERE_GROUP = "NewsGroupDao.findAllWhereGroup";

    @Builder
    public NewsGroup(
            @NotNull UUID id,
            @NotNull LocalDateTime dateTime,
            @NotNull String group,
            List<NewsEntry> newsEntries) {
        super(id);
        this.dateTime = dateTime;
        this.group = group;
        this.newsEntries = newsEntries;
    }

    @NotNull
    @Column(name = "date_time", nullable = false)
    private LocalDateTime dateTime;

    @NotNull
    @Column(name = "\"group\"", length = 64, nullable = false, unique = true)
    private String group;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "newsGroup")
    private List<NewsEntry> newsEntries;
}
//EOF
