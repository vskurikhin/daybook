/*
 * This file was last modified at 2020.04.14 16:50 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * NewsEntryBaseConverter.java
 * $Id$
 */

package su.svn.showcase.converters.impl;

import su.svn.showcase.converters.NewsEntryConverter;
import su.svn.showcase.domain.NewsEntry;
import su.svn.showcase.dto.jdo.NewsEntryJdo;
import su.svn.showcase.utils.ReadyMap;

import javax.annotation.Nonnull;
import javax.ejb.Stateless;
import java.util.UUID;

@Stateless(name = "NewsEntryBaseConverter")
public class NewsEntryBaseConverter extends AbstractConverter<UUID, NewsEntry, NewsEntryJdo>
       implements NewsEntryConverter {

    @Override
    public NewsEntryJdo convert(@Nonnull NewsEntry entity) {
        return super.convertByGetter(new NewsEntryJdo(), entity);
    }

    @Override
    public NewsEntryJdo convert(@Nonnull NewsEntry entity, ReadyMap ready) {
        return super.convertByGetter(new NewsEntryJdo(), entity);
    }

    @Override
    public NewsEntry convert(@Nonnull NewsEntryJdo dto) {
        return super.convertBySetter(new NewsEntry(dto.getId()), dto);
    }

    @Override
    public NewsEntry convert(@Nonnull NewsEntryJdo dto, ReadyMap ready) {
        return super.convertBySetter(new NewsEntry(dto.getId()), dto);
    }

    @Override
    Class<NewsEntry> getEClass() {
        return NewsEntry.class;
    }

    @Override
    Class<NewsEntryJdo> getDClass() {
        return NewsEntryJdo.class;
    }
}
