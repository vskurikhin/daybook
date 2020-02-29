/*
 * This file was last modified at 2020.02.24 20:09 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * NewsEntryFullCrudServiceImpl.java
 * $Id$
 */

package su.svn.showcase.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import su.svn.showcase.dao.NewsEntryDao;
import su.svn.showcase.dao.UserLoginDao;
import su.svn.showcase.domain.NewsEntry;
import su.svn.showcase.domain.UserLogin;
import su.svn.showcase.dto.*;
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
    private NewsEntryDao newsEntryDao;

    @EJB(beanName = "UserLoginDaoJpa")
    private UserLoginDao userLoginDao;

    @Inject
    private UserTransaction userTransaction;

    private Consumer<NewsEntry> tagSavingConsumer(NewsEntryFullDto dto) {
        return entity -> {

            if (dto.getRecord() instanceof RecordFullDto) {
                UUID userLoginId = ((RecordFullDto) dto.getRecord()).getUserLogin().getId();
                entity = dto.update(entity, getUserLogin(userLoginId));
                newsEntryDao.save(entity);
            }
        };
    }

    @Override
    public void create(NewsEntryFullDto dto) {
        validateOrFillRecordNewsEntryId(dto);
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
        validateRecordNewsEntryId(dto);
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

    private UserLogin getUserLogin(UUID id) {
        System.out.println("id = " + id); // TODO remove
        return userLoginDao.findById(id).orElseThrow(ErrorCase::notFound);
    }

    private void validateRecordNewsEntryId(NewsEntryFullDto dto) {
        Objects.requireNonNull(dto);
        Objects.requireNonNull(dto.getRecord());
        if ( ! dto.getId().equals(dto.getRecord().getId())) {
            throw ErrorCase.doesntEquals("Ids of Record and NewsEntry DTO", dto.getRecord().getId(), dto.getId());
        }
        if ( ! NewsEntryDtoEnum.containsValue(dto.getRecord().getType())) {
            throw ErrorCase.unknownType(dto.getRecord().getType());
        }
    }

    private void validateOrFillRecordNewsEntryId(NewsEntryFullDto dto) {
        Objects.requireNonNull(dto);
        Objects.requireNonNull(dto.getRecord());
        if (dto.getId() == null) {
            UUID id = UUID.randomUUID();
            dto.setId(id);
            dto.getRecord().setId(id);
        } else if ( ! dto.getId().equals(dto.getRecord().getId())) {
            throw ErrorCase.doesntEquals("Ids of Record and NewsEntry DTO", dto.getRecord().getId(), dto.getId());
        }
        if (dto.getRecord().getType() == null) {
            dto.getRecord().setType(dto.getDtoClass().getSimpleName());
        } else if ( ! NewsEntryDtoEnum.containsValue(dto.getRecord().getType())) {
            throw ErrorCase.unknownType(dto.getRecord().getType());
        }
    }
}
