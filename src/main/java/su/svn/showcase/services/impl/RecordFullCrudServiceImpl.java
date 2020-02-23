/*
 * This file was last modified at 2020.02.22 17:46 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * RecordFullCrudServiceImpl.java
 * $Id$
 */

package su.svn.showcase.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import su.svn.showcase.dao.RecordDao;
import su.svn.showcase.domain.Record;
import su.svn.showcase.dto.NewsEntryDtoEnum;
import su.svn.showcase.dto.RecordFullDto;
import su.svn.showcase.dto.RecordTypesEnum;
import su.svn.showcase.exceptions.ErrorCase;
import su.svn.showcase.services.RecordFullCrudService;

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
public class RecordFullCrudServiceImpl extends AbstractUserTransactionService implements RecordFullCrudService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RecordFullCrudServiceImpl.class);

    @EJB(beanName = "RecordDaoJpa")
    private RecordDao recordDao;

    @Inject
    private UserTransaction userTransaction;

    private Consumer<Record> tagSavingConsumer(RecordFullDto tdo) {
        return entity -> {
            entity = tdo.update(entity);
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

    private void validateRecordId(RecordFullDto dto) {
        Objects.requireNonNull(dto);
        RecordTypesEnum type = RecordTypesEnum.valueOf(dto.getType());
        switch (type) {
            case NEWS_ENTRY_BASE:
            case NEWS_ENTRY_FULL:
                validateRecordNewsEntry(dto);
                break;
            default:
                throw new IllegalArgumentException("Ids of Record and NewsEntry must be equals!");
        }
    }

    private void validateRecordNewsEntry(RecordFullDto dto) {
        Objects.requireNonNull(dto.getNewsEntry());
        if ( ! dto.getId().equals(dto.getNewsEntry().getId())) {
            throw new IllegalArgumentException("Ids of Record and NewsEntry must be equals!");
        }
        if ( ! NewsEntryDtoEnum.isValid(dto.getType())) {
            throw new IllegalArgumentException("Ids of NewsEntry and Record must be equals!");
        }
    }
}
