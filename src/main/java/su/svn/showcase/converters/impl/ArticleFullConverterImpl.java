/*
 * This file was last modified at 2020.04.01 22:50 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * ArticleFullConverterImpl.java
 * $Id$
 */

package su.svn.showcase.converters.impl;

import su.svn.showcase.converters.ArticleConverter;
import su.svn.showcase.converters.LinkConverter;
import su.svn.showcase.converters.RecordConverter;
import su.svn.showcase.domain.Article;
import su.svn.showcase.dto.*;
import su.svn.showcase.utils.ReadyMap;

import javax.annotation.Nonnull;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.UUID;

@Stateless(name = "articleFullConverter")
public class ArticleFullConverterImpl extends AbstractConverter<UUID, Article, ArticleFullDto>
       implements ArticleConverter {

    @EJB(beanName = "recordFullConverter")
    private RecordConverter recordConverter;

    @EJB(beanName = "linkBaseConverter")
    private LinkConverter linkConverter;

    @Override
    public ArticleFullDto convert(@Nonnull Article entity) {
        return doConvert(new ArticleFullDto(), entity, new ReadyMap());
    }

    @Override
    public ArticleFullDto convert(@Nonnull Article entity, ReadyMap ready) {
        return doConvert(new ArticleFullDto(), entity, ready);
    }

    private ArticleFullDto doConvert(ArticleFullDto dto, Article entity, ReadyMap ready) {
        if (entity.getRecord() != null) {
            dto.setRecord(convertUuid(entity.getRecord(), ready, recordConverter::convert));
        }
        if (entity.getLink() != null) {
            dto.setLink(convertUuid(entity.getLink(), ready, linkConverter::convert));
        }
        return super.convertByGetter(dto, entity);
    }

    @Override
    public Article convert(@Nonnull ArticleFullDto dto) {
        return doConvert(new Article(dto.getId()), dto, new ReadyMap());
    }

    @Override
    public Article convert(@Nonnull ArticleFullDto dto, ReadyMap ready) {
        return doConvert(new Article(dto.getId()), dto, ready);
    }

    private Article doConvert(Article entity, ArticleFullDto dto, ReadyMap ready) {
        if (dto.getRecord() != null) {
            entity.setRecord(convertUuid((RecordFullDto) dto.getRecord(), ready, recordConverter::convert));
        }
        if (dto.getLink() != null) {
            entity.setLink(convertUuid((LinkFullDto) dto.getLink(), ready, linkConverter::convert));
        }
        return super.convertBySetter(entity, dto);
    }

    @Override
    Class<Article> getEClass() {
        return Article.class;
    }

    @Override
    Class<ArticleFullDto> getDClass() {
        return ArticleFullDto.class;
    }
}
