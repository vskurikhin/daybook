/*
 * This file was last modified at 2020.03.31 20:05 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * NewsEntryFullConverterImpl.java
 * $Id$
 */

package su.svn.showcase.converters.impl;

import su.svn.showcase.converters.NewsEntryConverter;
import su.svn.showcase.converters.RecordConverter;
import su.svn.showcase.domain.NewsEntry;
import su.svn.showcase.dto.NewsEntryFullDto;
import su.svn.showcase.dto.RecordFullDto;
import su.svn.showcase.utils.ReadyMap;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.UUID;

@Named("newsEntryFull")
public class NewsEntryFullConverterImpl extends AbstractConverter<UUID, NewsEntry, NewsEntryFullDto>
       implements NewsEntryConverter {

    @Inject
    @Named("recordFull")
    private RecordConverter recordConverter;

    @Override
    public NewsEntryFullDto convert(@Nonnull NewsEntry entity) {
        return doConvert(new NewsEntryFullDto(), entity, new ReadyMap());
    }

    @Override
    public NewsEntryFullDto convert(@Nonnull NewsEntry entity, ReadyMap ready) {
        return doConvert(new NewsEntryFullDto(), entity, ready);
    }

    private NewsEntryFullDto doConvert(NewsEntryFullDto dto, NewsEntry entity, ReadyMap ready) {
        if (entity.getRecord() != null) {
            dto.setRecord(getOrConvertUuidDto(entity.getRecord(), ready, recordConverter::convert));
        }
        if (entity.getNewsGroup() != null) {
            // TODO
        }
        return super.convertByGetter(dto, entity);
    }

    @Override
    public NewsEntry convert(@Nonnull NewsEntryFullDto dto) {
        return doConvert(new NewsEntry(dto.getId()), dto, new ReadyMap());
    }

    @Override
    public NewsEntry convert(@Nonnull NewsEntryFullDto dto, ReadyMap ready) {
        return doConvert(new NewsEntry(dto.getId()), dto, ready);
    }

    private NewsEntry doConvert(NewsEntry entity, NewsEntryFullDto dto, ReadyMap ready) {
        if (dto.getRecord() != null) {
            entity.setRecord(getOrConvertUuidEntity((RecordFullDto) dto.getRecord(), ready, recordConverter::convert));
        }
        if (dto.getNewsGroup() != null) {
            // TODO
        }
        return super.convertBySetter(entity, dto);
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
