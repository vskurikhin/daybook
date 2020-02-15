/*
 * This file was last modified at 2020.02.12 23:11 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * Record.java
 * $Id$
 */

package su.svn.showcase.domain;

import lombok.*;
import org.wildfly.common.annotation.Nullable;

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
                " LEFT JOIN FETCH e.tags t WHERE e.id = :id"
    ),

    @NamedQuery(
        name = FETCH_ALL,
        query = "SELECT DISTINCT e FROM Record e" +
                " LEFT JOIN FETCH e.userLogin u" +
                " LEFT JOIN FETCH e.newsEntry n" +
                " LEFT JOIN FETCH e.tags t"
    ),
    @NamedQuery(
        name = FETCH_ALL_ORDER_BY_EDIT_DATE_TIME_DESC_INDEX,
        query = "SELECT DISTINCT e FROM Record e" +
                " LEFT JOIN FETCH e.userLogin u" +
                " LEFT JOIN FETCH e.newsEntry n" +
                " LEFT JOIN FETCH e.tags t" +
                " ORDER BY e.editDateTime DESC, e.index ASC"
    ),
    @NamedQuery(
        name = FETCH_ALL_WHERE_ID_IN,
        query = "SELECT DISTINCT e FROM Record e" +
                " LEFT JOIN FETCH e.userLogin u" +
                " LEFT JOIN FETCH e.newsEntry n" +
                " LEFT JOIN FETCH e.tags t" +
                " WHERE e.id IN (:id)"
    ),
    @NamedQuery(
        name = FETCH_ALL_WHERE_ID_IN_ORDER_BY_EDIT_DATE_TIME_DESC_INDEX,
        query = "SELECT DISTINCT e FROM Record e" +
                " LEFT JOIN FETCH e.userLogin u" +
                " LEFT JOIN FETCH e.newsEntry n" +
                " LEFT JOIN FETCH e.tags t" +
                " WHERE e.id IN (:id)" +
                " ORDER BY e.editDateTime DESC, e.index ASC"
    ),
    @NamedQuery(
        name = FETCH_ALL_BY_DAY,
        query = "SELECT DISTINCT e FROM Record e" +
                " LEFT JOIN FETCH e.userLogin u" +
                " LEFT JOIN FETCH e.newsEntry n" +
                " LEFT JOIN FETCH e.tags t" +
                " WHERE e.editDateTime BETWEEN :startDate AND :endDate"
    ),
    @NamedQuery(
        name = FETCH_ALL_BY_DAY_ORDER_BY_EDIT_DATE_TIME_DESC_INDEX,
        query = "SELECT DISTINCT e FROM Record e" +
                " LEFT JOIN FETCH e.userLogin u" +
                " LEFT JOIN FETCH e.newsEntry n" +
                " LEFT JOIN FETCH e.tags t" +
                " WHERE e.editDateTime BETWEEN :startDate AND :endDate" +
                " ORDER BY e.editDateTime DESC, e.index ASC"
    ),
})
public class Record extends UUIDEntity implements Serializable {
    private static final long serialVersionUID = 240L;

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


    public static final String RANGE = FETCH_ALL;

    public static final String RANGE_ORDER_BY_EDIT_DATE_TIME_DESC_INDEX = FETCH_ALL_ORDER_BY_EDIT_DATE_TIME_DESC_INDEX;

    public static final String RANGE_WHERE_ID_IN = FETCH_ALL_WHERE_ID_IN;

    public static final String RANGE_WHERE_ID_IN_ORDER_BY_EDIT_DATE_TIME_DESC_INDEX
                             = FETCH_ALL_WHERE_ID_IN_ORDER_BY_EDIT_DATE_TIME_DESC_INDEX;

    public static final String RANGE_ALL_BY_DAY = FETCH_ALL_BY_DAY;

    public static final String RANGE_ALL_BY_DAY_ORDER_BY_EDIT_DATE_TIME_DESC_INDEX
                             = FETCH_ALL_BY_DAY_ORDER_BY_EDIT_DATE_TIME_DESC_INDEX;

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

    @Nullable
    @OneToOne(fetch = FetchType.EAGER,
            cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinColumn(name = "id")
    private NewsEntry newsEntry;

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
            NewsEntry newsEntry, Set<Tag> tags) {
        super(id);
        this.createDateTime = createDateTime;
        this.editDateTime = editDateTime;
        this.index = index;
        this.type = type;
        this.userLogin = userLogin;
        this.newsEntry = newsEntry;
        this.tags = tags;
    }
}
//EOF
