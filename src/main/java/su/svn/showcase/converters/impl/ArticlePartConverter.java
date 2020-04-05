/*
 * This file was last modified at 2020.04.05 22:40 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * ArticlePartConverter.java
 * $Id$
 */

package su.svn.showcase.converters.impl;

import su.svn.showcase.converters.ArticleConverter;
import su.svn.showcase.converters.LinkConverter;
import su.svn.showcase.converters.RecordConverter;
import su.svn.showcase.domain.Article;
import su.svn.showcase.dto.ArticleFullDto;
import su.svn.showcase.utils.ReadyMap;

import javax.annotation.Nonnull;
import javax.ejb.EJB;
import javax.ejb.Stateless;

@Stateless(name = "ArticleFullConverter")
public class ArticlePartConverter extends AbstractArticleConverter implements ArticleConverter {

    @EJB(beanName = "RecordBaseConverter")
    private RecordConverter recordConverter;

    @EJB(beanName = "LinkBaseConverter")
    private LinkConverter linkConverter;

    @Override
    public ArticleFullDto convert(@Nonnull Article entity) {
        return doConvert(new ArticleFullDto(entity.getId()), entity, new ReadyMap());
    }

    @Override
    public ArticleFullDto convert(@Nonnull Article entity, ReadyMap ready) {
        return doConvert(new ArticleFullDto(entity.getId()), entity, ready);
    }

    @Override
    public Article convert(@Nonnull ArticleFullDto dto) {
        return doConvert(new Article(dto.getId()), dto, new ReadyMap());
    }

    @Override
    public Article convert(@Nonnull ArticleFullDto dto, ReadyMap ready) {
        return doConvert(new Article(dto.getId()), dto, ready);
    }

    @Override
    RecordConverter getRecordConverter() {
        return recordConverter;
    }

    @Override
    LinkConverter getLinkConverter() {
        return linkConverter;
    }
}
