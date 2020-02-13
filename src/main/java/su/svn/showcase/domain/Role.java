/*
 * This file was last modified at 2020.02.06 21:57 by Victor N. Skurikhin.
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

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(schema = "db", name = "db_role")
@NamedQueries({
    @NamedQuery(
        name = FIND_ALL,
        query = "SELECT DISTINCT e FROM Role e"
    ),
    @NamedQuery(
        name = FIND_ALL_WHERE_ID_IN,
        query = "SELECT DISTINCT e FROM Record e" +
                " WHERE e.id IN (:ids)"
    ),
    @NamedQuery(
        name = FIND_WHERE_ROLE,
        query = "SELECT DISTINCT e FROM Role e" +
                " WHERE e.roleName = :role"
    ),
})
public class Role extends UUIDEntity implements Serializable {
    private static final long serialVersionUID = 210L;

    public static final String FIND_ALL = "RoleDao.findAll";

    public static final String FIND_ALL_WHERE_ID_IN = "RoleDao.findAllWhereIdIn";

    public static final String FIND_WHERE_ROLE = "RoleDao.findWhereRole";

    @NotNull
    @Column(name = "role_name", length = 32, nullable = false, unique = true)
    private String roleName;

    @Builder
    public Role(@NotNull UUID id, @NotNull String roleName) {
        super(id);
        this.roleName = roleName;
    }
}
//EOF
