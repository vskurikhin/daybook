/*
 * This file was last modified at 2020.04.14 21:45 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * NewsEntryAbstractConverter.java
 * $Id$
 */

package su.svn.showcase.converters.impl;

import su.svn.showcase.converters.NewsGroupConverter;
import su.svn.showcase.converters.RecordConverter;
import su.svn.showcase.domain.NewsEntry;
import su.svn.showcase.dto.jdo.NewsEntryJdo;
import su.svn.showcase.dto.jdo.NewsGroupJdo;
import su.svn.showcase.dto.jdo.RecordJdo;
import su.svn.showcase.exceptions.ErrorCase;
import su.svn.showcase.utils.ReadyMap;

import java.util.UUID;

abstract class NewsEntryAbstractConverter extends AbstractConverter<UUID, NewsEntry, NewsEntryJdo> {

    abstract RecordConverter getRecordConverter();

    abstract NewsGroupConverter getNewsGroupConverter();

    NewsEntryJdo doConvert(NewsEntryJdo dto, NewsEntry entity, ReadyMap ready) {
        ReadyMap.Key key = new ReadyMap.UuidKey(dto.getId(), NewsEntryJdo.class);
        if (ready.containsKey(key)) {
            Object value = ready.get(key);
            if (value instanceof NewsEntryJdo) {
                return (NewsEntryJdo) value;
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

    NewsEntry doConvert(NewsEntry entity, NewsEntryJdo dto, ReadyMap ready) {
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
            entity.setRecord(getRecordConverter().convert((RecordJdo) dto.getRecord(), ready));
        }
        if (dto.getNewsGroup() != null) {
            entity.setNewsGroup(getNewsGroupConverter().convert((NewsGroupJdo) dto.getNewsGroup(), ready));
        }
        return super.convertBySetter(entity, dto);
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
