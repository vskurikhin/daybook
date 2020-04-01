/*
 * This file was last modified at 2020.04.01 17:19 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * NewsEntryBaseConverterImpl.java
 * $Id$
 */

package su.svn.showcase.converters.impl;

import su.svn.showcase.converters.NewsEntryConverter;
import su.svn.showcase.domain.NewsEntry;
import su.svn.showcase.dto.NewsEntryFullDto;
import su.svn.showcase.utils.ReadyMap;

import javax.annotation.Nonnull;
import javax.inject.Named;
import java.util.UUID;

@Named("newsEntryBaseConverter")
public class NewsEntryBaseConverterImpl extends AbstractConverter<UUID, NewsEntry, NewsEntryFullDto>
       implements NewsEntryConverter {

    @Override
    public NewsEntryFullDto convert(@Nonnull NewsEntry entity) {
        return super.convertByGetter(new NewsEntryFullDto(), entity);
    }

    @Override
    public NewsEntryFullDto convert(@Nonnull NewsEntry entity, ReadyMap ready) {
        return super.convertByGetter(new NewsEntryFullDto(), entity);
    }

    @Override
    public NewsEntry convert(@Nonnull NewsEntryFullDto dto) {
        return super.convertBySetter(new NewsEntry(dto.getId()), dto);
    }

    @Override
    public NewsEntry convert(@Nonnull NewsEntryFullDto dto, ReadyMap ready) {
        return super.convertBySetter(new NewsEntry(dto.getId()), dto);
    }

    @Override
    Class<NewsEntry> getEClass() {
        return NewsEntry.class;
    }

    @Override
    Class<NewsEntryFullDto> getDClass() {
        return NewsEntryFullDto.class;
    }
}
