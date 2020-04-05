/*
 * This file was last modified at 2020.04.02 18:19 by Victor N. Skurikhin.
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
import su.svn.showcase.exceptions.ErrorCase;
import su.svn.showcase.utils.ReadyMap;

import javax.annotation.Nonnull;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.UUID;

@Stateless(name = "NewsEntryFullConverter")
public class NewsEntryFullConverter extends AbstractConverter<UUID, NewsEntry, NewsEntryFullDto>
       implements NewsEntryConverter {

    @EJB(beanName = "RecordFullConverter")
    private RecordConverter recordConverter;

    @EJB(beanName = "NewsGroupBaseConverter")
    private NewsGroupConverter newsGroupConverter;

    @Override
    public NewsEntryFullDto convert(@Nonnull NewsEntry entity) {
        return doConvert(new NewsEntryFullDto(entity.getId()), entity, new ReadyMap());
    }

    @Override
    public NewsEntryFullDto convert(@Nonnull NewsEntry entity, ReadyMap ready) {
        return doConvert(new NewsEntryFullDto(entity.getId()), entity, ready);
    }

    private NewsEntryFullDto doConvert(NewsEntryFullDto dto, NewsEntry entity, ReadyMap ready) {
        ReadyMap.Key key = new ReadyMap.UuidKey(dto.getId(), NewsEntryFullDto.class);
        if (ready.containsKey(key)) {
            Object value = ready.get(key);
            if (value instanceof NewsEntryFullDto) {
                return (NewsEntryFullDto) value;
            }
            throw ErrorCase.badType(value.getClass().getSimpleName());
        } else {
            ready.put(dto);
        }
        if (entity.getRecord() != null) {
            dto.setRecord(recordConverter.convert(entity.getRecord(), ready));
        }
        if (entity.getNewsGroup() != null) {
            dto.setNewsGroup(newsGroupConverter.convert(entity.getNewsGroup(), ready));
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
        ReadyMap.Key key = new ReadyMap.UuidKey(entity.getId(), NewsEntry.class);
        if (ready.containsKey(key)) {
            Object value = ready.get(key);
            if (value instanceof NewsEntry) {
                return (NewsEntry) value;
            }
            throw ErrorCase.badType(value.getClass().getSimpleName());
        } else {
            ready.put(entity);
        }
        if (dto.getRecord() != null) {
            entity.setRecord(recordConverter.convert((RecordFullDto) dto.getRecord(), ready));
        }
        if (dto.getNewsGroup() != null) {
            entity.setNewsGroup(newsGroupConverter.convert((NewsGroupFullDto) dto.getNewsGroup(), ready));
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
