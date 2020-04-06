/*
 * This file was last modified at 2020.04.06 22:03 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * NewsEntryAbstractConverter.java
 * $Id$
 */

package su.svn.showcase.converters.impl;

import su.svn.showcase.converters.NewsGroupConverter;
import su.svn.showcase.converters.RecordConverter;
import su.svn.showcase.domain.NewsEntry;
import su.svn.showcase.dto.NewsEntryFullDto;
import su.svn.showcase.dto.NewsGroupFullDto;
import su.svn.showcase.dto.RecordFullDto;
import su.svn.showcase.exceptions.ErrorCase;
import su.svn.showcase.utils.ReadyMap;

import java.util.UUID;

abstract class NewsEntryAbstractConverter extends AbstractConverter<UUID, NewsEntry, NewsEntryFullDto> {

    abstract RecordConverter getRecordConverter();

    abstract NewsGroupConverter getNewsGroupConverter();

    NewsEntryFullDto doConvert(NewsEntryFullDto dto, NewsEntry entity, ReadyMap ready) {
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
            System.err.println("getRecordConverter() = " + getRecordConverter()); // TODO remove
            dto.setRecord(getRecordConverter().convert(entity.getRecord(), ready));
        }
        if (entity.getNewsGroup() != null) {
            dto.setNewsGroup(getNewsGroupConverter().convert(entity.getNewsGroup(), ready));
        }
        return super.convertByGetter(dto, entity);
    }

    NewsEntry doConvert(NewsEntry entity, NewsEntryFullDto dto, ReadyMap ready) {
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
            entity.setRecord(getRecordConverter().convert((RecordFullDto) dto.getRecord(), ready));
        }
        if (dto.getNewsGroup() != null) {
            entity.setNewsGroup(getNewsGroupConverter().convert((NewsGroupFullDto) dto.getNewsGroup(), ready));
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
