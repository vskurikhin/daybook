/*
 * This file was last modified at 2020.04.01 12:06 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * LinkFullConverterImpl.java
 * $Id$
 */

package su.svn.showcase.converters.impl;

import su.svn.showcase.converters.ArticleConverter;
import su.svn.showcase.converters.LinkConverter;
import su.svn.showcase.converters.LinkDescriptionConverter;
import su.svn.showcase.domain.Link;
import su.svn.showcase.domain.LinkDescription;
import su.svn.showcase.dto.*;
import su.svn.showcase.utils.ReadyMap;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Named("linkFull")
public class LinkFullConverterImpl extends AbstractConverter<UUID, Link, LinkFullDto>
       implements LinkConverter {

    @Inject
    @Named("articleBase")
    private ArticleConverter articleConverter;

    @Inject
    @Named("linkDescriptionBase")
    private LinkDescriptionConverter linkDescriptionConverter;

    @Override
    public LinkFullDto convert(@Nonnull Link entity) {
        return doConvert(new LinkFullDto(), entity, new ReadyMap());
    }

    @Override
    public LinkFullDto convert(@Nonnull Link entity, ReadyMap ready) {
        return doConvert(new LinkFullDto(), entity, ready);
    }

    private LinkFullDto doConvert(LinkFullDto dto, Link entity, ReadyMap ready) {
        if (entity.getArticle() != null) {
            dto.setArticle(convertUuid(entity.getArticle(), ready, articleConverter::convert));
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
    public Link convert(@Nonnull LinkFullDto dto) {
        return doConvert(new Link(dto.getId()), dto, new ReadyMap());
    }

    @Override
    public Link convert(@Nonnull LinkFullDto dto, ReadyMap ready) {
        return doConvert(new Link(dto.getId()), dto, ready);
    }

    private Link doConvert(Link entity, LinkFullDto dto, ReadyMap ready) {
        if (dto.getArticle() != null) {
            entity.setArticle(convertUuid((ArticleFullDto) dto.getArticle(), ready, articleConverter::convert));
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
    Class<Link> getEClass() {
        return Link.class;
    }

    @Override
    Class<LinkFullDto> getDClass() {
        return LinkFullDto.class;
    }
}
