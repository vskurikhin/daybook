/*
 * This file was last modified at 2020.03.31 20:41 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * LinkDescriptionFullConverterImpl.java
 * $Id$
 */

package su.svn.showcase.converters.impl;

import su.svn.showcase.converters.LinkConverter;
import su.svn.showcase.converters.LinkDescriptionConverter;
import su.svn.showcase.converters.NewsLinksConverter;
import su.svn.showcase.domain.LinkDescription;
import su.svn.showcase.dto.LinkDescriptionFullDto;
import su.svn.showcase.dto.LinkFullDto;
import su.svn.showcase.dto.NewsLinksFullDto;
import su.svn.showcase.utils.ReadyMap;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.UUID;

@Named("linkDescriptionFull")
public class LinkDescriptionFullConverterImpl extends AbstractConverter<UUID, LinkDescription, LinkDescriptionFullDto>
       implements LinkDescriptionConverter {

    @Inject
    @Named("newsLinks")
    private NewsLinksConverter newsLinksConverter;

    @Inject
    @Named("linkBase")
    private LinkConverter linkConverter;

    @Override
    public LinkDescriptionFullDto convert(@Nonnull LinkDescription entity) {
        return doConvert(new LinkDescriptionFullDto(), entity, new ReadyMap());
    }

    @Override
    public LinkDescriptionFullDto convert(@Nonnull LinkDescription entity, ReadyMap ready) {
        return doConvert(new LinkDescriptionFullDto(), entity, ready);
    }

    private LinkDescriptionFullDto doConvert(LinkDescriptionFullDto dto, LinkDescription entity, ReadyMap ready) {
        if (entity.getNewsLinks() != null) {
            dto.setNewsLinks(getOrConvertUuidDto(entity.getNewsLinks(), ready, newsLinksConverter::convert));
        }
        if (entity.getLink() != null) {
            dto.setLink(getOrConvertUuidDto(entity.getLink(), ready, linkConverter::convert));
        }
        return super.convertByGetter(dto, entity);
    }

    @Override
    public LinkDescription convert(@Nonnull LinkDescriptionFullDto dto) {
        return doConvert(new LinkDescription(dto.getId()), dto, new ReadyMap());
    }

    @Override
    public LinkDescription convert(@Nonnull LinkDescriptionFullDto dto, ReadyMap ready) {
        return doConvert(new LinkDescription(dto.getId()), dto, ready);
    }

    private LinkDescription doConvert(LinkDescription entity, LinkDescriptionFullDto dto, ReadyMap ready) {
        if (dto.getNewsLinks() != null) {
            entity.setNewsLinks(getOrConvertUuidEntity((NewsLinksFullDto) dto.getNewsLinks(), ready, newsLinksConverter::convert));
        }
        if (dto.getLink() != null) {
            entity.setLink(getOrConvertUuidEntity((LinkFullDto) dto.getLink(), ready, linkConverter::convert));
        }
        return super.convertBySetter(entity, dto);
    }

    @Override
    Class<LinkDescription> getEClass() {
        return LinkDescription.class;
    }

    @Override
    Class<LinkDescriptionFullDto> getDClass() {
        return LinkDescriptionFullDto.class;
    }
}
