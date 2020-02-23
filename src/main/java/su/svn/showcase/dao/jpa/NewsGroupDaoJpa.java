/*
 * This file was last modified at 2020.02.15 19:27 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * NewsGroupDaoJpa.java$
 * $Id$
 */

package su.svn.showcase.dao.jpa;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import su.svn.showcase.dao.NewsGroupDao;
import su.svn.showcase.domain.NewsGroup;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static su.svn.shared.Constants.Db.PERSISTENCE_UNIT_NAME;

@Stateless
public class NewsGroupDaoJpa extends AbstractDaoJpa<UUID, NewsGroup> implements NewsGroupDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(NewsGroupDaoJpa.class);

    @PersistenceUnit(unitName = PERSISTENCE_UNIT_NAME)
    private EntityManagerFactory emf;

    @Override
    public Optional<NewsGroup> findById(UUID id) {
        return abstractDaoFindById(id);
    }

    @Override
    public Optional<NewsGroup> findWhereGroup(String group) {
        return abstractDaoFindWhereField(NewsGroup.FIND_WHERE_GROUP, "group", group);
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
        return this.emf.createEntityManager();
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
