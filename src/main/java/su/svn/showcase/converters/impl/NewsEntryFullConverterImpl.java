/*
 * This file was last modified at 2020.04.01 22:50 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * NewsEntryFullConverterImpl.java
 * $Id$
 */

package su.svn.showcase.converters.impl;

import su.svn.showcase.converters.NewsEntryConverter;
import su.svn.showcase.converters.NewsGroupConverter;
import su.svn.showcase.converters.RecordConverter;
import su.svn.showcase.domain.NewsEntry;
import su.svn.showcase.dto.NewsEntryFullDto;
import su.svn.showcase.dto.NewsGroupFullDto;
import su.svn.showcase.dto.RecordFullDto;
import su.svn.showcase.utils.ReadyMap;

import javax.annotation.Nonnull;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.UUID;

@Stateless(name = "newsEntryFullConverter")
public class NewsEntryFullConverterImpl extends AbstractConverter<UUID, NewsEntry, NewsEntryFullDto>
       implements NewsEntryConverter {

    @EJB(beanName = "recordFullConverter")
    private RecordConverter recordConverter;

    @EJB(beanName = "newsGroupBaseConverter")
    private NewsGroupConverter newsGroupConverter;

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
            dto.setRecord(convertUuid(entity.getRecord(), ready, recordConverter::convert));
        }
        if (entity.getNewsGroup() != null) {
            dto.setNewsGroup(convertUuid(entity.getNewsGroup(), ready, newsGroupConverter::convert));
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
            entity.setRecord(convertUuid((RecordFullDto) dto.getRecord(), ready, recordConverter::convert));
        }
        if (dto.getNewsGroup() != null) {
            entity.setNewsGroup(convertUuid((NewsGroupFullDto) dto.getNewsGroup(), ready, newsGroupConverter::convert));
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
