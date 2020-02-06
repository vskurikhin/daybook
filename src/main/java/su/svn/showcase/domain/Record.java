/*
 * This file was last modified at 2020.02.06 21:55 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * Record.java
 * $Id$
 */

package su.svn.showcase.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static su.svn.showcase.domain.Record.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true, exclude = {"tags"})
@ToString(callSuper = true, exclude = {"tags"})
@Entity
@Table(schema = "db", name = "db_record")
@NamedQueries({
    @NamedQuery(
        name = FIND_ALL,
        query = "SELECT DISTINCT e FROM Record e ORDER BY e.editDateTime DESC, e.index ASC"
    ),
    @NamedQuery(
        name = FIND_ALL_ORDER_BY_EDIT_DATE_TIME,
        query = "SELECT DISTINCT e FROM Record e ORDER BY e.editDateTime DESC, e.index ASC"
    ),
    @NamedQuery(
        name = FIND_ALL_WITH_TAGS,
        query = "SELECT DISTINCT e FROM Record e" +
                " LEFT JOIN FETCH e.tags" +
                " ORDER BY e.editDateTime DESC, e.index ASC"
    ),
    @NamedQuery(
        name = FIND_ALL_WHERE_ID_IN,
        query = "SELECT DISTINCT e FROM Record e WHERE e.id IN (:ids) ORDER BY e.editDateTime"
    ),
    @NamedQuery(
        name = FIND_ALL_BY_DAY,
        query = "SELECT DISTINCT e FROM Record e" +
                " WHERE e.editDateTime BETWEEN :startDate AND :endDate" +
                " ORDER BY e.editDateTime DESC, e.index ASC"
    ),
    @NamedQuery(
        name = FIND_FETCH_BY_ID,
        query = "SELECT DISTINCT e FROM Record e" +
                " LEFT JOIN FETCH e.userLogin u" +
                " LEFT JOIN FETCH e.tags t WHERE e.id = :id"
    ),
})
public class Record extends UUIDEntity implements Serializable {
    private static final long serialVersionUID = 240L;

    public static final String FIND_ALL = "RecordDao.findAll";

    public static final String FIND_ALL_ORDER_BY_EDIT_DATE_TIME = "RecordDao.findAllOrderByEditDateTime";

    public static final String FIND_ALL_WITH_TAGS = "RecordDao.findAllWithTags";

    public static final String FIND_ALL_WHERE_ID_IN = "RecordDao.findAllWhereIdIn";

    public static final String FIND_ALL_BY_DAY = "RecordDao.findAllByDay";

    public static final String FIND_ALL_WHERE_TAG_ID = "RecordDao.findAllWhereTagId";

    public static final String FIND_FETCH_BY_ID = "RecordDao.findFetchById";

    public static final String RANGE_BY_DAY
            = "SELECT DISTINCT e FROM Record e"
            + " WHERE e.editDateTime BETWEEN :startDate AND :endDate"
            + " ORDER BY e.editDateTime DESC, e.index ASC";
    public static final String RANGE_WITH_TAGS
            = "FROM Record e LEFT JOIN FETCH e.tags ORDER BY e.editDateTime DESC, e.index ASC";
    public static final String COUNT = "SELECT COUNT(e.id) FROM Record e";
    public static final String COUNT_BY_DAY
            = "SELECT COUNT(e.id) FROM Record e WHERE e.editDateTime BETWEEN :startDate AND :endDate";

    @NotNull
    @Column(name = "create_date_time", nullable = false)
    private LocalDateTime createDateTime;

    @NotNull
    @Column(name = "edit_date_time", nullable = false)
    private LocalDateTime editDateTime;

    private int index;

    @NotNull
    @Column(name = "type", nullable = false)
    private String type;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.DETACH})
    @JoinColumn(name = "db_user_login_id", nullable = false)
    private UserLogin userLogin;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    @JoinTable(
            schema = "db",
            name = "db_record_tag",
            joinColumns = @JoinColumn(name = "db_record_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    Set<Tag> tags;

    public Record(@NotNull UUID id, @NotNull UserLogin userLogin) {
        super(id);
        this.createDateTime = LocalDateTime.now();
        this.editDateTime = LocalDateTime.now();
        this.index = 100;
        this.type = Record.class.getSimpleName();
        this.userLogin = userLogin;
        this.tags = new HashSet<>();
    }

    @Builder
    public Record(
            @NotNull UUID id,
            @NotNull LocalDateTime createDateTime,
            @NotNull LocalDateTime editDateTime,
            int index,
            @NotNull String type,
            @NotNull UserLogin userLogin,
            Set<Tag> tags) {
        super(id);
        this.createDateTime = createDateTime;
        this.editDateTime = editDateTime;
        this.index = index;
        this.type = type;
        this.userLogin = userLogin;
        this.tags = tags;
    }
}
//EOF
