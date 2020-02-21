/*
 * This file was last modified at 2020.02.16 00:13 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * NewsEntryDaoJpa.java$
 * $Id$
 */

package su.svn.showcase.dao.jpa;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import su.svn.showcase.dao.NewsEntryDao;
import su.svn.showcase.domain.NewsEntry;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static su.svn.shared.Constants.Db.PERSISTENCE_UNIT_NAME;

@Stateless
public class NewsEntryDaoJpa extends AbstractDaoJpa<UUID, NewsEntry> implements NewsEntryDao {
    private static final Logger LOGGER = LoggerFactory.getLogger(NewsEntryDaoJpa.class);

    @PersistenceContext(unitName = PERSISTENCE_UNIT_NAME)
    private EntityManager entityManager;

    @Override
    public Optional<NewsEntry> findById(UUID id) {
        return abstractDaoFindById(id);
    }

    @Override
    public Optional<NewsEntry> findWhereTitle(String title) {
        return abstractDaoFindWhereField(NewsEntry.FIND_WHERE_TITLE, "title", title);
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

    @Override
    public NewsEntry save(NewsEntry entity) {
        return abstractDaoSave(entity);
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

    @Override
    public List<NewsEntry> range(int start, int size) {
        return jpaRange(NewsEntry.FIND_ALL, start, size);
    }

    @Override
    public List<NewsEntry> rangeOrderByTitleAsc(int start, int size) {
        return jpaRange(NewsEntry.FIND_ALL_ORDER_BY_TITLE_ASC, start, size);
    }

    @Override
    public List<NewsEntry> findAllOrderByTitleDesc(int start, int size) {
        return jpaRange(NewsEntry.FIND_ALL_ORDER_BY_TITLE_DESC, start, size);
    }

    @Override
    EntityManager getEntityManager() {
        return this.entityManager;
    }

    @Override
    public Logger getLogger() {
        return LOGGER;
    }

    @Override
    public Class<NewsEntry> getEClass() {
        return NewsEntry.class;
    }
}
