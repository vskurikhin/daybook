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

@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"newsEntries"})
@ToString(exclude = {"newsEntries"})
@Entity
@Table(schema = "db", name = "db_news_group")
@NamedQueries({
    @NamedQuery(
        name = FIND_ALL,
        query = "SELECT DISTINCT e FROM NewsGroup e"
    ),
    @NamedQuery(
        name = FIND_ALL_ORDER_BY_GROUP_ASC,
        query = "SELECT DISTINCT e FROM NewsGroup e" +
                " ORDER BY e.group ASC"
    ),
    @NamedQuery(
        name = FIND_ALL_ORDER_BY_GROUP_DESC,
        query = "SELECT DISTINCT e FROM NewsGroup e" +
                " ORDER BY e.group DESC"
    ),
    @NamedQuery(
        name = FIND_WHERE_GROUP,
        query = "SELECT DISTINCT e FROM NewsGroup e" +
                " WHERE e.group = :group"
    ),
    @NamedQuery(
        name = FIND_ALL_WHERE_GROUP,
        query = "SELECT DISTINCT e FROM NewsGroup e" +
                " WHERE e.group LIKE :group"
    ),
    @NamedQuery(
        name = FIND_ALL_WHERE_ID_IN,
        query = "SELECT DISTINCT e FROM NewsGroup e" +
                " WHERE e.id IN (:ids)"
    ),
    @NamedQuery(
        name = FIND_ALL_WHERE_GROUP_IN,
        query = "SELECT DISTINCT e FROM NewsGroup e" +
                " WHERE e.group IN (:groups)"
    ),
})
public class NewsGroup implements DBEntity<UUID>, Serializable {

    private static final long serialVersionUID = 230L;

    public static final String FIND_ALL = "NewsGroupDao.findAll";

    public static final String FIND_ALL_ORDER_BY_GROUP_ASC = "NewsGroupDao.findAllOrderByGroupAsc";

    public static final String FIND_ALL_ORDER_BY_GROUP_DESC = "NewsGroupDao.findAllOrderByGroupDesc";

    public static final String FIND_WHERE_GROUP = "NewsGroupDao.findWhereGroup";

    public static final String FIND_ALL_WHERE_GROUP = "NewsGroupDao.findAllWhereGroup";

    public static final String FIND_ALL_WHERE_ID_IN = "NewsGroupDao.findAllWhereIdIn";

    public static final String FIND_ALL_WHERE_GROUP_IN = "NewsGroupDao.findAllWhereGroupIn";

    @Getter
    @Setter // TODO remove
    @Id
    @NotNull
    private UUID id;

    @Getter
    @Setter
    @NotNull
    @Column(name = "date_time", nullable = false)
    private LocalDateTime dateTime;

    @Getter
    @Setter
    @NotNull
    @Column(name = "\"group\"", length = 64, nullable = false, unique = true)
    private String group;

    @Getter
    @Setter
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "newsGroup")
    private List<NewsEntry> newsEntries;

    public NewsGroup(@NotNull UUID id) {
        this.id = id;
    }
}
//EOF
