/*
 * This file was last modified at 2020.04.15 22:24 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * NewsLinksFullConverter.java
 * $Id$
 */

package su.svn.showcase.converters.news;

import su.svn.showcase.converters.AbstractConverter;
import su.svn.showcase.converters.LinkDescriptionConverter;
import su.svn.showcase.converters.NewsGroupConverter;
import su.svn.showcase.converters.NewsLinksConverter;
import su.svn.showcase.converters.RecordConverter;
import su.svn.showcase.domain.LinkDescription;
import su.svn.showcase.domain.NewsLinks;
import su.svn.showcase.dto.LinkDescriptionDto;
import su.svn.showcase.dto.jdo.LinkDescriptionJdo;
import su.svn.showcase.dto.jdo.NewsGroupJdo;
import su.svn.showcase.dto.jdo.NewsLinksJdo;
import su.svn.showcase.dto.jdo.RecordJdo;
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
public class NewsLinksFullConverter extends AbstractConverter<UUID, NewsLinks, NewsLinksJdo>
       implements NewsLinksConverter {

    @EJB(beanName = "RecordFullConverter")
    private RecordConverter recordConverter;

    @EJB(beanName = "NewsGroupBaseConverter")
    private NewsGroupConverter newsGroupConverter;

    @EJB(beanName = "LinkDescriptionBaseConverter")
    private LinkDescriptionConverter linkDescriptionConverter;

    @Override
    public NewsLinksJdo convert(@Nonnull NewsLinks entity) {
        return doConvert(new NewsLinksJdo(entity.getId()), entity, new ReadyMap());
    }

    @Override
    public NewsLinksJdo convert(@Nonnull NewsLinks entity, @Nonnull ReadyMap ready) {
        return doConvert(new NewsLinksJdo(entity.getId()), entity, ready);
    }

    private NewsLinksJdo doConvert(NewsLinksJdo dto, NewsLinks entity, ReadyMap ready) {
        ReadyMap.Key key = new ReadyMap.UuidKey(dto.getId(), NewsLinksJdo.class);
        if (ready.containsKey(key)) {
            Object value = ready.get(key);
            if (value instanceof NewsLinksJdo) {
                return (NewsLinksJdo) value;
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
    public NewsLinks convert(@Nonnull NewsLinksJdo dto) {
        return doConvert(new NewsLinks(dto.getId()), dto, new ReadyMap());
    }

    @Override
    public NewsLinks convert(@Nonnull NewsLinksJdo dto, @Nonnull ReadyMap ready) {
        return doConvert(new NewsLinks(dto.getId()), dto, ready);
    }

    @Override
    public NewsLinks update(@Nonnull NewsLinks entity, @Nonnull NewsLinksJdo dto) {
        return doConvert(entity, dto, new ReadyMap());
    }

    @Override
    public NewsLinks update(@Nonnull NewsLinks entity, @Nonnull NewsLinksJdo dto, @Nonnull ReadyMap ready) {
        return doConvert(entity, dto, ready);
    }

    private NewsLinks doConvert(NewsLinks entity, NewsLinksJdo dto, ReadyMap ready) {
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
            entity.setRecord(recordConverter.convert((RecordJdo) dto.getRecord(), ready));
        }
        if (dto.getNewsGroup() != null) {
            entity.setNewsGroup(newsGroupConverter.convert((NewsGroupJdo) dto.getNewsGroup(), ready));
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
    protected Class<NewsLinks> getEClass() {
        return NewsLinks.class;
    }

    @Override
    protected Class<NewsLinksJdo> getDClass() {
        return NewsLinksJdo.class;
    }
}
//EOF
