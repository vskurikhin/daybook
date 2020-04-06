/*
 * This file was last modified at 2020.04.06 22:03 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * Record.java
 * $Id$
 */

package su.svn.showcase.domain;

import lombok.*;
import org.wildfly.common.annotation.Nullable;
import su.svn.showcase.interfaces.Typing;

import javax.annotation.Nonnull;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static su.svn.showcase.domain.Record.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"tags"})
@ToString(exclude = {"tags"})
@Entity
@Table(schema = "db", name = "db_record")
@SuppressWarnings({"SingleElementAnnotation", "JpaQlInspection"})
@NamedQueries({
    @NamedQuery(
        name = FIND_ALL,
        query = "SELECT DISTINCT e FROM Record e"
    ),
    @NamedQuery(
        name = FIND_ALL_ORDER_BY_EDIT_DATE_TIME_DESC_INDEX,
        query = "SELECT DISTINCT e FROM Record e" +
                " ORDER BY e.editDateTime DESC, e.index ASC"
    ),
    @NamedQuery(
        name = FIND_ALL_WHERE_ID_IN,
        query = "SELECT DISTINCT e FROM Record e" +
                " WHERE e.id IN (:ids)"
    ),
    @NamedQuery(
        name = FIND_ALL_WHERE_ID_IN_ORDER_BY_EDIT_DATE_TIME_DESC_INDEX,
        query = "SELECT DISTINCT e FROM Record e" +
                " WHERE e.id IN (:ids)" +
                " ORDER BY e.editDateTime DESC, e.index ASC"
    ),
    @NamedQuery(
        name = FIND_ALL_BY_DAY,
        query = "SELECT DISTINCT e FROM Record e" +
                " WHERE e.editDateTime BETWEEN :startDate AND :endDate"
    ),
    @NamedQuery(
        name = FIND_ALL_BY_DAY_ORDER_BY_EDIT_DATE_TIME_DESC_INDEX,
        query = "SELECT DISTINCT e FROM Record e" +
                " WHERE e.editDateTime BETWEEN :startDate AND :endDate" +
                " ORDER BY e.editDateTime DESC, e.index ASC"
    ),

    @NamedQuery(
        name = FETCH_BY_ID,
        query = "SELECT DISTINCT e FROM Record e" +
                " LEFT JOIN FETCH e.userLogin u" +
                " LEFT JOIN FETCH e.article a" +
                " LEFT JOIN FETCH e.newsEntry n" +
                " LEFT JOIN FETCH e.newsLinks l" +
                " LEFT JOIN FETCH e.tags t" +
                " LEFT JOIN FETCH a.link al" +
                " LEFT JOIN FETCH n.newsGroup ng" +
                " LEFT JOIN FETCH l.newsGroup lg" +
                " LEFT JOIN FETCH l.descriptions ld" +
                " WHERE e.id = :id"
    ),

    @NamedQuery(
        name = FETCH_ALL,
        query = "SELECT DISTINCT e FROM Record e" +
                " LEFT JOIN FETCH e.userLogin u" +
                " LEFT JOIN FETCH e.article a" +
                " LEFT JOIN FETCH e.newsEntry n" +
                " LEFT JOIN FETCH e.newsLinks l" +
                " LEFT JOIN FETCH e.tags t" +
                " LEFT JOIN FETCH a.link al" +
                " LEFT JOIN FETCH n.newsGroup ng" +
                " LEFT JOIN FETCH l.newsGroup lg" +
                " LEFT JOIN FETCH l.descriptions ld"
    ),
    @NamedQuery(
        name = FETCH_ALL_ORDER_BY_EDIT_DATE_TIME_DESC_INDEX,
        query = "SELECT DISTINCT e FROM Record e" +
                " LEFT JOIN FETCH e.userLogin u" +
                " LEFT JOIN FETCH e.article a" +
                " LEFT JOIN FETCH e.newsEntry n" +
                " LEFT JOIN FETCH e.newsLinks l" +
                " LEFT JOIN FETCH e.tags t" +
                " LEFT JOIN FETCH a.link al" +
                " LEFT JOIN FETCH n.newsGroup ng" +
                " LEFT JOIN FETCH l.newsGroup lg" +
                " LEFT JOIN FETCH l.descriptions ld" +
                " ORDER BY e.editDateTime DESC, e.index ASC"
    ),
    @NamedQuery(
        name = FETCH_ALL_WHERE_ID_IN,
        query = "SELECT DISTINCT e FROM Record e" +
                " LEFT JOIN FETCH e.userLogin u" +
                " LEFT JOIN FETCH e.article a" +
                " LEFT JOIN FETCH e.newsEntry n" +
                " LEFT JOIN FETCH e.newsLinks l" +
                " LEFT JOIN FETCH e.tags t" +
                " LEFT JOIN FETCH a.link al" +
                " LEFT JOIN FETCH n.newsGroup ng" +
                " LEFT JOIN FETCH l.newsGroup lg" +
                " LEFT JOIN FETCH l.descriptions ld" +
                " WHERE e.id IN (:id)"
    ),
    @NamedQuery(
        name = FETCH_ALL_WHERE_ID_IN_ORDER_BY_EDIT_DATE_TIME_DESC_INDEX,
        query = "SELECT DISTINCT e FROM Record e" +
                " LEFT JOIN FETCH e.userLogin u" +
                " LEFT JOIN FETCH e.article a" +
                " LEFT JOIN FETCH e.newsEntry n" +
                " LEFT JOIN FETCH e.newsLinks l" +
                " LEFT JOIN FETCH e.tags t" +
                " LEFT JOIN FETCH a.link al" +
                " LEFT JOIN FETCH n.newsGroup ng" +
                " LEFT JOIN FETCH l.newsGroup lg" +
                " LEFT JOIN FETCH l.descriptions ld" +
                " WHERE e.id IN (:id)" +
                " ORDER BY e.editDateTime DESC, e.index ASC"
    ),
    @NamedQuery(
        name = FETCH_ALL_BY_DAY,
        query = "SELECT DISTINCT e FROM Record e" +
                " LEFT JOIN FETCH e.userLogin u" +
                " LEFT JOIN FETCH e.article a" +
                " LEFT JOIN FETCH e.newsEntry n" +
                " LEFT JOIN FETCH e.newsLinks l" +
                " LEFT JOIN FETCH e.tags t" +
                " LEFT JOIN FETCH a.link al" +
                " LEFT JOIN FETCH n.newsGroup ng" +
                " LEFT JOIN FETCH l.newsGroup lg" +
                " LEFT JOIN FETCH l.descriptions ld" +
                " WHERE e.editDateTime BETWEEN :startDate AND :endDate"
    ),
    @NamedQuery(
        name = FETCH_ALL_BY_DAY_ORDER_BY_EDIT_DATE_TIME_DESC_INDEX,
        query = "SELECT DISTINCT e FROM Record e" +
                " LEFT JOIN FETCH e.userLogin u" +
                " LEFT JOIN FETCH e.article a" +
                " LEFT JOIN FETCH e.newsEntry n" +
                " LEFT JOIN FETCH e.newsLinks l" +
                " LEFT JOIN FETCH e.tags t" +
                " LEFT JOIN FETCH a.link al" +
                " LEFT JOIN FETCH n.newsGroup ng" +
                " LEFT JOIN FETCH l.newsGroup lg" +
                " LEFT JOIN FETCH l.descriptions ld" +
                " ORDER BY e.editDateTime DESC, e.index ASC"
    ),
})
public class Record implements DBEntity<UUID>, Serializable, Typing {

