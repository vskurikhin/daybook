/*
 * This file was last modified at 2020.04.15 00:03 by Victor N. Skurikhin.
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
import su.svn.showcase.dto.jdo.ArticleJdo;
import su.svn.showcase.utils.ReadyMap;

import javax.annotation.Nonnull;
import javax.ejb.EJB;
import javax.ejb.Stateless;

@Stateless(name = "ArticlePartConverter")
public class ArticlePartConverter extends ArticleAbstractConverter implements ArticleConverter {

    @EJB(beanName = "RecordBaseConverter")
    private RecordConverter recordConverter;

    @EJB(beanName = "LinkBaseConverter")
    private LinkConverter linkConverter;

    @Override
    public ArticleJdo convert(@Nonnull Article entity) {
        return doConvert(new ArticleJdo(entity.getId()), entity, new ReadyMap());
    }

    @Override
    public ArticleJdo convert(@Nonnull Article entity, @Nonnull ReadyMap ready) {
        return doConvert(new ArticleJdo(entity.getId()), entity, ready);
    }

    @Override
    public Article convert(@Nonnull ArticleJdo dto) {
        return doConvert(new Article(dto.getId()), dto, new ReadyMap());
    }

    @Override
    public Article convert(@Nonnull ArticleJdo dto, @Nonnull ReadyMap ready) {
        return doConvert(new Article(dto.getId()), dto, ready);
    }

    @Override
    public Article update(@Nonnull Article entity, @Nonnull ArticleJdo dto) {
        return doConvert(entity, dto, new ReadyMap());
    }

    @Override
    public Article update(@Nonnull Article entity, @Nonnull ArticleJdo dto, @Nonnull ReadyMap ready) {
        return doConvert(entity, dto, ready);
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
//EOF
