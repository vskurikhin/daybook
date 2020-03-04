/*
 * This file was last modified at 2020.03.03 20:33 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * NewsGroupBaseCrudServiceImpl.java
 * $Id$
 */

package su.svn.showcase.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import su.svn.showcase.dao.NewsGroupDao;
import su.svn.showcase.domain.NewsGroup;
import su.svn.showcase.dto.NewsGroupBaseDto;
import su.svn.showcase.exceptions.ErrorCase;
import su.svn.showcase.exceptions.NotFound;
import su.svn.showcase.services.NewsGroupBaseCrudService;

import javax.annotation.Nonnull;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Stateless
public class NewsGroupBaseCrudServiceImpl extends AbstractCrudService implements NewsGroupBaseCrudService {

    private static final Logger LOGGER = LoggerFactory.getLogger(NewsGroupBaseCrudServiceImpl.class);

    @EJB(beanName = "NewsGroupDaoEjb")
    private NewsGroupDao newsGroupDao;

    @Override
    @Transactional
    public void create(@Nonnull NewsGroupBaseDto dto) {
        checkAndSetId(dto);
        saveUpdatedEntity(new NewsGroup(dto.getId()), dto);
    }

    @Override
    @Transactional
    public NewsGroupBaseDto readById(@Nonnull UUID id) {
        return new NewsGroupBaseDto(newsGroupDao.findById(id)
                .orElseThrow(NotFound::is));
    }

    @Override
    @Transactional
    public NewsGroupBaseDto readByGroup(@Nonnull String group) {
        return new NewsGroupBaseDto(newsGroupDao.findWhereGroup(group)
                .orElseThrow(NotFound::is));
    }

    @Override
    @Transactional
    public List<NewsGroupBaseDto> readRange(int start, int size) {
        return newsGroupDao.rangeOrderByGroupAsc(start, size).stream()
                .map(NewsGroupBaseDto::new)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void update(@Nonnull NewsGroupBaseDto dto) {
        validateId(dto);
        saveUpdatedEntity(getNewsGroup(dto.getId()), dto);
    }

    @Override
    @Transactional
    public void delete(@Nonnull UUID id) {
        Objects.requireNonNull(id);
        newsGroupDao.delete(id);
    }

    @Override
    @Transactional
    public int count() {
        return (int) newsGroupDao.count();
    }

    @Override
    Logger getLogger() {
        return LOGGER;
    }

    private void saveUpdatedEntity(NewsGroup entity, NewsGroupBaseDto dto) {
        entity = dto.update(entity);
        newsGroupDao.save(entity);
    }

    private NewsGroup getNewsGroup(UUID id) {
        return newsGroupDao.findById(id).orElseThrow(ErrorCase::notFound);
    }
}
//EOF
