/*
 * This file was last modified at 2020.04.15 22:24 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * RecordCrudServiceImpl.java
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
import su.svn.showcase.dto.UserLoginDto;
import su.svn.showcase.dto.UserOnlyLoginDto;
import su.svn.showcase.dto.jdo.RecordJdo;
import su.svn.showcase.exceptions.ErrorCase;
import su.svn.showcase.services.RecordCrudService;

import javax.annotation.Nonnull;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Stateless(name = "RecordFullCrudService")
public class RecordCrudServiceImpl extends AbstractCrudService implements RecordCrudService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RecordCrudServiceImpl.class);

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
    public void create(@Nonnull RecordJdo dto) {
        validateId(dto);
        create(new Record(getOrGenerateUuidKey(dto)), dto);
    }

    private void create(Record entity, RecordJdo dto) {
        UserLogin userLogin = getUserLogin(dto.getUserLogin());
        validateUserLoginDto(userLogin, dto.getUserLogin());
        entity.setUserLogin(userLogin);
        entity = recordFullConverter.convert(dto);
        recordDao.save(entity);
    }

    @Override
    @Transactional
    public RecordJdo readById(@Nonnull UUID id) {
        return recordFullConverter.convert(recordDao.fetchById(id).orElseThrow(ErrorCase::notFound));
    }

    @Override
    @Transactional
    public List<RecordJdo> readRange(int start, int size) {
        return recordDao.range(start, size).stream()
                .map(recordPartConverter::convert)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void update(@Nonnull RecordJdo dto) {
        validateId(dto);
        validateRecordUserLogin(dto.getUserLogin());
        update(getRecord(dto.getId()), dto);
    }

    private void update(Record entity, RecordJdo dto) {
        UserLogin userLogin = getUserLogin(dto.getUserLogin());
        validateUserLoginDto(userLogin, dto.getUserLogin());
        entity.setUserLogin(userLogin);
        entity = recordFullConverter.update(entity, dto);
        recordDao.save(entity);
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
        if ( ! (dto instanceof UserOnlyLoginDto)) {
            throw ErrorCase.bad("user login DTO", String.valueOf(dto));
        }
    }

    @Override
    public int countByDay(LocalDate localDate) {
        return recordDao.countByDay(localDate);
    }

    @Override
    public List<RecordJdo> readRangeByDay(LocalDate localDate, int first, int pageSize) {
        return recordDao.rangeByDay(first, pageSize, localDate).stream()
                .map(recordPartConverter::convert)
                .collect(Collectors.toList());
    }
}
//EOF
