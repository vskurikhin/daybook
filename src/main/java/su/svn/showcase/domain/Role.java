/*
 * This file was last modified at 2020.03.01 00:04 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * Role.java
 * $Id$
 */

package su.svn.showcase.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.UUID;

import static su.svn.showcase.domain.Role.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Entity
@Table(schema = "db", name = "db_role")
@NamedQueries({
    @NamedQuery(
        name = FIND_ALL,
        query = "SELECT DISTINCT e FROM Role e"
    ),
    @NamedQuery(
        name = FIND_ALL_ORDER_BY_ROLE_ASC,
        query = "SELECT DISTINCT e FROM Role e" +
                " ORDER BY e.roleName ASC"
    ),
    @NamedQuery(
        name = FIND_ALL_ORDER_BY_ROLE_DESC,
        query = "SELECT DISTINCT e FROM Role e" +
                " ORDER BY e.roleName DESC"
    ),
    @NamedQuery(
        name = FIND_WHERE_ROLE,
        query = "SELECT DISTINCT e FROM Role e" +
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
public class Role implements DBEntity<UUID>, Serializable {

    private static final long serialVersionUID = 220L;

    public static final String FIND_ALL = "RoleDao.findAll";

    public static final String FIND_ALL_ORDER_BY_ROLE_ASC = "RoleDao.findAllOrderByRoleAsc";

    public static final String FIND_ALL_ORDER_BY_ROLE_DESC = "RoleDao.findAllOrderByRoleDesc";

    public static final String FIND_WHERE_ROLE = "RoleDao.findWhereRole";

    public static final String FIND_ALL_WHERE_ID_IN = "RoleDao.findAllByIdIn";

    public static final String FIND_ALL_WHERE_ROLE_IN = "RoleDao.findAllWhereRoleIn";

    @Getter
    @NotNull
    @Id
    private UUID id;

    @Getter
    @Setter
    @NotNull
    @Column(name = "role_name", length = 32, nullable = false, unique = true)
    private String roleName;

    public Role(@NotNull UUID id) {
        assert id != null;
        this.id = id;
    }
}
//EOF
