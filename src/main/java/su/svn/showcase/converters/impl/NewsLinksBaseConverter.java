/*
 * This file was last modified at 2020.04.15 00:03 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * NewsLinksBaseConverter.java
 * $Id$
 */

package su.svn.showcase.converters.impl;

import su.svn.showcase.converters.AbstractConverter;
import su.svn.showcase.converters.NewsLinksConverter;
import su.svn.showcase.domain.NewsLinks;
import su.svn.showcase.dto.jdo.NewsLinksJdo;
import su.svn.showcase.utils.ReadyMap;

import javax.annotation.Nonnull;
import javax.ejb.Stateless;
import java.util.UUID;

@Stateless(name = "NewsLinksBaseConverter")
public class NewsLinksBaseConverter extends AbstractConverter<UUID, NewsLinks, NewsLinksJdo>
       implements NewsLinksConverter {

    @Override
    public NewsLinksJdo convert(@Nonnull NewsLinks entity) {
        return super.convertByGetter(new NewsLinksJdo(), entity);
    }

    @Override
    public NewsLinksJdo convert(@Nonnull NewsLinks entity, @Nonnull ReadyMap ready) {
        return super.convertByGetter(new NewsLinksJdo(), entity);
    }

    @Override
    public NewsLinks convert(@Nonnull NewsLinksJdo dto) {
        return super.convertBySetter(new NewsLinks(dto.getId()), dto);
    }

    @Override
    public NewsLinks convert(@Nonnull NewsLinksJdo dto, @Nonnull ReadyMap ready) {
        return super.convertBySetter(new NewsLinks(dto.getId()), dto);
    }

    @Override
    public NewsLinks update(@Nonnull NewsLinks entity, @Nonnull NewsLinksJdo dto) {
        return super.convertBySetter(entity, dto);
    }

    @Override
    public NewsLinks update(@Nonnull NewsLinks entity, @Nonnull NewsLinksJdo dto, @Nonnull ReadyMap ready) {
        return super.convertBySetter(entity, dto);
    }

    @Override
    protected Class<NewsLinks> getEClass() {
        return NewsLinks.class;
    }

    @Override
    protected Class<NewsLinksJdo> getDClass() {
        return NewsLinksJdo.class;
    }
}
//EOF
