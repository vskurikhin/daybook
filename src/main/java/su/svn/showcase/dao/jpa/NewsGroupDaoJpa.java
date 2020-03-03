/*
 * This file was last modified at 2020.03.03 20:33 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * NewsGroupDaoJpa.java
 * $Id$
 */

package su.svn.showcase.dao.jpa;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import su.svn.showcase.dao.NewsGroupDao;
import su.svn.showcase.domain.NewsGroup;

import javax.ejb.Stateless;
import javax.persistence.*;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static su.svn.shared.Constants.Db.PERSISTENCE_UNIT_NAME;

@Stateless
public class NewsGroupDaoJpa extends AbstractDaoJpa<UUID, NewsGroup> implements NewsGroupDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(NewsGroupDaoJpa.class);

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
    public Optional<NewsGroup> findById(UUID id) {
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
    public Optional<NewsGroup> findWhereGroup(String group) {
        return jpaFindWhereField(NewsGroup.FIND_WHERE_GROUP, "group", group);
    }

    @Override
    public List<NewsGroup> findAll() {
        return abstractDaoFindAll(NewsGroup.FIND_ALL);
    }

    @Override
    public List<NewsGroup> findAllOrderByGroupAsc() {
        return abstractDaoFindAll(NewsGroup.FIND_ALL_ORDER_BY_GROUP_ASC);
    }

    @Override
    public List<NewsGroup> findAllOrderByGroupDesc() {
        return abstractDaoFindAll(NewsGroup.FIND_ALL_ORDER_BY_GROUP_DESC);
    }

    @Override
    public List<NewsGroup> findAllByIdIn(Iterable<UUID> ids) {
        return abstractDaoFindAllWhereIn(NewsGroup.FIND_ALL_WHERE_ID_IN, "ids", ids);
    }

    @Override
    public long count() {
        return abstractCount();
    }

    @Override
    public NewsGroup save(NewsGroup entity) {
        return abstractDaoSave(entity);
    }

    @Override
    public Iterable<NewsGroup> saveAll(Iterable<NewsGroup> entities) {
        return abstractDaoSaveAll(entities);
    }

    @Override
    public void delete(UUID id) {
        abstractDaoDelete(id);
    }

    @Override
    public void deleteAll(Iterable<NewsGroup> entities) {
        abstractDaoDeleteAll(entities);
    }

    @Override
    public List<NewsGroup> findAllWhereGroup(String group) {
        return abstractDaoFindAllWhereField(NewsGroup.FIND_WHERE_GROUP, "group", group);
    }

    @Override
    public List<NewsGroup> range(int start, int size) {
        return jpaRange(NewsGroup.FIND_ALL, start, size);
    }

    @Override
    public List<NewsGroup> rangeOrderByGroupAsc(int start, int size) {
        return jpaRange(NewsGroup.FIND_ALL_ORDER_BY_GROUP_ASC, start, size);
    }

    @Override
    public List<NewsGroup> rangeOrderByGroupDesc(int start, int size) {
        return jpaRange(NewsGroup.FIND_ALL_ORDER_BY_GROUP_DESC, start, size);
    }

    @Override
    EntityManager getEntityManager() {
        return this.entityManager;
    }

    @Override
    Logger getLogger() {
        return LOGGER;
    }

    @Override
    public Class<NewsGroup> getEClass() {
        return NewsGroup.class;
    }
}
//EOF
