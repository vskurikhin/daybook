/*
 * This file was last modified at 2020.04.24 22:15 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * Role.java
 * $Id$
 */

package su.svn.showcase.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

import static su.svn.showcase.domain.Role.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
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
    @Column(name = "id", nullable = false, updatable = false)
    private UUID id;

    @Getter
    @Setter
    @NotNull
    @Column(name = "role_name", length = 32, nullable = false, unique = true, updatable = false)
    private String roleName;

    public Role(@NotNull UUID id) {
        assert id != null;
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Role role = (Role) o;
        return Objects.equals(id, role.id) &&
               Objects.equals(roleName, role.roleName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, roleName);
    }
}
//EOF
