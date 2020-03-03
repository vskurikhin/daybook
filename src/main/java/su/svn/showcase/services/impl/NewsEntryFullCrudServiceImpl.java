/*
 * This file was last modified at 2020.03.03 22:49 by Victor N. Skurikhin.
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
import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Stateless
public class NewsEntryFullCrudServiceImpl extends AbstractCrudService implements NewsEntryFullCrudService {

    private static final Logger LOGGER = LoggerFactory.getLogger(NewsEntryFullCrudServiceImpl.class);

    @EJB(beanName = "NewsEntryDaoJpa")
    private NewsEntryDao newsEntryDao;

    @EJB(beanName = "RecordDaoJpa")
    private RecordDao recordDao;

    @EJB(beanName = "UserLoginDaoJpa")
    private UserLoginDao userLoginDao;

    @Override
    @Transactional
    public void create(@Nonnull NewsEntryFullDto dto) {
        validateOrFillRecordNewsEntryId(dto);
        create(new NewsEntry(getOrGenerateUuidKey(dto)), dto);
    }

    @Override
    @Transactional
    public NewsEntryFullDto readById(@Nonnull UUID id) {
        return new NewsEntryFullDto(newsEntryDao.findById(id)
                .orElseThrow(ErrorCase::notFound));
    }

    @Override
    @Transactional
    public List<NewsEntryFullDto> readRange(int start, int size) {
        return newsEntryDao.range(start, size).stream()
                .map(NewsEntryFullDto::new)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void update(@Nonnull NewsEntryFullDto dto) {
        validateId(dto);
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

    private void create(NewsEntry entity, NewsEntryFullDto dto) {
        UserLoginDto userLogin = ((RecordFullDto) dto.getRecord()).getUserLogin();
        entity = dto.update(entity, getUserLogin(userLogin));
        Record record = entity.getRecord();
        recordDao.save(record);
    }

    private void update(NewsEntry entity, NewsEntryFullDto dto) {
        entity = dto.update(entity);
        Record record = entity.getRecord();
        recordDao.save(record);
    }

    private NewsEntry getNewsEntry(UUID id) {
        return newsEntryDao.findById(id).orElseThrow(ErrorCase::notFound);
    }

    private UserLogin getUserLogin(UserLoginDto userLogin) {
        if (userLogin.getLogin() == null) {
            return userLoginDao.findById(userLogin.getId()).orElseThrow(ErrorCase::notFound);
        }
        return userLoginDao.findWhereLogin(userLogin.getLogin()).orElseThrow(ErrorCase::notFound);
    }

    private void validateOrFillRecordNewsEntryId(NewsEntryFullDto dto) {
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
            if ( ! (recordFullDto.getUserLogin() instanceof UserOnlyLoginBaseDto)) {
                throw ErrorCase.bad("user login DTO", String.valueOf(recordFullDto.getUserLogin()));
            }
        }
    }

    private boolean notEquals(UUID id1, UUID id2) {
        return ! id1.equals(id2);
    }
}
//EOF
