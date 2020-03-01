/*
 * This file was last modified at 2020.03.01 16:57 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * NewsEntryFullCrudServiceImpl.java
 * $Id$
 */

package su.svn.showcase.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import su.svn.showcase.dao.NewsEntryDao;
import su.svn.showcase.dao.RecordDao;
import su.svn.showcase.dao.UserLoginDao;
import su.svn.showcase.domain.NewsEntry;
import su.svn.showcase.domain.Record;
import su.svn.showcase.domain.UserLogin;
import su.svn.showcase.dto.*;
import su.svn.showcase.exceptions.ErrorCase;
import su.svn.showcase.services.NewsEntryFullCrudService;

import javax.annotation.Nonnull;
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

    @EJB(beanName = "RecordDaoJpa")
    private RecordDao recordDao;

    @EJB(beanName = "UserLoginDaoJpa")
    private UserLoginDao userLoginDao;

    @Inject
    private UserTransaction userTransaction;

    @Override
    public void create(@Nonnull NewsEntryFullDto dto) {
        validateOrFillRecordNewsEntryId(dto);
        consume(storageConsumer(dto), new NewsEntry(getOrGenerateUuidKey(dto)));
    }

    @Override
    public NewsEntryFullDto readById(@Nonnull UUID id) {
        return new NewsEntryFullDto(newsEntryDao.findById(id)
                .orElseThrow(ErrorCase::notFound));
    }

    @Override
    public List<NewsEntryFullDto> readRange(int start, int size) {
        return newsEntryDao.range(start, size).stream()
                .map(NewsEntryFullDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public void update(@Nonnull NewsEntryFullDto dto) {
        validateRecordNewsEntryAndUserOnlyLogin(dto);
        consume(storageConsumer(dto), null);
    }

    @Override
    public void delete(@Nonnull UUID id) {
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

    private Consumer<NewsEntry> storageConsumer(NewsEntryFullDto dto) {
        return entity -> {
            if (entity == null) { // update
                entity = newsEntryDao.findById(dto.getId()).orElseThrow(ErrorCase::notFound);
                entity = dto.update(entity, entity.getRecord().getUserLogin());
                newsEntryDao.save(entity);
            } else if (dto.getRecord() instanceof RecordFullDto) { // create
                UserLoginDto userLogin = ((RecordFullDto) dto.getRecord()).getUserLogin();
                entity = dto.update(entity, getUserLogin(userLogin));
                Record record = entity.getRecord();
                recordDao.save(record);
            } else
                throw ErrorCase.open(LOGGER, "Can't save DTO {} and {}", dto, entity);
        };
    }

    private UserLogin getUserLogin(UserLoginDto userLogin) {
        if (userLogin.getLogin() == null) {
            return userLoginDao.findById(userLogin.getId()).orElseThrow(ErrorCase::notFound);
        }
        return userLoginDao.findWhereLogin(userLogin.getLogin()).orElseThrow(ErrorCase::notFound);
    }

    private void validateRecordNewsEntryAndUserOnlyLogin(NewsEntryFullDto dto) {
        validateId(dto);
        validateRecordNewsEntryId(dto);
        validateRecordUserLogin(dto.getRecord());
    }

    private void validateRecordNewsEntryId(NewsEntryFullDto dto) {
        Objects.requireNonNull(dto.getRecord());
        if ( ! dto.getId().equals(dto.getRecord().getId())) {
            throw ErrorCase.doesntEquals("Ids of Record and NewsEntry DTO", dto.getRecord().getId(), dto.getId());
        }
        if ( ! NewsEntryDtoEnum.containsValue(dto.getRecord().getType())) {
            throw ErrorCase.unknownType(dto.getRecord().getType());
        }
    }

    private void validateOrFillRecordNewsEntryId(NewsEntryFullDto dto) {
        Objects.requireNonNull(dto.getRecord());
        Objects.requireNonNull(dto.getNewsGroup());
        validateRecordUserLogin(dto.getRecord());
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

    private void validateRecordUserLogin(RecordDto dto) {
        if (dto instanceof RecordFullDto) {
            RecordFullDto recordFullDto = (RecordFullDto) dto;
            if ( ! (recordFullDto.getUserLogin() instanceof UserOnlyLoginBaseDto)) {
                throw ErrorCase.bad("user login DTO", String.valueOf(recordFullDto.getUserLogin()));
            }
        }
    }
}
//EOF
