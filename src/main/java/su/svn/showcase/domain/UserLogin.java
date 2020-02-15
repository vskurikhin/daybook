/*
 * This file was last modified at 2020.02.06 21:53 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * UserLogin.java
 * $Id$
 */

package su.svn.showcase.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static su.svn.showcase.domain.UserLogin.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"roles"})
@ToString(exclude = {"roles"})
@Entity
@Table(schema = "db", name = "db_user_login")
@NamedQueries({
    @NamedQuery(
        name = FIND_ALL,
        query = "SELECT DISTINCT e FROM UserLogin e"
    ),
    @NamedQuery(
        name = FIND_WHERE_LOGIN,
        query = "SELECT DISTINCT e FROM UserLogin e LEFT JOIN FETCH e.roles r WHERE e.login = :login"
    ),
    @NamedQuery(
        name = FIND_ALL_IN_USER_ROLE,
        query = "SELECT DISTINCT e FROM UserLogin e LEFT JOIN FETCH e.roles r WHERE r.roleName = :name"
    ),
    @NamedQuery(
        name = FIND_ALL_WHERE_ID_IN,
        query = "SELECT DISTINCT e FROM UserLogin e WHERE e.id IN (:ids)"
    ),
})
public class UserLogin implements DBEntity<UUID>, Serializable {

    private static final long serialVersionUID = 200L;

    public static final String FIND_ALL = "UserLogin.findAll";

    public static final String FIND_WHERE_LOGIN = "UserLogin.findWhereLogin";

    public static final String FIND_ALL_IN_USER_ROLE = "UserLogin.findAllInUserRole";

    public static final String FIND_ALL_WHERE_ID_IN = "UserLogin.findAllByIdIn";

    @Getter
    @Setter // TODO remove
    @Id
    @NotNull
    private UUID id;

    @Getter
    @Setter
    @NotNull
    @Column(name = "date_time", nullable = false)
    private LocalDateTime dateTime;

    @Getter
    @Setter
    @NotNull
    @Column(name = "login", nullable = false, unique = true, length = 64)
    private String login;

    @Getter
    @Setter
    @NotNull
    @Column(name = "password", nullable = false, length = 256)
    private String password;

    @Getter
    @Setter
    @JsonIgnoreProperties("roles")
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "userLogin")
    private List<UserRole> roles;

    public UserLogin(@NotNull UUID id) {
        this.id = id;
    }
}
//EOF
