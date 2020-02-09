/*
 * This file was last modified at 2020.02.09 13:36 by Victor N. Skurikhin.
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

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static su.svn.shared.Constants.Db.PERSISTENCE_UNIT_NAME;

/**
 * The Tag DAO implementation.
 */
@Stateless
public class TagDaoJpa extends AbstractDaoJpa<String, Tag> implements TagDao {
    private static final Logger LOGGER = LoggerFactory.getLogger(TagDaoJpa.class);
    private static final Pattern validating = Pattern.compile("[\\w\\sа-яА-Я]+");

    @PersistenceContext(unitName = PERSISTENCE_UNIT_NAME)
    private EntityManager entityManager;

    /**
     * {@inheritDoc }
     */
    @Override
    public EntityManager getEntityManager() {
        return this.entityManager;
    }

    @Override
    public Logger getLogger() {
        return LOGGER;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public Class<Tag> getEClass() {
        return Tag.class;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public Optional<Tag> findById(String id) {
        return abstractDaoFindById(id);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public Optional<Tag> findWhereTag(String tag) {
        return abstractDaoFindWhereField(Tag.FIND_WHERE_TAG, "tag", tag);
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
    public List<Tag> findAllWhereTag(String tag) {
        return abstractDaoFindAllWhereField(Tag.FIND_WHERE_TAG, "tag", tag);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public List<Tag> findAllByIdIn(Iterable<String> uuids) {
        List<String> list = CollectionUtil.iterableToList(uuids);
        return abstractDaoFindAllWhereIn(Tag.FIND_ALL_WHERE_ID_IN, "ids", list);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public List<Tag> findAllByTagIn(Iterable<String> tags) {
        List<String> list = CollectionUtil.iterableToList(tags);
        return abstractDaoFindAllWhereIn(Tag.FIND_ALL_WHERE_TAG_IN, "tags", list);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public boolean save(Tag entity) {
        return abstractDaoSave(entity);
    }

    @Override
    public boolean saveAll(Iterable<Tag> entities) {
        return abstractDaoSaveAll(entities);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public boolean delete(String id) {
        return abstractDaoDelete(id);
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
}
//EOF
