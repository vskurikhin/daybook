/*
 * This file was last modified at 2020.03.21 21:02 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * LinkDaoJpa.java
 * $Id$
 */

package su.svn.showcase.dao.jpa;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import su.svn.showcase.dao.LinkDao;
import su.svn.showcase.domain.Link;

import javax.annotation.Nonnull;
import javax.persistence.*;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * The Link DAO implementation.
 *
 * @author Victor N. Skurikhin
 */
public class LinkDaoJpa extends AbstractDaoJpa<UUID, Link> implements LinkDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(LinkDaoJpa.class);

    private final EntityManager entityManager;

    public LinkDaoJpa(@Nonnull EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * {@inheritDoc }
     * @throws IllegalArgumentException if the first argument does
     *         not denote an entity type or the second argument is
     *         is not a valid type for that entitys primary key or
     *         is null
     */
    @Override
    public Optional<Link> findById(UUID id) {
        return jpaFindById(id);
    }

    @Override
    public List<Link> findAll() {
        return jpaDaoFindAll(Link.FIND_ALL);
    }

    /**
     * {@inheritDoc }
     * @param ids - possible values.
     * @return list of Links.
     * @throws IllegalArgumentException if a query has not been
     *         defined with the given name or if the query string is
     *         found to be invalid or if the query result is found to
     *         not be assignable to the specified type
     *         or if iterable is null
     * @throws QueryTimeoutException if the query execution exceeds
     *         the query timeout value set and only the statement is
     *         rolled back
     * @throws TransactionRequiredException if a lock mode has
     *         been set and there is no transaction
     * @throws PessimisticLockException if pessimistic locking
     *         fails and the transaction is rolled back
     * @throws LockTimeoutException if pessimistic locking
     *         fails and only the statement is rolled back
     * @throws PersistenceException if the query execution exceeds
     *         the query timeout value set and the transaction
     *         is rolled back
     */
    @Override
    public List<Link> findAllByIdIn(Iterable<UUID> ids) {
        return jpaDaoFindAllWhereIn(Link.FIND_ALL_WHERE_ID_IN, "ids", ids);
    }

    @Override
    public long count() {
        return abstractCount();
    }

    /**
     * {@inheritDoc }
     * @param entity must not be {@literal null}.
     * @throws IllegalArgumentException if the instance is not an
     *          entity
     * @throws TransactionRequiredException if invoked on a
     *         container-managed entity manager of type
     *         <code>PersistenceContextType.TRANSACTION</code> and there is
     *         no transaction
     * @throws PersistenceException if the flush fails
     */
    @Override
    public Link save(Link entity) {
        return jpaDaoSave(entity);
    }

    @Override
    public Iterable<Link> saveAll(Iterable<Link> entities) {
        return abstractDaoSaveAll(entities);
    }

    @Override
    public void delete(UUID id) {
        abstractDaoDelete(id);
    }

    @Override
    public void deleteAll(Iterable<Link> entities) {
        abstractDaoDeleteAll(entities);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public List<Link> range(int start, int size) {
        return jpaRange(Link.FIND_ALL, start, size);
    }

    @Override
    EntityManager getEntityManager() {
        return entityManager;
    }

    @Override
    Logger getLogger() {
        return LOGGER;
    }

    @Override
    public Class<Link> getEClass() {
        return Link.class;
    }
}
