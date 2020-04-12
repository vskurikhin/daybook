/*
 * This file was last modified at 2020.04.02 18:19 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * NewsLinksFullConverterImpl.java
 * $Id$
 */

package su.svn.showcase.converters.impl;

import su.svn.showcase.converters.LinkDescriptionConverter;
import su.svn.showcase.converters.NewsGroupConverter;
import su.svn.showcase.converters.NewsLinksConverter;
import su.svn.showcase.converters.RecordConverter;
import su.svn.showcase.domain.LinkDescription;
import su.svn.showcase.domain.NewsLinks;
import su.svn.showcase.dto.*;
import su.svn.showcase.dto.jdo.LinkDescriptionJdo;
import su.svn.showcase.exceptions.ErrorCase;
import su.svn.showcase.utils.ReadyMap;

import javax.annotation.Nonnull;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Stateless(name = "NewsLinksFullConverter")
public class NewsLinksFullConverter extends AbstractConverter<UUID, NewsLinks, NewsLinksFullDto>
       implements NewsLinksConverter {

    @EJB(beanName = "RecordFullConverter")
    private RecordConverter recordConverter;

    @EJB(beanName = "NewsGroupBaseConverter")
    private NewsGroupConverter newsGroupConverter;

    @EJB(beanName = "LinkDescriptionBaseConverter")
    private LinkDescriptionConverter linkDescriptionConverter;

    @Override
    public NewsLinksFullDto convert(@Nonnull NewsLinks entity) {
        return doConvert(new NewsLinksFullDto(entity.getId()), entity, new ReadyMap());
    }

    @Override
    public NewsLinksFullDto convert(@Nonnull NewsLinks entity, ReadyMap ready) {
        return doConvert(new NewsLinksFullDto(entity.getId()), entity, ready);
    }

    private NewsLinksFullDto doConvert(NewsLinksFullDto dto, NewsLinks entity, ReadyMap ready) {
        ReadyMap.Key key = new ReadyMap.UuidKey(dto.getId(), NewsLinksFullDto.class);
        if (ready.containsKey(key)) {
            Object value = ready.get(key);
            if (value instanceof NewsLinksFullDto) {
                return (NewsLinksFullDto) value;
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
        if (entity.getDescriptions() != null) {
            Set<LinkDescriptionDto> set = entity.getDescriptions().stream()
                    .map(functionLinkDescriptionToDto(ready))
                    .collect(Collectors.toSet());
            dto.setDescriptions(set);
        }
        return super.convertByGetter(dto, entity);
    }

    private Function<LinkDescription, LinkDescriptionJdo> functionLinkDescriptionToDto(ReadyMap ready) {
        return entity -> linkDescriptionConverter.convert(entity, ready);
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
        ReadyMap.Key key = new ReadyMap.UuidKey(entity.getId(), NewsLinks.class);
        if (ready.containsKey(key)) {
            Object value = ready.get(key);
            if (value instanceof NewsLinks) {
                return (NewsLinks) value;
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
        if (dto.getDescriptions() != null) {
            Set<LinkDescription> set = dto.getDescriptions().stream()
                    .map(functionLinkDescriptionDtoToEntity(ready))
                    .collect(Collectors.toSet());
            entity.setDescriptions(set);

        }
        return super.convertBySetter(entity, dto);
    }

    private Function<LinkDescriptionDto, LinkDescription> functionLinkDescriptionDtoToEntity(ReadyMap ready) {
        return dto ->  linkDescriptionConverter.convert((LinkDescriptionJdo) dto, ready);
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
