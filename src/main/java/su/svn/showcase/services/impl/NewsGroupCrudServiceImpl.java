/*
 * This file was last modified at 2020.04.14 19:52 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * NewsGroupCrudServiceImpl.java
 * $Id$
 */

package su.svn.showcase.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import su.svn.showcase.converters.NewsGroupConverter;
import su.svn.showcase.dao.NewsGroupDao;
import su.svn.showcase.domain.NewsGroup;
import su.svn.showcase.dto.jdo.NewsGroupJdo;
import su.svn.showcase.exceptions.NotFound;
import su.svn.showcase.services.NewsGroupCrudService;

import javax.annotation.Nonnull;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Stateless
public class NewsGroupCrudServiceImpl extends AbstractCrudService implements NewsGroupCrudService {

    private static final Logger LOGGER = LoggerFactory.getLogger(NewsGroupCrudServiceImpl.class);

    @EJB(beanName = "NewsGroupDaoEjb")
    private NewsGroupDao newsGroupDao;

    @EJB(beanName = "NewsGroupBaseConverter")
    private NewsGroupConverter newsGroupConverter;

    @Override
    @Transactional
    public void create(@Nonnull NewsGroupJdo dto) {
        checkAndSetId(dto);
        save(dto);
    }

    @Override
    @Transactional
    public NewsGroupJdo readById(@Nonnull UUID id) {
        return newsGroupConverter.convert(newsGroupDao.findById(id).orElseThrow(NotFound::is));
    }

    @Override
    @Transactional
    public NewsGroupJdo readByGroup(@Nonnull String group) {
        return newsGroupConverter.convert(newsGroupDao.findWhereGroup(group).orElseThrow(NotFound::is));
    }

    @Override
    @Transactional
    public List<NewsGroupJdo> readRange(int start, int size) {
        return newsGroupDao.rangeOrderByGroupAsc(start, size).stream()
                .map(newsGroupConverter::convert)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void update(@Nonnull NewsGroupJdo dto) {
        validateId(dto);
        save(dto);
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


    private void save(NewsGroupJdo dto) {
        NewsGroup entity = newsGroupConverter.convert(dto);
        newsGroupDao.save(entity);
    }
}
//EOF
