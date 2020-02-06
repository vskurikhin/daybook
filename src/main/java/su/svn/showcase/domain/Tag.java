/*
 * This file was last modified at 2020.02.06 21:51 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * Tag.java
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

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true, exclude = {"records"})
@ToString(callSuper = true, exclude = {"records"})
@Entity
@Table(schema = "dictionary", name = "tag")
@NamedQueries({
    @NamedQuery(
        name = FIND_ALL,
        query = "SELECT DISTINCT e FROM Tag e"
    ),
    @NamedQuery(
        name = FIND_WHERE_TAG,
        query = "SELECT DISTINCT e FROM Tag e WHERE e.tag = :tag"
    ),
    @NamedQuery(
        name = FIND_ALL_WHERE_TAG,
        query = "SELECT DISTINCT e FROM Tag e WHERE e.tag LIKE :tag"
    ),
    @NamedQuery(
        name = FIND_ALL_WHERE_ID_IN,
        query = "SELECT DISTINCT e FROM Tag e WHERE e.id IN (:ids)"
    ),
    @NamedQuery(
        name = FIND_ALL_WHERE_TAG_IN,
        query = "SELECT DISTINCT e FROM Tag e WHERE e.tag IN (:tags)"
    ),
})
public class Tag extends StringEntity implements Serializable {
    private static final long serialVersionUID = 130L;

    public static final String FIND_ALL = "Tag.findAll";

    public static final String FIND_WHERE_TAG = "Tag.findWhereTag";

    public static final String FIND_ALL_WHERE_TAG = "Tag.findAllWhereTag";

    public static final String FIND_ALL_WHERE_ID_IN = "Tag.findAllByIdIn";

    public static final String FIND_ALL_WHERE_TAG_IN = "Tag.findAllByTagIn";

    public static final String OUTER_SECTION = "SELECT new FROM dictionary.tag t1" +
            " RIGHT OUTER JOIN (VALUES %s) t2(new) ON t1.tag = t2.new WHERE t1.tag IS NULL";

    @NotNull
    @Column(name = "tag", length = 128, nullable = false ,unique = true)
    private String tag;

    @Column(name = "visible")
    private Boolean visible;

    @NotNull
    @Column(name = "date_time", nullable = false)
    private LocalDateTime dateTime;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            schema = "db",
            name = "db_record_tag",
            joinColumns = @JoinColumn(name = "tag_id"),
            inverseJoinColumns = @JoinColumn(name = "db_record_id"))
    Set<Record> records;

    @Builder
    public Tag(@NotNull String id,
               @NotNull String tag,
               boolean visible,
               @NotNull LocalDateTime dateTime,
               Set<Record> records) {
        super(id);
        this.tag = tag;
        this.visible = visible;
        this.dateTime = dateTime;
        this.records = records;
    }
}
//EOF
