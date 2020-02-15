/*
 * This file was last modified at 2020.02.15 14:30 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * Tag.java$
 * $Id$
 */

package su.svn.showcase.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

import static su.svn.showcase.domain.Tag.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"records"})
@ToString(exclude = {"records"})
@Entity
@Table(schema = "dictionary", name = "tag")
@NamedQueries({
    @NamedQuery(
        name = FIND_ALL,
        query = "SELECT DISTINCT e FROM Tag e"
    ),
    @NamedQuery(
        name = FIND_ALL_ORDER_BY_TAG_ASC,
        query = "SELECT DISTINCT e FROM Tag e" +
                " ORDER BY e.tag ASC"
    ),
    @NamedQuery(
        name = FIND_ALL_ORDER_BY_TAG_DESC,
        query = "SELECT DISTINCT e FROM Tag e" +
                " ORDER BY e.tag DESC"
    ),
    @NamedQuery(
        name = FIND_WHERE_TAG,
        query = "SELECT DISTINCT e FROM Tag e" +
                " WHERE e.tag = :tag"
    ),
    @NamedQuery(
        name = FIND_ALL_WHERE_TAG,
        query = "SELECT DISTINCT e FROM Tag e" +
                " WHERE e.tag LIKE :tag"
    ),
    @NamedQuery(
        name = FIND_ALL_WHERE_ID_IN,
        query = "SELECT DISTINCT e FROM Tag e" +
                " WHERE e.id IN (:ids)"
    ),
    @NamedQuery(
        name = FIND_ALL_WHERE_TAG_IN,
        query = "SELECT DISTINCT e FROM Tag e" +
                " WHERE e.tag IN (:tags)"
    ),
})
public class Tag implements DBEntity<String>, Serializable {
    private static final long serialVersionUID = 130L;

    public static final String FIND_ALL = "TagDao.findAll";

    public static final String FIND_ALL_ORDER_BY_TAG_ASC = "TagDao.findAllOrderByTagAsc";

    public static final String FIND_ALL_ORDER_BY_TAG_DESC = "TagDao.findAllOrderByTagDesc";

    public static final String FIND_WHERE_TAG = "TagDao.findWhereTag";

    public static final String FIND_ALL_WHERE_TAG = "TagDao.findAllWhereTag";

    public static final String FIND_ALL_WHERE_ID_IN = "TagDao.findAllByIdIn";

    public static final String FIND_ALL_WHERE_TAG_IN = "TagDao.findAllByTagIn";

    public static final String OUTER_SECTION = "SELECT new FROM dictionary.tag t1" +
            " RIGHT OUTER JOIN (VALUES %s) t2(new) ON t1.tag = t2.new WHERE t1.tag IS NULL";

    @Getter
    @Setter // TODO remove
    @Id
    @NotNull
    private String id;

    @Getter
    @Setter
    @NotNull
    @Column(name = "tag", length = 128, nullable = false, unique = true)
    private String tag;

    @Getter
    @Setter
    private Boolean visible;

    @Getter
    @Setter
    @NotNull
    @Column(name = "date_time", nullable = false)
    private LocalDateTime dateTime;

    @Getter
    @Setter
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            schema = "db",
            name = "db_record_tag",
            joinColumns = @JoinColumn(name = "tag_id"),
            inverseJoinColumns = @JoinColumn(name = "db_record_id"))
    Set<Record> records;

    public Tag(@NotNull String id) {
        this.id = id;
    }
}
//EOF
