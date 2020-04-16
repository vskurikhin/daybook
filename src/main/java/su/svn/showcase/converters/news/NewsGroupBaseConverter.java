/*
 * This file was last modified at 2020.04.15 22:24 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * NewsGroupBaseConverter.java
 * $Id$
 */

package su.svn.showcase.converters.news;

import su.svn.showcase.converters.AbstractConverter;
import su.svn.showcase.converters.NewsGroupConverter;
import su.svn.showcase.domain.NewsGroup;
import su.svn.showcase.dto.jdo.NewsGroupJdo;
import su.svn.showcase.utils.ReadyMap;

import javax.annotation.Nonnull;
import javax.ejb.Stateless;
import java.util.UUID;

@Stateless(name = "NewsGroupBaseConverter")
public class NewsGroupBaseConverter extends AbstractConverter<UUID, NewsGroup, NewsGroupJdo>
       implements NewsGroupConverter {

    @Override
    public NewsGroupJdo convert(@Nonnull NewsGroup entity) {
        return super.convertByGetter(new NewsGroupJdo(), entity);
    }

    @Override
    public NewsGroupJdo convert(@Nonnull NewsGroup entity, @Nonnull ReadyMap ready) {
        return super.convertByGetter(new NewsGroupJdo(), entity);
    }

    @Override
    public NewsGroup convert(@Nonnull NewsGroupJdo dto) {
        return super.convertBySetter(new NewsGroup(dto.getId()), dto);
    }

    @Override
    public NewsGroup convert(@Nonnull NewsGroupJdo dto, @Nonnull ReadyMap ready) {
        return super.convertBySetter(new NewsGroup(dto.getId()), dto);
    }

    @Override
    public NewsGroup update(@Nonnull NewsGroup entity, @Nonnull NewsGroupJdo dto) {
        return super.convertBySetter(entity, dto);
    }

    @Override
    public NewsGroup update(@Nonnull NewsGroup entity, @Nonnull NewsGroupJdo dto, @Nonnull ReadyMap ready) {
        return super.convertBySetter(entity, dto);
    }

    @Override
    protected Class<NewsGroup> getEClass() {
        return NewsGroup.class;
    }

    @Override
    protected Class<NewsGroupJdo> getDClass() {
        return NewsGroupJdo.class;
    }
}
//EOF
