/*
 * This file was last modified at 2020.02.06 21:57 by Victor N. Skurikhin.
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
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity
@Table(schema = "db", name = "db_user_role")
@NamedQueries({
    @NamedQuery(
        name = FIND_ALL,
        query = "SELECT DISTINCT e FROM UserRole e"
    ),
    @NamedQuery(
        name = FIND_WHERE_ROLE,
        query = "SELECT DISTINCT e FROM UserRole e WHERE e.roleName = :role"
    ),
    @NamedQuery(
        name = FIND_ALL_WHERE_ID_IN,
        query = "SELECT DISTINCT e FROM UserRole e WHERE e.id IN (:ids)"
    ),
})
public class UserRole extends UUIDEntity implements Serializable {
    private static final long serialVersionUID = 210L;

    public static final String FIND_ALL = "UserRole.findAll";

    public static final String FIND_WHERE_ROLE = "UserRole.findWhereRole";

    public static final String FIND_ALL_WHERE_ID_IN = "UserRole.findAllByIdIn";

    @NotNull
    @Column(name = "date_time", nullable = false)
    private LocalDateTime dateTime;

    @NotNull
    @Column(name = "role_name", length = 32, nullable = false)
    private String roleName;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.DETACH})
    @JoinColumn(name = "db_user_login_id", nullable = false)
    private UserLogin userLogin;

    @NotNull
    @Builder
    public UserRole(UUID id, LocalDateTime dateTime, String roleName, UserLogin userLogin) {
        super(id);
        this.dateTime = dateTime;
        this.roleName = roleName;
        this.userLogin = userLogin;
    }
}
//EOF