    private static final long serialVersionUID = 235L;

    public static final String FIND_ALL = "RecordDao.findAll";

    public static final String FIND_ALL_ORDER_BY_EDIT_DATE_TIME_DESC_INDEX
            = "RecordDao.findAllOrderByEditDateTimeDescIndex";

    public static final String FIND_ALL_WHERE_ID_IN = "RecordDao.findAllWhereIdIn";

    public static final String FIND_ALL_WHERE_ID_IN_ORDER_BY_EDIT_DATE_TIME_DESC_INDEX
            = "RecordDao.findAllWhereIdInOrderByEditDateTimeDescIndex";

    public static final String FIND_ALL_BY_DAY = "RecordDao.findAllByDay";

    public static final String FIND_ALL_BY_DAY_ORDER_BY_EDIT_DATE_TIME_DESC_INDEX
            = "RecordDao.findAllByDayOrderByEditDateTimeDescIndex";


    public static final String FETCH_BY_ID = "RecordDao.fetchById";


    public static final String FETCH_ALL = "RecordDao.fetchAll";

    public static final String FETCH_ALL_ORDER_BY_EDIT_DATE_TIME_DESC_INDEX
            = "RecordDao.fetchAllOrderByEditDateTimeDescIndex";

    public static final String FETCH_ALL_WHERE_ID_IN = "RecordDao.fetchAllWhereIdIn";

