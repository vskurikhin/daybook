/*
 * This file was last modified at 2020.03.20 19:57 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * NewsLinksDaoJpa.java
 * $Id$
 */

package su.svn.showcase.dao.jpa;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import su.svn.showcase.dao.NewsLinksDao;
import su.svn.showcase.domain.NewsLinks;

import javax.annotation.Nonnull;
import javax.persistence.*;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * The NewsLinks DAO implementation.
 *
 * @author Victor N. Skurikhin
 */
public class NewsLinksDaoJpa extends AbstractDaoJpa<UUID, NewsLinks> implements NewsLinksDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(NewsLinksDaoJpa.class);

    private final EntityManager entityManager;

    public NewsLinksDaoJpa(@Nonnull EntityManager entityManager) {
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
    public Optional<NewsLinks> findById(UUID id) {
        return jpaFindById(id);
    }

    /**
     * {@inheritDoc }
     * @throws IllegalArgumentException if a query has not been
     *         defined with the given name or if the query string is
     *         found to be invalid or if the query result is found to
     *         not be assignable to the specified type or if called for a Java
     *         Persistence query language UPDATE or DELETE statement
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
    public Optional<NewsLinks> findWhereTitle(String title) {
        return jpaFindWhereField(NewsLinks.FIND_WHERE_TITLE, "title", title);
    }

    /**
     * {@inheritDoc }
     * @throws IllegalArgumentException if a query has not been
     *         defined with the given name or if the query string is
     *         found to be invalid or if the query result is found to
     *         not be assignable to the specified type
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
     * @return
     */
    @Override
    public List<NewsLinks> findAll() {
        return jpaDaoFindAll(NewsLinks.FIND_ALL);
    }

    /**
     * {@inheritDoc }
     * @throws IllegalArgumentException if a query has not been
     *         defined with the given name or if the query string is
     *         found to be invalid or if the query result is found to
     *         not be assignable to the specified type
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
     * @return
     */
    @Override
    public List<NewsLinks> findAllOrderByTitleAsc() {
        return jpaDaoFindAll(NewsLinks.FIND_ALL_ORDER_BY_TITLE_ASC);
    }

    /**
     * {@inheritDoc }
     * @throws IllegalArgumentException if a query has not been
     *         defined with the given name or if the query string is
     *         found to be invalid or if the query result is found to
     *         not be assignable to the specified type
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
     * @return
     */
    @Override
    public List<NewsLinks> findAllOrderByTitleDesc() {
        return jpaDaoFindAll(NewsLinks.FIND_ALL_ORDER_BY_TITLE_DESC);
    }

    /**
     * {@inheritDoc }
     * @param ids - possible values.
     * @return list of NewsLinks
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
    public List<NewsLinks> findAllByIdIn(Iterable<UUID> ids) {
        return jpaDaoFindAllWhereIn(NewsLinks.FIND_ALL_WHERE_ID_IN, "ids", ids);
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
    public NewsLinks save(NewsLinks entity) {
        return jpaDaoSave(entity);
    }

    @Override
    public Iterable<NewsLinks> saveAll(Iterable<NewsLinks> entities) {
        return abstractDaoSaveAll(entities);
    }

    @Override
    public void delete(UUID id) {
        abstractDaoDelete(id);
    }

    @Override
    public void deleteAll(Iterable<NewsLinks> entities) {
        abstractDaoDeleteAll(entities);
    }

    /**
     * {@inheritDoc }
     * @throws IllegalArgumentException if a query has not been
     *         defined with the given name or if the query string is
     *         found to be invalid or if the query result is found to
     *         not be assignable to the specified type
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
    public List<NewsLinks> findAllWhereTitle(String title) {
        return jpaDaoFindAllWhereField(NewsLinks.FIND_WHERE_TITLE, "title", title);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public List<NewsLinks> range(int start, int size) {
        return jpaRange(NewsLinks.FIND_ALL, start, size);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public List<NewsLinks> rangeOrderByTitleAsc(int start, int size) {
        return jpaRange(NewsLinks.FIND_ALL_ORDER_BY_TITLE_ASC, start, size);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public List<NewsLinks> findAllOrderByTitleDesc(int start, int size) {
        return jpaRange(NewsLinks.FIND_ALL_ORDER_BY_TITLE_DESC, start, size);
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
    public Class<NewsLinks> getEClass() {
        return NewsLinks.class;
    }
}
