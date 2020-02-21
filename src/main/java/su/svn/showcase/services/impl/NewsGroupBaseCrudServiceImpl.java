/*
 * This file was last modified at 2020.02.15 19:55 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * NewsGroupBaseCrudServiceImpl.java$
 * $Id$
 */

package su.svn.showcase.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import su.svn.showcase.dao.NewsGroupDao;
import su.svn.showcase.domain.NewsGroup;
import su.svn.showcase.dto.NewsGroupBaseDto;
import su.svn.showcase.exceptions.NotFound;
import su.svn.showcase.services.NewsGroupBaseCrudService;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;
import javax.transaction.UserTransaction;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class NewsGroupBaseCrudServiceImpl extends AbstractUserTransactionService implements NewsGroupBaseCrudService {

    private static final Logger LOGGER = LoggerFactory.getLogger(NewsGroupBaseCrudServiceImpl.class);

    @EJB(beanName = "NewsGroupDaoJpa")
    NewsGroupDao newsGroupDao;

    @Inject
    UserTransaction userTransaction;

    private Consumer<NewsGroup> tagSavingConsumer(NewsGroupBaseDto tdo) {
        return entity -> {
            tdo.update(entity);
            newsGroupDao.save(entity);
        };
    }

    @Override
    public void create(NewsGroupBaseDto dto) {
        Objects.requireNonNull(dto);
        consume(tagSavingConsumer(dto), new NewsGroup(UUID.randomUUID()));
    }

    @Override
    public NewsGroupBaseDto readById(UUID id) {
        Objects.requireNonNull(id);
        return new NewsGroupBaseDto(newsGroupDao.findById(id).orElseThrow(NotFound::is));
    }

    @Override
    public List<NewsGroupBaseDto> readRange(int start, int size) {
        return newsGroupDao.rangeOrderByGroupAsc(start, size).stream()
                .map(NewsGroupBaseDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public void update(NewsGroupBaseDto dto) {
        validateId(dto);
        consume(tagSavingConsumer(dto), new NewsGroup(dto.getId()));
    }

    @Override
    public void delete(UUID id) {
        Objects.requireNonNull(id);
        newsGroupDao.delete(id);
    }

    @Override
    public int count() {
        return (int) newsGroupDao.count();
    }

    @Override
    UserTransaction getUserTransaction() {
        return this.userTransaction;
    }

    @Override
    Logger getLogger() {
        return LOGGER;
    }
}