    public static final String FETCH_ALL_WHERE_ID_IN_ORDER_BY_EDIT_DATE_TIME_DESC_INDEX
            = "RecordDao.fetchAllWhereIdInOrderByEditDateTimeDescIndex";

    public static final String FETCH_ALL_BY_DAY = "RecordDao.fetchAllByDay";

    public static final String FETCH_ALL_BY_DAY_ORDER_BY_EDIT_DATE_TIME_DESC_INDEX
            = "RecordDao.fetchAllByDayOrderByEditDateTimeDescIndex";


    public static final String RANGE = FETCH_ALL_ORDER_BY_EDIT_DATE_TIME_DESC_INDEX;

    public static final String RANGE_ORDER_BY_EDIT_DATE_TIME_DESC_INDEX = FETCH_ALL_ORDER_BY_EDIT_DATE_TIME_DESC_INDEX;

    public static final String RANGE_WHERE_ID_IN = FETCH_ALL_WHERE_ID_IN;

    public static final String RANGE_WHERE_ID_IN_ORDER_BY_EDIT_DATE_TIME_DESC_INDEX
                             = FETCH_ALL_WHERE_ID_IN_ORDER_BY_EDIT_DATE_TIME_DESC_INDEX;

    public static final String RANGE_ALL_BY_DAY = FETCH_ALL_BY_DAY;

    public static final String RANGE_ALL_BY_DAY_ORDER_BY_EDIT_DATE_TIME_DESC_INDEX
                             = FETCH_ALL_BY_DAY_ORDER_BY_EDIT_DATE_TIME_DESC_INDEX;

    public static final String COUNT_BY_DAY
            = "SELECT COUNT(e.id) FROM Record e WHERE e.editDateTime BETWEEN :startDate AND :endDate";

    @Getter
    @NotNull
    @Id
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @Getter
    @Setter
    @Nullable
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "record")
    private Article article;

    @Getter
    @Setter
    @Nullable
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "record")
    private NewsEntry newsEntry;

    @Getter
    @Setter
    @Nullable
    @OneToOne(fetch = FetchType.EAGER,
            cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinColumn(name = "id")
    private NewsLinks newsLinks;

    @Getter
    @Setter
    @NotNull
    @Column(name = "create_date_time", nullable = false)
    private LocalDateTime createDateTime;

    @Getter
    @Setter
    @NotNull
    @Column(name = "edit_date_time", nullable = false)
    private LocalDateTime editDateTime;

    @Getter
    @Setter
    private int index;

    @Getter
    @Setter
    @NotNull
    @Column(name = "type", nullable = false)
    private String type;

    @Getter
    @Setter
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.DETACH})
    @JoinColumn(name = "db_user_login_id", nullable = false)
    private UserLogin userLogin;

    @Getter
    @Setter
    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    @JoinTable(
            schema = "db",
            name = "db_record_tag",
            joinColumns = @JoinColumn(name = "db_record_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private Set<Tag> tags;

    public Record(@Nonnull UUID id) {
        this.id = id;
        this.createDateTime = LocalDateTime.now();
        this.editDateTime = LocalDateTime.now();
        this.index = 100;
        this.type = Record.class.getSimpleName();
        this.tags = new HashSet<>();
    }

    public Record(@Nonnull UUID id, @Nonnull UserLogin userLogin) {
        this.id = id;
        this.createDateTime = LocalDateTime.now();
        this.editDateTime = LocalDateTime.now();
        this.index = 100;
        this.type = Record.class.getSimpleName();
        this.userLogin = userLogin;
        this.tags = new HashSet<>();
    }
}
//EOF
