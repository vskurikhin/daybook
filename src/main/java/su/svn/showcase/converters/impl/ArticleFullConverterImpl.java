/*
 * This file was last modified at 2020.04.02 18:19 by Victor N. Skurikhin.
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
import su.svn.showcase.dto.ArticleFullDto;
import su.svn.showcase.dto.LinkFullDto;
import su.svn.showcase.dto.RecordFullDto;
import su.svn.showcase.exceptions.ErrorCase;
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
        return doConvert(new ArticleFullDto(entity.getId()), entity, new ReadyMap());
    }

    @Override
    public ArticleFullDto convert(@Nonnull Article entity, ReadyMap ready) {
        return doConvert(new ArticleFullDto(entity.getId()), entity, ready);
    }

    private ArticleFullDto doConvert(ArticleFullDto dto, Article entity, ReadyMap ready) {
        ReadyMap.Key key = new ReadyMap.UuidKey(dto.getId(), ArticleFullDto.class);
        if (ready.containsKey(key)) {
            Object value = ready.get(key);
            if (value instanceof ArticleFullDto) {
                return (ArticleFullDto) value;
            }
            throw ErrorCase.badType(value.getClass().getSimpleName());
        } else {
            ready.put(dto);
        }
        if (entity.getRecord() != null) {
            dto.setRecord(recordConverter.convert(entity.getRecord(), ready));
        }
        if (entity.getLink() != null) {
            dto.setLink(linkConverter.convert(entity.getLink(), ready));
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
        ReadyMap.Key key = new ReadyMap.UuidKey(entity.getId(), Article.class);
        if (ready.containsKey(key)) {
            Object value = ready.get(key);
            if (value instanceof Article) {
                return (Article) value;
            }
            throw ErrorCase.badType(value.getClass().getSimpleName());
        } else {
            ready.put(entity);
        }
        if (dto.getRecord() != null) {
            entity.setRecord(recordConverter.convert((RecordFullDto) dto.getRecord(), ready));
        }
        if (dto.getLink() != null) {
            entity.setLink(linkConverter.convert((LinkFullDto) dto.getLink(), ready));
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
