/*
 * This file was last modified at 2020.04.01 22:50 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * UserRoleBaseConverterImpl.java
 * $Id$
 */

package su.svn.showcase.converters.impl;

import su.svn.showcase.converters.ArticleConverter;
import su.svn.showcase.domain.Article;
import su.svn.showcase.dto.ArticleFullDto;
import su.svn.showcase.utils.ReadyMap;

import javax.annotation.Nonnull;
import javax.ejb.Stateless;
import java.util.UUID;

@Stateless(name = "UserRoleBaseConverter")
public class UserRoleBaseConverterImpl extends AbstractConverter<UUID, Article, ArticleFullDto>
       implements ArticleConverter {

    @Override
    public ArticleFullDto convert(@Nonnull Article entity) {
        return super.convertByGetter(new ArticleFullDto(), entity);
    }

    @Override
    public ArticleFullDto convert(@Nonnull Article entity, ReadyMap ready) {
        return super.convertByGetter(new ArticleFullDto(), entity);
    }

    @Override
    public Article convert(@Nonnull ArticleFullDto dto) {
        return super.convertBySetter(new Article(dto.getId()), dto);
    }

    @Override
    public Article convert(@Nonnull ArticleFullDto dto, ReadyMap ready) {
        return super.convertBySetter(new Article(dto.getId()), dto);
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
