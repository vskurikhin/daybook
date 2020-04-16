/*
 * This file was last modified at 2020.04.14 22:15 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * UserRole.java
 * $Id$
 */

package su.svn.showcase.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

import static su.svn.showcase.domain.UserRole.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Entity
@Table(schema = "db", name = "db_user_role")
@NamedQueries({
    @NamedQuery(
        name = FIND_ALL,
        query = "SELECT DISTINCT e FROM UserRole e"
    ),
    @NamedQuery(
        name = FIND_WHERE_ROLE,
        query = "SELECT DISTINCT e FROM UserRole e" +
                " WHERE e.roleName = :role"
    ),
    @NamedQuery(
        name = FIND_ALL_WHERE_ID_IN,
        query = "SELECT DISTINCT e FROM Role e" +
                " WHERE e.id IN (:ids)"
    ),
    @NamedQuery(
        name = FIND_ALL_WHERE_ROLE_IN,
        query = "SELECT DISTINCT e FROM Role e" +
                " WHERE e.roleName IN (:roles)"
    ),
    @NamedQuery(
        name = FETCH_ALL,
        query = "SELECT DISTINCT e FROM UserRole e" +
                " LEFT JOIN FETCH e.userLogin l"
    ),
    @NamedQuery(
        name = FETCH_ALL_ORDER_BY_ROLE_ASC,
        query = "SELECT DISTINCT e FROM UserRole e" +
                " LEFT JOIN FETCH e.userLogin l" +
                " ORDER BY e.roleName ASC"
    ),
    @NamedQuery(
        name = FETCH_ALL_ORDER_BY_ROLE_DESC,
        query = "SELECT DISTINCT e FROM UserRole e" +
                " LEFT JOIN FETCH e.userLogin l" +
                " ORDER BY e.roleName DESC"
    ),
})
public class UserRole implements DBEntity<UUID>, Serializable {

    private static final long serialVersionUID = 210L;

    public static final String FIND_ALL = "UserRoleDao.findAll";

    public static final String FIND_WHERE_ROLE = "UserRoleDao.findWhereRole";

    public static final String FIND_ALL_WHERE_ID_IN = "UserRoleDao.findAllByIdIn";

    public static final String FIND_ALL_WHERE_ROLE_IN = "UserRoleDao.findAllWhereRoleIn";

    public static final String FETCH_ALL = "UserRoleDao.fetchAll";

    public static final String FETCH_ALL_ORDER_BY_ROLE_ASC = "UserRoleDao.fetchAllOrderByRoleAsc";

    public static final String FETCH_ALL_ORDER_BY_ROLE_DESC = "UserRoleDao.fetchAllOrderByRoleDesc";

    public static final String RANGE = FETCH_ALL;

    public static final String RANGE_ORDER_BY_ROLE_ASC = FETCH_ALL_ORDER_BY_ROLE_ASC;

    public static final String RANGE_ORDER_BY_ROLE_DESC = FETCH_ALL_ORDER_BY_ROLE_DESC;

    @Getter
    @NotNull
    @Id
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @Getter
    @Setter
    @OneToOne(fetch = FetchType.EAGER,
            cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinColumn(name = "id")
    private Role role;

    @Getter
    @Setter
    @NotNull
    @Column(name = "date_time", nullable = false)
    private LocalDateTime dateTime;

    @Getter
    @Setter
    @NotNull
    @Column(name = "role_name", length = 32, nullable = false)
    private String roleName;

    @Getter
    @Setter
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.DETACH})
    @JoinColumn(name = "db_user_login_id", nullable = false)
    private UserLogin userLogin;

    public UserRole(@NotNull UUID id) {
        assert id != null;
        this.id = id;
    }
}
//EOF
