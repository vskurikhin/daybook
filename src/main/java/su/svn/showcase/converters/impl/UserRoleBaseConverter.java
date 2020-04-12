/*
 * This file was last modified at 2020.04.12 11:21 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * UserRoleBaseConverter.java
 * $Id$
 */

package su.svn.showcase.converters.impl;

import su.svn.showcase.converters.ArticleConverter;
import su.svn.showcase.domain.Article;
import su.svn.showcase.dto.jdo.ArticleJdo;
import su.svn.showcase.utils.ReadyMap;

import javax.annotation.Nonnull;
import javax.ejb.Stateless;
import java.util.UUID;

@Stateless(name = "UserRoleBaseConverter")
public class UserRoleBaseConverter extends AbstractConverter<UUID, Article, ArticleJdo>
       implements ArticleConverter {

    @Override
    public ArticleJdo convert(@Nonnull Article entity) {
        return super.convertByGetter(new ArticleJdo(), entity);
    }

    @Override
    public ArticleJdo convert(@Nonnull Article entity, ReadyMap ready) {
        return super.convertByGetter(new ArticleJdo(), entity);
    }

    @Override
    public Article convert(@Nonnull ArticleJdo dto) {
        return super.convertBySetter(new Article(dto.getId()), dto);
    }

    @Override
    public Article convert(@Nonnull ArticleJdo dto, ReadyMap ready) {
        return super.convertBySetter(new Article(dto.getId()), dto);
    }

    @Override
    Class<Article> getEClass() {
        return Article.class;
    }

    @Override
    Class<ArticleJdo> getDClass() {
        return ArticleJdo.class;
    }
}
