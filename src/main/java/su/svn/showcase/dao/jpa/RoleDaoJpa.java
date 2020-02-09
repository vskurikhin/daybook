/*
 * This file was last modified at 2020.02.09 15:50 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * RoleDaoJpa.java
 * $Id$
 */

package su.svn.showcase.dao.jpa;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import su.svn.showcase.dao.RoleDao;
import su.svn.showcase.domain.Role;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static su.svn.shared.Constants.Db.PERSISTENCE_UNIT_NAME;

/**
 * The Role DAO implementation.
 *
 * @author Victor N. Skurikhin
 */
@Stateless
public class RoleDaoJpa extends AbstractDaoJpa<UUID, Role> implements RoleDao {
    private static final Logger LOGGER = LoggerFactory.getLogger(RoleDaoJpa.class);

    @PersistenceContext(unitName = PERSISTENCE_UNIT_NAME)
    private EntityManager entityManager;

    /**
     * {@inheritDoc }
     */
    @Override
    public EntityManager getEntityManager() {
        return this.entityManager;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public Logger getLogger() {
        return LOGGER;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public Class<Role> getEClass() {
        return Role.class;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public Optional<Role> findById(UUID id) {
        return abstractDaoFindById(id);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public Optional<Role> findWhereRole(String role) {
        return abstractDaoFindWhereField(Role.FIND_WHERE_ROLE, "role", role);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public List<Role> findAll() {
        return abstractDaoFindAll(Role.FIND_ALL);
    }

    @Override
    public List<Role> findAllByIdIn(Iterable<UUID> ids) {
        return null;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public long count() {
        return abstractCount();
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public boolean save(Role entity) {
        return abstractDaoSave(entity);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public boolean saveAll(Iterable<Role> entities) {
        return abstractDaoSaveAll(entities);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public boolean delete(UUID id) {
        return abstractDaoDelete(id);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public boolean deleteAll(Iterable<Role> entities) {
        return abstractDaoDeleteAll(entities);
    }
}
//EOF
