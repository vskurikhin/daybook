/*
 * This file was last modified at 2020.04.01 12:06 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * NewsLinksFullConverterImpl.java
 * $Id$
 */

package su.svn.showcase.converters.impl;

import su.svn.showcase.converters.LinkDescriptionConverter;
import su.svn.showcase.converters.NewsLinksConverter;
import su.svn.showcase.converters.RecordConverter;
import su.svn.showcase.domain.LinkDescription;
import su.svn.showcase.domain.NewsLinks;
import su.svn.showcase.dto.*;
import su.svn.showcase.utils.ReadyMap;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Named("newsLinksFull")
public class NewsLinksFullConverterImpl extends AbstractConverter<UUID, NewsLinks, NewsLinksFullDto>
       implements NewsLinksConverter {

    @Inject
    @Named("recordFull")
    private RecordConverter recordConverter;

    @Inject
    @Named("linkDescriptionBase")
    private LinkDescriptionConverter linkDescriptionConverter;

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
            dto.setRecord(convertUuid(entity.getRecord(), ready, recordConverter::convert));
        }
        if (entity.getNewsGroup() != null) {
            // TODO
        }
        if (entity.getDescriptions() != null) {
            Set<LinkDescriptionDto> set = entity.getDescriptions().stream()
                    .map(functionLinkDescriptionToDto(ready))
                    .collect(Collectors.toSet());
            dto.setDescriptions(set);
        }
        return super.convertByGetter(dto, entity);
    }

    private Function<LinkDescription, LinkDescriptionFullDto> functionLinkDescriptionToDto(ReadyMap ready) {
        return entity -> convertUuid(entity, ready, linkDescriptionConverter::convert);
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
            entity.setRecord(convertUuid((RecordFullDto) dto.getRecord(), ready, recordConverter::convert));
        }
        if (dto.getNewsGroup() != null) {
            // TODO
        }
        if (dto.getDescriptions() != null) {
            Set<LinkDescription> set = dto.getDescriptions().stream()
                    .map(functionLinkDescriptionDtoToEntity(ready))
                    .collect(Collectors.toSet());
            entity.setDescriptions(set);

        }
        return super.convertBySetter(entity, dto);
    }

    private Function<LinkDescriptionDto, LinkDescription> functionLinkDescriptionDtoToEntity(ReadyMap ready) {
        return dto -> convertUuid((LinkDescriptionFullDto) dto, ready, linkDescriptionConverter::convert);
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
