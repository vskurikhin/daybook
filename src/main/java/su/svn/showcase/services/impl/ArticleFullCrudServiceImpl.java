/*
 * This file was last modified at 2020.04.05 22:40 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * ArticleFullCrudServiceImpl.java
 * $Id$
 */

package su.svn.showcase.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import su.svn.showcase.converters.ArticleConverter;
import su.svn.showcase.dao.ArticleDao;
import su.svn.showcase.dao.LinkDao;
import su.svn.showcase.dao.RecordDao;
import su.svn.showcase.dao.UserLoginDao;
import su.svn.showcase.domain.*;
import su.svn.showcase.dto.*;
import su.svn.showcase.exceptions.ErrorCase;
import su.svn.showcase.services.ArticleFullCrudService;

import javax.annotation.Nonnull;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Stateless
public class ArticleFullCrudServiceImpl extends AbstractCrudService implements ArticleFullCrudService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ArticleFullCrudServiceImpl.class);

    @EJB(beanName = "ArticleDaoEjb")
    private ArticleDao articleDao;

    @EJB(beanName = "RecordDaoEjb")
    private RecordDao recordDao;

    @EJB(beanName = "LinkDaoEjb")
    private LinkDao linkDao;

    @EJB(beanName = "UserLoginDaoEjb")
    private UserLoginDao userLoginDao;

    @EJB(beanName = "ArticleFullConverter")
    private ArticleConverter articleFullConverter;

    @EJB(beanName = "ArticlePartConverter")
    private ArticleConverter articlePartConverter;

    @Override
    @Transactional
    public void create(@Nonnull ArticleFullDto dto) {
        validateOrFillRecordArticleId(dto);
        create(dto, getUserLogin(dto));
    }

    @Override
    @Transactional
    public ArticleFullDto readById(@Nonnull UUID id) {
        return articlePartConverter.convert(articleDao.findById(id).orElseThrow(ErrorCase::notFound));
    }

    @Override
    @Transactional
    public List<ArticleFullDto> readRange(int start, int size) {
        return articleDao.range(start, size).stream()
                .map(articlePartConverter::convert)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void update(@Nonnull ArticleFullDto dto) {
        validateId(dto);
        // TODO validate login
        update(getArticle(dto.getId()), dto);
    }

    @Override
    @Transactional
    public void delete(@Nonnull UUID id) {
        articleDao.delete(id);
    }

    @Override
    @Transactional
    public int count() {
        return (int) articleDao.count();
    }

    @Override
    Logger getLogger() {
        return LOGGER;
    }

    private void create(ArticleFullDto dto, UserLogin userLogin) {
        Article entity = articleFullConverter.convert(dto);
        Record record = entity.getRecord();
        record.setUserLogin(userLogin);
        articleDao.save(entity);
    }

    private void update(Article entity, ArticleFullDto dto) {
        RecordFullDto recordFullDto = (RecordFullDto) dto.getRecord();
        entity = dto.update(entity, getUserLogin(recordFullDto.getUserLogin()));
        recordDao.save(entity.getRecord());
    }

    private Article getArticle(UUID id) {
        System.err.println("id = " + id);
        return articleDao.findById(id).orElseThrow(ErrorCase::notFound);
    }

    private UserLogin getUserLogin(ArticleFullDto dto) {
        UserLoginDto userLogin = ((RecordFullDto) dto.getRecord()).getUserLogin();
        return getUserLogin(userLogin);
    }

    private UserLogin getUserLogin(UserLoginDto userLogin) {
        if (userLogin.getLogin() == null) {
            return userLoginDao.findById(userLogin.getId()).orElseThrow(ErrorCase::notFound);
        }
        System.out.println("userLogin = " + userLogin); // TODO remove
        return userLoginDao.findWhereLogin(userLogin.getLogin()).orElseThrow(ErrorCase::notFound);
    }

    private void validateOrFillRecordArticleId(ArticleFullDto dto) {
        System.out.println("dto = " + dto);
        Objects.requireNonNull(dto.getRecord());
        validateRecordUserLogin(dto.getRecord());
        if (dto.getId() == null) {
            UUID id = UUID.randomUUID();
            dto.setId(id);
            dto.getRecord().setId(id);
        } else if (notEquals(dto.getId(), dto.getRecord().getId())) {
            throw ErrorCase.doesntEquals("Ids of Record and Article DTO", dto.getRecord().getId(), dto.getId());
        }
        if (dto.getRecord().getType() == null) {
            dto.getRecord().setType(dto.getDtoClass().getSimpleName());
        } else if ( ! ArticleDtoEnum.containsValue(dto.getRecord().getType())) {
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
