/*
 * This file was last modified at 2020.03.31 20:21 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * NewsLinksFullConverterImpl.java
 * $Id$
 */

package su.svn.showcase.converters.impl;

import su.svn.showcase.converters.NewsLinksConverter;
import su.svn.showcase.converters.RecordConverter;
import su.svn.showcase.domain.NewsLinks;
import su.svn.showcase.dto.NewsLinksFullDto;
import su.svn.showcase.dto.RecordFullDto;
import su.svn.showcase.utils.ReadyMap;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.UUID;

@Named("newsLinksFull")
public class NewsLinksFullConverterImpl extends AbstractConverter<UUID, NewsLinks, NewsLinksFullDto>
       implements NewsLinksConverter {

    @Inject
    @Named("recordFull")
    private RecordConverter recordConverter;

    @Override
    public NewsLinksFullDto convert(@Nonnull NewsLinks entity) {
        return doConvert(new NewsLinksFullDto(), entity, new ReadyMap());
    }

    @Override
    public NewsLinksFullDto convert(@Nonnull NewsLinks entity, ReadyMap ready) {
        return doConvert(new NewsLinksFullDto(), entity, ready);
    }

    private NewsLinksFullDto doConvert(NewsLinksFullDto dto, NewsLinks entity, ReadyMap ready) {
        if (entity.getRecord() != null) {
            dto.setRecord(getOrConvertUuidDto(entity.getRecord(), ready, recordConverter::convert));
        }
        if (entity.getNewsGroup() != null) {
            // TODO
        }
        if (entity.getLinks() != null) {
            // TODO
        }
        return super.convertByGetter(dto, entity);
    }

    @Override
    public NewsLinks convert(@Nonnull NewsLinksFullDto dto) {
        return doConvert(new NewsLinks(dto.getId()), dto, new ReadyMap());
    }

    @Override
    public NewsLinks convert(@Nonnull NewsLinksFullDto dto, ReadyMap ready) {
        return doConvert(new NewsLinks(dto.getId()), dto, ready);
    }

    private NewsLinks doConvert(NewsLinks entity, NewsLinksFullDto dto, ReadyMap ready) {
        if (dto.getRecord() != null) {
            entity.setRecord(getOrConvertUuidEntity((RecordFullDto) dto.getRecord(), ready, recordConverter::convert));
        }
        if (dto.getNewsGroup() != null) {
            // TODO
        }
        if (dto.getLinks() != null) {
            // TODO
        }
        return super.convertBySetter(entity, dto);
    }

    @Override
    Class<NewsLinks> getEClass() {
        return NewsLinks.class;
    }

    @Override
    Class<NewsLinksFullDto> getDClass() {
        return NewsLinksFullDto.class;
    }
}
