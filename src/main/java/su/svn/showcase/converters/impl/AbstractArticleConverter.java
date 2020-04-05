/*
 * This file was last modified at 2020.04.05 22:40 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * AbstractArticleConverter.java
 * $Id$
 */

package su.svn.showcase.converters.impl;

import su.svn.showcase.converters.LinkConverter;
import su.svn.showcase.converters.RecordConverter;
import su.svn.showcase.domain.Article;
import su.svn.showcase.dto.ArticleFullDto;
import su.svn.showcase.dto.LinkFullDto;
import su.svn.showcase.dto.RecordFullDto;
import su.svn.showcase.exceptions.ErrorCase;
import su.svn.showcase.utils.ReadyMap;

import java.util.UUID;

abstract class AbstractArticleConverter extends AbstractConverter<UUID, Article, ArticleFullDto> {

    abstract RecordConverter getRecordConverter();

    abstract LinkConverter getLinkConverter();

    ArticleFullDto doConvert(ArticleFullDto dto, Article entity, ReadyMap ready) {
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
            dto.setRecord(getRecordConverter().convert(entity.getRecord(), ready));
        }
        if (entity.getLink() != null) {
            dto.setLink(getLinkConverter().convert(entity.getLink(), ready));
        }
        return super.convertByGetter(dto, entity);
    }

    Article doConvert(Article entity, ArticleFullDto dto, ReadyMap ready) {
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
            entity.setRecord(getRecordConverter().convert((RecordFullDto) dto.getRecord(), ready));
        }
        if (dto.getLink() != null) {
            entity.setLink(getLinkConverter().convert((LinkFullDto) dto.getLink(), ready));
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
