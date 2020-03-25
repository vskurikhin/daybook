/*
 * This file was last modified at 2020.03.22 17:24 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * Link.java
 * $Id$
 */

package su.svn.showcase.domain;

import lombok.*;
import org.wildfly.common.annotation.Nullable;

import javax.annotation.Nonnull;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

import static su.svn.showcase.domain.Link.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"descriptions"})
@ToString(exclude = {"descriptions"})
@Entity
@Table(schema = "db", name = "db_link")
@NamedQueries({
    @NamedQuery(
        name = FIND_ALL,
        query = "SELECT DISTINCT e FROM Link e"
    ),
    @NamedQuery(
        name = FIND_ALL_ORDER_BY_TAG_ASC,
        query = "SELECT DISTINCT e FROM Link e" +
                " ORDER BY e.link ASC"
    ),
    @NamedQuery(
        name = FIND_ALL_ORDER_BY_TAG_DESC,
        query = "SELECT DISTINCT e FROM Link e" +
                " ORDER BY e.link DESC"
    ),
    @NamedQuery(
        name = FIND_WHERE_TAG,
        query = "SELECT DISTINCT e FROM Link e" +
                " WHERE e.link = :link"
    ),
    @NamedQuery(
        name = FIND_ALL_WHERE_TAG,
        query = "SELECT DISTINCT e FROM Link e" +
                " WHERE e.link LIKE :link"
    ),
    @NamedQuery(
        name = FIND_ALL_WHERE_ID_IN,
        query = "SELECT DISTINCT e FROM Link e" +
                " WHERE e.id IN (:ids)"
    ),
    @NamedQuery(
        name = FIND_ALL_WHERE_TAG_IN,
        query = "SELECT DISTINCT e FROM Link e" +
                " WHERE e.link IN (:links)"
    ),
})
public class Link implements DBEntity<UUID>, Serializable {
    private static final long serialVersionUID = 270L;

    public static final String FIND_ALL = "LinkDao.findAll";

    public static final String FIND_ALL_ORDER_BY_TAG_ASC = "LinkDao.findAllOrderByLinkAsc";

    public static final String FIND_ALL_ORDER_BY_TAG_DESC = "LinkDao.findAllOrderByLinkDesc";

    public static final String FIND_WHERE_TAG = "LinkDao.findWhereLink";

    public static final String FIND_ALL_WHERE_TAG = "LinkDao.findAllWhereLink";

    public static final String FIND_ALL_WHERE_ID_IN = "LinkDao.findAllByIdIn";

    public static final String FIND_ALL_WHERE_TAG_IN = "LinkDao.findAllByLinkIn";

    @Getter
    @NotNull
    @Id
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @Getter
    @Setter
    @Nullable
    @OneToOne(fetch = FetchType.EAGER, cascade = {CascadeType.REFRESH, CascadeType.DETACH})
    @JoinColumn(name = "id")
    private Article article;

    @Getter
    @Setter
    @NotNull
    @Column(name = "date_time", nullable = false)
    private LocalDateTime dateTime;

    @Getter
    @Setter
    private Boolean visible;

    @Getter
    @Setter
    @NotNull
    @Column(name = "link", length = 512, nullable = false, unique = true)
    private String link;

    @Getter
    @Setter
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "link")
    private Set<LinkDescription> descriptions;

    public Link(@Nonnull UUID id) {
        this.id = id;
    }
}
//EOF
