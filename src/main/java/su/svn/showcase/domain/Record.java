/*
 * This file was last modified at 2020.07.09 14:59 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * Record.java
 * $Id$
 */

package su.svn.showcase.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.wildfly.common.annotation.Nullable;
import su.svn.showcase.interfaces.Typing;
import su.svn.showcase.utils.MapUtil;

import javax.annotation.Nonnull;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;

import static su.svn.showcase.domain.Record.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
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
                name = FIND_ALL_IDS,
                query = "SELECT DISTINCT e.id FROM Record e"
        ),
        @NamedQuery(
                name = FIND_ALL_WHERE_ID_IN,
                query = "SELECT DISTINCT e FROM Record e" +
                        " WHERE e.id IN (:ids)"
        ),
        @NamedQuery(
                name = FIND_ALL_BY_DAY,
                query = "SELECT DISTINCT e FROM Record e" +
                        " WHERE e.editDateTime BETWEEN :startDate AND :endDate"
        ),
        @NamedQuery(
                name = FIND_ALL_IDS_BY_DAY,
                query = "SELECT DISTINCT e.id FROM Record e" +
                        " WHERE e.editDateTime BETWEEN :startDate AND :endDate"
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
                        " WHERE e.id IN (:ids)"
        ),
})
public class Record implements DBEntity<UUID>, Serializable, Typing {

    private static final long serialVersionUID = 235L;

    public static final String FIND_ALL = "RecordDao.findAll";

    public static final String FIND_ALL_IDS = "RecordDao.findAllIds";

    public static final String FIND_ALL_WHERE_ID_IN = "RecordDao.findAllWhereIdIn";

    public static final String FIND_ALL_BY_DAY = "RecordDao.findAllByDay";

    public static final String FIND_ALL_IDS_BY_DAY = "RecordDao.findAllIdsByDay";

    public static final String FETCH_BY_ID = "RecordDao.fetchById";

    public static final String FETCH_ALL = "RecordDao.fetchAll";

    public static final String FETCH_ALL_WHERE_ID_IN = "RecordDao.fetchAllWhereIdIn";

    public static final String FETCH_ALL_BY_DAY = "RecordDao.fetchAllByDay";

    public static final String COUNT_BY_DAY
            = "SELECT COUNT(e.id) FROM Record e WHERE e.editDateTime BETWEEN :startDate AND :endDate";

    @Getter
    private static final LinkedHashMap<String, Boolean> defaultOrderMap
            = new MapUtil.Builder<String, Boolean>()
            .key("editDateTime").value(false)
            .key("index").value(true)
            .linkedHashMap();

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

    @Sort
    @Getter
    @Setter
    @NotNull
    @Column(name = "create_date_time", nullable = false, updatable = false)
    private LocalDateTime createDateTime;

    @Getter
    @Setter
    @NotNull
    @Sort(decrease = true, cluster = {"index"})
    @Column(name = "edit_date_time", nullable = false)
    private LocalDateTime editDateTime;

    @Getter
    @Setter
    @Sort
    private int index;

    @Getter
    @Setter
    @NotNull
    @Column(name = "type", nullable = false, updatable = false)
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Record record = (Record) o;
        return Objects.equals(id, record.id) &&
               Objects.equals(createDateTime, record.createDateTime) &&
               Objects.equals(type, record.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, createDateTime, type);
    }
}
//EOF
