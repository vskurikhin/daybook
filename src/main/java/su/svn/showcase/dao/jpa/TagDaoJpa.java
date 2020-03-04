/*
 * This file was last modified at 2020.03.04 23:17 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * TagDaoJpa.java
 * $Id$
 */

package su.svn.showcase.dao.jpa;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import su.svn.showcase.dao.TagDao;
import su.svn.showcase.domain.Tag;
import su.svn.showcase.utils.CollectionUtil;

import javax.annotation.Nonnull;
import javax.persistence.*;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * The Tag DAO implementation.
 *
 * @author Victor N. Skurikhin
 */
public class TagDaoJpa extends AbstractDaoJpa<String, Tag> implements TagDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(TagDaoJpa.class);

    private static final Pattern validating = Pattern.compile("[\\w\\sа-яА-Я]+");

    private final EntityManager entityManager;

    public TagDaoJpa(@Nonnull EntityManager entityManager) {
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
    public Optional<Tag> findById(String id) {
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
    public Optional<Tag> findWhereTag(String tag) {
        return jpaFindWhereField(Tag.FIND_WHERE_TAG, "tag", tag);
    }

    @Override
    public long count() {
        return abstractCount();
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public List<Tag> findAll() {
        return abstractDaoFindAll(Tag.FIND_ALL);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public List<Tag> findAllOrderByTagAsc() {
        return abstractDaoFindAll(Tag.FIND_ALL_ORDER_BY_TAG_ASC);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public List<Tag> findAllOrderByTagDesc() {
        return abstractDaoFindAll(Tag.FIND_ALL_ORDER_BY_TAG_DESC);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public List<Tag> findAllWhereTag(String tag) {
        return abstractDaoFindAllWhereField(Tag.FIND_WHERE_TAG, "tag", tag);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public List<Tag> findAllByIdIn(Iterable<String> ids) {
        return abstractDaoFindAllWhereIn(Tag.FIND_ALL_WHERE_ID_IN, "ids", ids);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public List<Tag> findAllByTagIn(Iterable<String> tags) {
        return abstractDaoFindAllWhereIn(Tag.FIND_ALL_WHERE_TAG_IN, "tags", tags);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public List<Tag> range(int start, int size) {
        return jpaRange(Tag.FIND_ALL, start, size);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public List<Tag> rangeOrderByTagAsc(int start, int size) {
        return jpaRange(Tag.FIND_ALL_ORDER_BY_TAG_ASC, start, size);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public List<Tag> rangeOrderByTagDesc(int start, int size) {
        return jpaRange(Tag.FIND_ALL_ORDER_BY_TAG_DESC, start, size);
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
    public Tag save(Tag entity) {
        return jpaDaoSave(entity);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public Iterable<Tag> saveAll(Iterable<Tag> entities) {
        return abstractDaoSaveAll(entities);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void delete(String id) {
        abstractDaoDelete(id);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void deleteAll(Iterable<Tag> entities) {
        abstractDaoDeleteAll(entities);
    }

    private boolean isValidListOfTags(Iterable<String> tags) {
        for (String tag : tags) {
            if ( ! validating.matcher(tag).matches()) {
                return false;
            }
        }
        return true;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public List<String> outerSection(Iterable<String> tags) {
        if ( ! isValidListOfTags(tags)) {
            throw new RuntimeException("Isn't valid tag in list: " + tags);
        }
        String values = CollectionUtil.iterableToStream(tags)
                .map(s -> "('" + s + "')")
                .collect(Collectors.joining(","));
        String sql = String.format(Tag.OUTER_SECTION, values);
        return abstractDaoNativeResultList(sql, String.class);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    EntityManager getEntityManager() {
        return this.entityManager;
    }

    @Override
    Logger getLogger() {
        return LOGGER;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public Class<Tag> getEClass() {
        return Tag.class;
    }
}
//EOF
