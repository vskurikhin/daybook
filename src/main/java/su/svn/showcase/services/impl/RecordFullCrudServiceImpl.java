/*
 * This file was last modified at 2020.04.05 22:40 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * RecordFullCrudServiceImpl.java
 * $Id$
 */

package su.svn.showcase.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import su.svn.showcase.converters.RecordConverter;
import su.svn.showcase.dao.RecordDao;
import su.svn.showcase.dao.UserLoginDao;
import su.svn.showcase.domain.Record;
import su.svn.showcase.domain.UserLogin;
import su.svn.showcase.dto.*;
import su.svn.showcase.exceptions.ErrorCase;
import su.svn.showcase.services.RecordFullCrudService;

import javax.annotation.Nonnull;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Stateless(name = "RecordFullCrudService")
public class RecordFullCrudServiceImpl extends AbstractCrudService implements RecordFullCrudService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RecordFullCrudServiceImpl.class);

    @EJB(beanName = "RecordDaoEjb")
    private RecordDao recordDao;

    @EJB(beanName = "UserLoginDaoEjb")
    private UserLoginDao userLoginDao;

    @EJB(beanName = "RecordFullConverter")
    private RecordConverter recordFullConverter;

    @EJB(beanName = "RecordPartConverter")
    private RecordConverter recordPartConverter;

    @Override
    @Transactional
    public void create(@Nonnull RecordFullDto dto) {
        validateId(dto);
        saveUpdatedEntity(new Record(getOrGenerateUuidKey(dto)), dto);
    }

    @Override
    @Transactional
    public RecordFullDto readById(@Nonnull UUID id) {
        return recordPartConverter.convert(recordDao.fetchById(id).orElseThrow(ErrorCase::notFound));
    }

    @Override
    @Transactional
    public List<RecordFullDto> readRange(int start, int size) {
        System.err.println("recordPartConverter = " + recordPartConverter);
        return recordDao.range(start, size).stream()
                .map(recordPartConverter::convert)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void update(@Nonnull RecordFullDto dto) {
        validateId(dto);
        validateRecordUserLogin(dto.getUserLogin());
        saveUpdatedEntity(getRecord(dto.getId()), dto);
    }

    @Override
    @Transactional
    public void delete(@Nonnull UUID id) {
        recordDao.delete(id);
    }

    @Override
    @Transactional
    public int count() {
        return (int) recordDao.count();
    }

    @Override
    Logger getLogger() {
        return LOGGER;
    }

    private void saveUpdatedEntity(Record entity, RecordFullDto dto) {
        UserLogin userLogin = getUserLogin(dto.getUserLogin());
        validateUserLoginDto(userLogin, dto.getUserLogin());
        entity.setUserLogin(userLogin);
        // entity = dto.update(entity);
        entity = recordFullConverter.convert(dto);
        recordDao.save(entity);
    }

    private Record getRecord(UUID id) {
        return recordDao.findById(id).orElseThrow(ErrorCase::notFound);
    }

    private void validateUserLoginDto(UserLogin userLogin, UserLoginDto dto) {
        if ( ! userLogin.getLogin().equals(dto.getLogin())) {
            throw ErrorCase.bad("UserLogin DTO", dto.toString());
        }
    }

    private UserLogin getUserLogin(UserLoginDto userLogin) {
        if (userLogin.getLogin() == null) {
            return userLoginDao.findById(userLogin.getId()).orElseThrow(ErrorCase::notFound);
        }
        return userLoginDao.findWhereLogin(userLogin.getLogin()).orElseThrow(ErrorCase::notFound);
    }

    private void validateRecordUserLogin(UserLoginDto dto) {
        if ( ! (dto instanceof UserOnlyLoginBaseDto)) {
            throw ErrorCase.bad("user login DTO", String.valueOf(dto));
        }
    }

    @Override
    public int countByDay(LocalDate localDate) {
        return recordDao.countByDay(localDate);
    }

    @Override
    public List<RecordFullDto> readRangeByDay(LocalDate localDate, int first, int pageSize) {
        return recordDao.rangeByDay(first, pageSize, localDate).stream()
                .map(recordPartConverter::convert)
                .collect(Collectors.toList());
    }
}
//EOF
