/*
 * This file was last modified at 2020.02.18 10:55 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * UserRole.java
 * $Id$
 */

package su.svn.showcase.domain;

import lombok.*;

import javax.persistence.*;
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
        name = FIND_ALL_WHERE_ID_IN,
        query = "SELECT DISTINCT e FROM UserRole e WHERE e.id IN (:ids)"
    ),

    @NamedQuery(
        name = FIND_ALL_ORDER_BY_ROLE_ASC,
        query = "SELECT DISTINCT e FROM UserRole e" +
                " ORDER BY e.roleName ASC"
    ),
    @NamedQuery(
        name = FIND_ALL_ORDER_BY_ROLE_DESC,
        query = "SELECT DISTINCT e FROM UserRole e" +
                " ORDER BY e.roleName DESC"
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
})
public class UserRole implements DBEntity<UUID>, Serializable {

    private static final long serialVersionUID = 210L;

    public static final String FIND_ALL = "UserRoleDao.findAll";

    public static final String FIND_ALL_ORDER_BY_ROLE_ASC = "UserRoleDao.findAllOrderByRoleAsc";

    public static final String FIND_ALL_ORDER_BY_ROLE_DESC = "UserRoleDao.findAllOrderByRoleDesc";

    public static final String FIND_WHERE_ROLE = "UserRoleDao.findWhereRole";

    public static final String FIND_ALL_WHERE_ID_IN = "UserRoleDao.findAllByIdIn";

    public static final String FIND_ALL_WHERE_ROLE_IN = "UserRoleDao.findAllWhereRoleIn";

    @Getter
    @NotNull
    @Id
    private UUID id;

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
        this.id = id;
    }
}
//EOF
