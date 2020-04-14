/*
 * This file was last modified at 2020.04.14 20:47 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * NewsEntryCrudServiceImpl.java
 * $Id$
 */

package su.svn.showcase.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import su.svn.showcase.converters.NewsEntryConverter;
import su.svn.showcase.dao.NewsEntryDao;
import su.svn.showcase.dao.RecordDao;
import su.svn.showcase.dao.UserLoginDao;
import su.svn.showcase.domain.NewsEntry;
import su.svn.showcase.domain.Record;
import su.svn.showcase.domain.UserLogin;
import su.svn.showcase.dto.*;
import su.svn.showcase.dto.enums.NewsEntryDtoEnum;
import su.svn.showcase.dto.jdo.NewsEntryJdo;
import su.svn.showcase.dto.RecordFullDto;
import su.svn.showcase.exceptions.ErrorCase;
import su.svn.showcase.services.NewsEntryCrudService;

import javax.annotation.Nonnull;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Stateless
public class NewsEntryCrudServiceImpl extends AbstractCrudService implements NewsEntryCrudService {

    private static final Logger LOGGER = LoggerFactory.getLogger(NewsEntryCrudServiceImpl.class);

    @EJB(beanName = "NewsEntryDaoEjb")
    private NewsEntryDao newsEntryDao;

    @EJB(beanName = "RecordDaoEjb")
    private RecordDao recordDao;

    @EJB(beanName = "UserLoginDaoEjb")
    private UserLoginDao userLoginDao;

    @EJB(beanName = "NewsEntryFullConverter")
    private NewsEntryConverter newsEntryFullConverter;

    @EJB(beanName = "NewsEntryPartConverter")
    private NewsEntryConverter newsEntryPartConverter;

    @Override
    @Transactional
    public void create(@Nonnull NewsEntryJdo dto) {
        validateOrFillRecordNewsEntryId(dto);
        create(dto, getUserLogin(dto));
    }

    @Override
    @Transactional
    public NewsEntryJdo readById(@Nonnull UUID id) {
        return newsEntryPartConverter.convert(newsEntryDao.findById(id).orElseThrow(ErrorCase::notFound));
    }

    @Override
    @Transactional
    public List<NewsEntryJdo> readRange(int start, int size) {
        return newsEntryDao.range(start, size).stream()
                .map(newsEntryPartConverter::convert)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void update(@Nonnull NewsEntryJdo dto) {
        validateId(dto);
        // TODO validate login
        update(getNewsEntry(dto.getId()), dto);
    }

    @Override
    @Transactional
    public void delete(@Nonnull UUID id) {
        newsEntryDao.delete(id);
    }

    @Override
    @Transactional
    public int count() {
        return (int) newsEntryDao.count();
    }

    @Override
    Logger getLogger() {
        return LOGGER;
    }

    private void create(NewsEntryJdo dto, UserLogin userLogin) {
        NewsEntry entity = newsEntryFullConverter.convert(dto);
        Record record = entity.getRecord();
        record.setUserLogin(userLogin);
        newsEntryDao.save(entity);
    }

    private void update(NewsEntry entity, NewsEntryJdo dto) {
        NewsEntryConverter.Updater.update(entity, dto);
        recordDao.save(entity.getRecord());
    }


    private NewsEntry getNewsEntry(UUID id) {
        return newsEntryDao.findById(id).orElseThrow(ErrorCase::notFound);
    }

    private UserLogin getUserLogin(NewsEntryJdo dto) {
        UserLoginDto userLogin = ((RecordFullDto) dto.getRecord()).getUserLogin();
        return getUserLogin(userLogin);
    }

    private UserLogin getUserLogin(UserLoginDto userLogin) {
        if (userLogin.getLogin() == null) {
            return userLoginDao.findById(userLogin.getId()).orElseThrow(ErrorCase::notFound);
        }
        return userLoginDao.findWhereLogin(userLogin.getLogin()).orElseThrow(ErrorCase::notFound);
    }

    private void validateOrFillRecordNewsEntryId(NewsEntryJdo dto) {
        Objects.requireNonNull(dto.getRecord());
        Objects.requireNonNull(dto.getNewsGroup());
        validateRecordUserLogin(dto.getRecord());
        if (dto.getId() == null) {
            UUID id = UUID.randomUUID();
            dto.setId(id);
            dto.getRecord().setId(id);
        } else if (notEquals(dto.getId(), dto.getRecord().getId())) {
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
            if ( ! (recordFullDto.getUserLogin() instanceof UserOnlyLoginDto)) {
                throw ErrorCase.bad("user login DTO", String.valueOf(recordFullDto.getUserLogin()));
            }
        }
    }

    private boolean notEquals(UUID id1, UUID id2) {
        return ! id1.equals(id2);
    }
}
//EOF
