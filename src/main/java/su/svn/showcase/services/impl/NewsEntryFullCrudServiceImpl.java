/*
 * This file was last modified at 2020.02.21 22:20 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * NewsEntryFullCrudServiceImpl.java
 * $Id$
 */

package su.svn.showcase.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import su.svn.showcase.dao.NewsEntryDao;
import su.svn.showcase.domain.NewsEntry;
import su.svn.showcase.dto.NewsEntryFullDto;
import su.svn.showcase.exceptions.ErrorCase;
import su.svn.showcase.services.NewsEntryFullCrudService;

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
public class NewsEntryFullCrudServiceImpl extends AbstractUserTransactionService implements NewsEntryFullCrudService {

    private static final Logger LOGGER = LoggerFactory.getLogger(NewsEntryFullCrudServiceImpl.class);

    @EJB(beanName = "NewsEntryDaoJpa")
    NewsEntryDao newsEntryDao;

    @Inject
    UserTransaction userTransaction;

    private Consumer<NewsEntry> tagSavingConsumer(NewsEntryFullDto tdo) {
        return entity -> {
            entity = tdo.update(entity);
            System.out.println("entity = " + entity);
            newsEntryDao.save(entity);
        };
    }

    @Override
    public void create(NewsEntryFullDto dto) {
        validateNewsEntryId(dto);
        consume(tagSavingConsumer(dto), new NewsEntry(getOrGenerateUuidKey(dto)));
    }

    @Override
    public NewsEntryFullDto readById(UUID id) {
        Objects.requireNonNull(id);
        return new NewsEntryFullDto(newsEntryDao.findById(id).orElseThrow(ErrorCase::notFound));
    }

    @Override
    public List<NewsEntryFullDto> readRange(int start, int size) {
        return newsEntryDao.range(start, size).stream()
                .map(NewsEntryFullDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public void update(NewsEntryFullDto dto) {
        validateId(dto);
        validateNewsEntryId(dto);
        consume(tagSavingConsumer(dto), new NewsEntry(dto.getId()));
    }

    @Override
    public void delete(UUID id) {
        Objects.requireNonNull(id);
        newsEntryDao.delete(id);
    }

    @Override
    public int count() {
        return (int) newsEntryDao.count();
    }

    @Override
    UserTransaction getUserTransaction() {
        return this.userTransaction;
    }

    @Override
    Logger getLogger() {
        return LOGGER;
    }

    private void validateNewsEntryId(NewsEntryFullDto dto) {
        Objects.requireNonNull(dto);
        Objects.requireNonNull(dto.getRecord());
        if (dto.getId() == null) {
            UUID id = UUID.randomUUID();
            dto.setId(id);
            dto.getRecord().setId(id);
        }
        if ( ! dto.getId().equals(dto.getRecord().getId()))
            throw new IllegalArgumentException("Ids of NewsEntry and Record must be equals!");
    }
}
