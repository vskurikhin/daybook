package su.svn.showcase.dao;

import su.svn.showcase.domain.UserLogin;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserLoginDao extends Dao<UUID, UserLogin> {
    Optional<UserLogin> findWhereLogin(String login);

    List<UserLogin> findAllInUserRoleByName(String name);
}
