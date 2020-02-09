/*
 * This file was last modified at  by Victor N. Skurikhin.
 */

package su.svn.showcase.dao.jpa;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import su.svn.showcase.dao.UserLoginDao;
import su.svn.showcase.domain.UserLogin;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.*;

import static su.svn.shared.Constants.Db.PERSISTENCE_UNIT_NAME;

@Stateless
public class UserLoginDaoJpa extends AbstractDaoJpa<UUID, UserLogin> implements UserLoginDao {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserLoginDaoJpa.class);

    @PersistenceContext(unitName = PERSISTENCE_UNIT_NAME)
    private EntityManager entityManager;

    @Override
    public EntityManager getEntityManager() {
        return this.entityManager;
    }

    @Override
    public Logger getLogger() {
        return LOGGER;
    }

    @Override
    public Class<UserLogin> getEClass() {
        return UserLogin.class;
    }

    @Override
    public Collection<UserLogin> findAllByIdIn(Iterable<UUID> ids) {
        return abstractDaoFindAllWhereIn(UserLogin.FIND_ALL_WHERE_ID_IN, "ids", ids);
    }

    @Override
    public Optional<UserLogin> findById(UUID id) {
        return abstractDaoFindById(id);
    }

    @Override
    public List<UserLogin> findAll() {
        return abstractDaoFindAll(UserLogin.FIND_ALL);
    }

    @Override
    public Optional<UserLogin> findWhereLogin(String login) {
        return abstractDaoFindWhereField(UserLogin.FIND_WHERE_LOGIN, "login", login);
    }

    @Override
    public List<UserLogin> findAllInUserRoleByName(String name) {
        return abstractDaoFindAllWhereField(UserLogin.FIND_ALL_IN_USER_ROLE , "name", name);
    }

    @Override
    public long count() {
        return abstractCount();
    }

    @Override
    public boolean save(UserLogin entity) {
        return abstractDaoSave(entity);
    }

    @Override
    public boolean saveAll(Iterable<UserLogin> entities) {
        return false;
    }

    @Override
    public boolean delete(UUID id) {
        return abstractDaoDelete(id);
    }

    @Override
    public boolean deleteAll(Iterable<UserLogin> entities) {
        return false;
    }
}
//EOF
