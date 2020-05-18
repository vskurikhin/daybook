/*
 * This file was last modified at 2020.04.24 22:15 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * LinkDescription.java
 * $Id$
 */

package su.svn.showcase.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.annotation.Nonnull;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

import static su.svn.showcase.domain.LinkDescription.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"newsLinks"})
@Entity
@Table(schema = "db", name = "db_link_description")
@NamedQueries({
    @NamedQuery(
        name = FIND_ALL,
        query = "SELECT DISTINCT e FROM LinkDescription e"
    ),
    @NamedQuery(
        name = FIND_ALL_ORDER_BY_TAG_ASC,
        query = "SELECT DISTINCT e FROM LinkDescription e" +
                " ORDER BY e.description ASC"
    ),
    @NamedQuery(
        name = FIND_ALL_ORDER_BY_TAG_DESC,
        query = "SELECT DISTINCT e FROM LinkDescription e" +
                " ORDER BY e.description DESC"
    ),
    @NamedQuery(
        name = FIND_WHERE_TAG,
        query = "SELECT DISTINCT e FROM LinkDescription e" +
                " WHERE e.description = :description"
    ),
    @NamedQuery(
        name = FIND_ALL_WHERE_TAG,
        query = "SELECT DISTINCT e FROM LinkDescription e" +
                " WHERE e.description LIKE :description"
    ),
    @NamedQuery(
        name = FIND_ALL_WHERE_ID_IN,
        query = "SELECT DISTINCT e FROM LinkDescription e" +
                " WHERE e.id IN (:ids)"
    ),
    @NamedQuery(
        name = FIND_ALL_WHERE_TAG_IN,
        query = "SELECT DISTINCT e FROM LinkDescription e" +
                " WHERE e.description IN (:descriptions)"
    ),
})
public class LinkDescription implements DBEntity<UUID>, Serializable {
    private static final long serialVersionUID = 270L;

    public static final String FIND_ALL = "LinkDescriptionDao.findAll";

    public static final String FIND_ALL_ORDER_BY_TAG_ASC = "LinkDescriptionDao.findAllOrderByLinkAsc";

    public static final String FIND_ALL_ORDER_BY_TAG_DESC = "LinkDescriptionDao.findAllOrderByLinkDesc";

    public static final String FIND_WHERE_TAG = "LinkDescriptionDao.findWhereLink";

    public static final String FIND_ALL_WHERE_TAG = "LinkDescriptionDao.findAllWhereLink";

    public static final String FIND_ALL_WHERE_ID_IN = "LinkDescriptionDao.findAllByIdIn";

    public static final String FIND_ALL_WHERE_TAG_IN = "LinkDescriptionDao.findAllByLinkIn";

    @Getter
    @NotNull
    @Id
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @Getter
    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "db_news_links_id", nullable = false)
    private NewsLinks newsLinks;

    @Getter
    @Setter
    @ManyToOne(fetch = FetchType.LAZY,
            cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinColumn(name = "db_link_id", nullable = false)
    private Link link;

    @Getter
    @Setter
    @NotNull
    @Column(name = "date_time", nullable = false)
    private LocalDateTime dateTime;

    @Getter
    @Setter
    @NotNull
    @Column(name = "description", length = 128, nullable = false)
    private String description;

    @Getter
    @Setter
    @NotNull
    @Column(name = "details", length = 8192)
    private String details;

    public LinkDescription(@Nonnull UUID id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LinkDescription that = (LinkDescription) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
//EOF
