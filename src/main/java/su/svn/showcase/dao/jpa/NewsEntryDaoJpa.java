/*
 * This file was last modified at 2020.03.03 22:49 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * NewsEntryDaoJpa.java
 * $Id$
 */

package su.svn.showcase.dao.jpa;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import su.svn.showcase.dao.NewsEntryDao;
import su.svn.showcase.domain.NewsEntry;

import javax.ejb.Stateless;
import javax.persistence.*;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static su.svn.shared.Constants.Db.PERSISTENCE_UNIT_NAME;

@Stateless
public class NewsEntryDaoJpa extends AbstractDaoJpa<UUID, NewsEntry> implements NewsEntryDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(NewsEntryDaoJpa.class);

    @PersistenceContext(unitName = PERSISTENCE_UNIT_NAME)
    private EntityManager entityManager;

    /**
     * {@inheritDoc }
     * @throws IllegalArgumentException if the first argument does
     *         not denote an entity type or the second argument is
     *         is not a valid type for that entitys primary key or
     *         is null
     */
    @Override
    public Optional<NewsEntry> findById(UUID id) {
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
    public Optional<NewsEntry> findWhereTitle(String title) {
        return jpaFindWhereField(NewsEntry.FIND_WHERE_TITLE, "title", title);
    }

    @Override
    public List<NewsEntry> findAll() {
        return abstractDaoFindAll(NewsEntry.FIND_ALL);
    }

    @Override
    public List<NewsEntry> findAllOrderByTitleAsc() {
        return abstractDaoFindAll(NewsEntry.FIND_ALL_ORDER_BY_TITLE_ASC);
    }

    @Override
    public List<NewsEntry> findAllOrderByTitleDesc() {
        return abstractDaoFindAll(NewsEntry.FIND_ALL_ORDER_BY_TITLE_DESC);
    }

    @Override
    public List<NewsEntry> findAllByIdIn(Iterable<UUID> ids) {
        return abstractDaoFindAllWhereIn(NewsEntry.FIND_ALL_WHERE_ID_IN, "ids", ids);
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
    public NewsEntry save(NewsEntry entity) {
        return jpaDaoSave(entity);
    }

    @Override
    public Iterable<NewsEntry> saveAll(Iterable<NewsEntry> entities) {
        return abstractDaoSaveAll(entities);
    }

    @Override
    public void delete(UUID id) {
        abstractDaoDelete(id);
    }

    @Override
    public void deleteAll(Iterable<NewsEntry> entities) {
        abstractDaoDeleteAll(entities);
    }

    @Override
    public List<NewsEntry> findAllWhereTitle(String title) {
        return abstractDaoFindAllWhereField(NewsEntry.FIND_WHERE_TITLE, "title", title);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public List<NewsEntry> range(int start, int size) {
        return jpaRange(NewsEntry.FIND_ALL, start, size);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public List<NewsEntry> rangeOrderByTitleAsc(int start, int size) {
        return jpaRange(NewsEntry.FIND_ALL_ORDER_BY_TITLE_ASC, start, size);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public List<NewsEntry> findAllOrderByTitleDesc(int start, int size) {
        return jpaRange(NewsEntry.FIND_ALL_ORDER_BY_TITLE_DESC, start, size);
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
    public Class<NewsEntry> getEClass() {
        return NewsEntry.class;
    }
}
