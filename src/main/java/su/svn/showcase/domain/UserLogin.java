/*
 * This file was last modified at 2020.04.24 22:15 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * UserLogin.java
 * $Id$
 */

package su.svn.showcase.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static su.svn.showcase.domain.UserLogin.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"roles"})
@Entity
@Table(schema = "db", name = "db_user_login")
@NamedQueries({
    @NamedQuery(
        name = FIND_ALL,
        query = "SELECT DISTINCT e FROM UserLogin e ORDER BY e.login"
    ),
    @NamedQuery(
        name = FETCH_BY_ID,
        query = "SELECT DISTINCT e FROM UserLogin e LEFT JOIN FETCH e.roles r WHERE e.id = :id"
    ),
    @NamedQuery(
        name = FIND_WHERE_LOGIN,
        query = "SELECT DISTINCT e FROM UserLogin e LEFT JOIN FETCH e.roles r WHERE e.login = :login"
    ),
    @NamedQuery(
        name = FIND_ALL_IN_USER_ROLE,
        query = "SELECT DISTINCT e" +
                " FROM UserLogin e" +
                " LEFT JOIN FETCH e.roles r" +
                " WHERE r.roleName = :name" +
                " ORDER BY r.roleName"
    ),
    @NamedQuery(
        name = FIND_ALL_WHERE_ID_IN,
        query = "SELECT DISTINCT e FROM UserLogin e WHERE e.id IN (:ids) ORDER BY e.login"
    ),
})
public class UserLogin implements DBEntity<UUID>, Serializable {

    private static final long serialVersionUID = 200L;

    public static final String FETCH_BY_ID = "UserLoginDao.fetchById";

    public static final String FIND_WHERE_LOGIN = "UserLoginDao.findWhereLogin";

    public static final String FIND_ALL = "UserLoginDao.findAll";

    public static final String FIND_ALL_IN_USER_ROLE = "UserLoginDao.findAllInUserRole";

    public static final String FIND_ALL_WHERE_ID_IN = "UserLoginDao.findAllByIdIn";

    public static final String RANGE = FIND_ALL;

    @Getter
    @NotNull
    @Id
    @Column(name = "id", nullable = false, updatable = false)
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
        assert id != null;
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserLogin userLogin = (UserLogin) o;
        return Objects.equals(id, userLogin.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
//EOF
