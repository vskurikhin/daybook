/*
 * This file was last modified at 2020.03.20 19:57 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * ArticleFullCrudServiceImpl.java
 * $Id$
 */

package su.svn.showcase.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import su.svn.showcase.dao.ArticleDao;
import su.svn.showcase.dao.RecordDao;
import su.svn.showcase.dao.UserLoginDao;
import su.svn.showcase.domain.Article;
import su.svn.showcase.domain.Record;
import su.svn.showcase.domain.UserLogin;
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

    @EJB(beanName = "UserLoginDaoEjb")
    private UserLoginDao userLoginDao;

    @Override
    @Transactional
    public void create(@Nonnull ArticleFullDto dto) {
        validateOrFillRecordArticleId(dto);
        create(new Article(getOrGenerateUuidKey(dto)), dto);
    }

    @Override
    @Transactional
    public ArticleFullDto readById(@Nonnull UUID id) {
        return new ArticleFullDto(articleDao.findById(id)
                .orElseThrow(ErrorCase::notFound));
    }

    @Override
    @Transactional
    public List<ArticleFullDto> readRange(int start, int size) {
        return articleDao.range(start, size).stream()
                .map(ArticleFullDto::new)
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

    private void create(Article entity, ArticleFullDto dto) {
        UserLoginDto userLogin = ((RecordFullDto) dto.getRecord()).getUserLogin();
        entity = dto.update(entity, getUserLogin(userLogin));
        Record record = entity.getRecord();
        recordDao.save(record);
    }

    private void update(Article entity, ArticleFullDto dto) {
        RecordFullDto recordFullDto = (RecordFullDto) dto.getRecord();
        entity = dto.update(entity, getUserLogin(recordFullDto.getUserLogin()));
        articleDao.save(entity);
    }

    private Article getArticle(UUID id) {
        return articleDao.findById(id).orElseThrow(ErrorCase::notFound);
    }

    private UserLogin getUserLogin(UserLoginDto userLogin) {
        if (userLogin.getLogin() == null) {
            return userLoginDao.findById(userLogin.getId()).orElseThrow(ErrorCase::notFound);
        }
        return userLoginDao.findWhereLogin(userLogin.getLogin()).orElseThrow(ErrorCase::notFound);
    }

    private void validateOrFillRecordArticleId(ArticleFullDto dto) {
        Objects.requireNonNull(dto.getRecord());
        Objects.requireNonNull(dto.getLink());
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
