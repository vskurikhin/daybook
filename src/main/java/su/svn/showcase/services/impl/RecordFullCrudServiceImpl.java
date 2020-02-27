/*
 * This file was last modified at 2020.02.27 18:02 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * RecordFullCrudServiceImpl.java
 * $Id$
 */

package su.svn.showcase.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import su.svn.showcase.dao.RecordDao;
import su.svn.showcase.dao.UserLoginDao;
import su.svn.showcase.domain.Record;
import su.svn.showcase.domain.UserLogin;
import su.svn.showcase.dto.*;
import su.svn.showcase.exceptions.ErrorCase;
import su.svn.showcase.services.RecordFullCrudService;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;
import javax.transaction.UserTransaction;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class RecordFullCrudServiceImpl extends AbstractUserTransactionService implements RecordFullCrudService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RecordFullCrudServiceImpl.class);

    @EJB(beanName = "RecordDaoJpa")
    private RecordDao recordDao;

    @EJB(beanName = "UserLoginDaoJpa")
    private UserLoginDao userLoginDao;

    @Inject
    private UserTransaction userTransaction;

    private Consumer<Record> tagSavingConsumer(RecordFullDto dto) {
        return entity -> {
            UserLogin userLogin = userLoginDao.findById(dto.getUserLogin().getId())
                    .orElseThrow(IllegalArgumentException::new);
            validateUserLoginDto(userLogin, dto.getUserLogin());
            entity.setUserLogin(userLogin);
            entity = dto.update(entity);
            recordDao.save(entity);
        };
    }

    @Override
    public void create(RecordFullDto dto) {
        validateRecordId(dto);
        consume(tagSavingConsumer(dto), new Record(getOrGenerateUuidKey(dto)));
    }

    @Override
    public RecordFullDto readById(UUID id) {
        Objects.requireNonNull(id);
        return new RecordFullDto(recordDao.findById(id).orElseThrow(ErrorCase::notFound));
    }

    @Override
    public List<RecordFullDto> readRange(int start, int size) {
        return recordDao.range(start, size).stream()
                .map(RecordFullDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public void update(RecordFullDto dto) {
        validateId(dto);
        validateRecordId(dto);
        consume(tagSavingConsumer(dto), new Record(dto.getId()));
    }

    @Override
    public void delete(UUID id) {
        Objects.requireNonNull(id);
        recordDao.delete(id);
    }

    @Override
    public int count() {
        return (int) recordDao.count();
    }

    @Override
    UserTransaction getUserTransaction() {
        return this.userTransaction;
    }

    @Override
    Logger getLogger() {
        return LOGGER;
    }

    private void validateUserLoginDto(UserLogin userLogin, UserLoginDto dto) {
        if ( ! userLogin.getLogin().equals(dto.getLogin())) {
            throw ErrorCase.bad("UserLogin DTO", dto.toString());
        }
    }

    private void validateRecordId(RecordFullDto dto) {
        Objects.requireNonNull(dto);
        Objects.requireNonNull(dto.getUserLogin());
        RecordTypesEnum type = RecordTypesEnum.valueOf(dto.getType());
        switch (type) {
            case NewsEntryBaseDto:
            case NewsEntryFullDto:
                validateRecordNewsEntry(dto);
                break;
            default:
                throw ErrorCase.unknownType(type.toString());
        }
    }

    private void validateRecordNewsEntry(RecordFullDto dto) {
        Objects.requireNonNull(dto.getNewsEntry());
        if ( ! dto.getId().equals(dto.getNewsEntry().getId())) {
            throw ErrorCase.doesntEquals("Ids of Record and NewsEntry DTO", dto.getId(), dto.getNewsEntry().getId());
        }
        if ( ! NewsEntryDtoEnum.containsValue(dto.getType())) {
            throw ErrorCase.unknownType(dto.getType());
        }
    }
}
