/*
 * This file was last modified at 2020.02.16 00:13 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * NewsEntryBaseCrudServiceImpl.java$
 * $Id$
 */

package su.svn.showcase.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import su.svn.showcase.dao.NewsEntryDao;
import su.svn.showcase.domain.NewsEntry;
import su.svn.showcase.dto.NewsEntryBaseDto;
import su.svn.showcase.exceptions.NotFound;
import su.svn.showcase.services.NewsEntryBaseCrudService;

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
public class NewsEntryBaseCrudServiceImpl extends AbstractUserTransactionService implements NewsEntryBaseCrudService {

    private static final Logger LOGGER = LoggerFactory.getLogger(NewsEntryBaseCrudServiceImpl.class);

    @EJB(beanName = "NewsEntryDaoJpa")
    NewsEntryDao newsGroupDao;

    @Inject
    UserTransaction userTransaction;

    private Consumer<NewsEntry> tagSavingConsumer(NewsEntryBaseDto tdo) {
        return entity -> {
            tdo.update(entity);
            newsGroupDao.save(entity);
        };
    }

    @Override
    public void create(NewsEntryBaseDto dto) {
        Objects.requireNonNull(dto);
        consume(tagSavingConsumer(dto), new NewsEntry(UUID.randomUUID()));
    }

    @Override
    public NewsEntryBaseDto readById(UUID id) {
        Objects.requireNonNull(id);
        return new NewsEntryBaseDto(newsGroupDao.findById(id).orElseThrow(NotFound::is));
    }

    @Override
    public List<NewsEntryBaseDto> readRange(int start, int size) {
        return newsGroupDao.rangeOrderByTitleAsc(start, size).stream()
                .map(NewsEntryBaseDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public void update(NewsEntryBaseDto dto) {
        validateId(dto);
        consume(tagSavingConsumer(dto), new NewsEntry(dto.getId()));
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
