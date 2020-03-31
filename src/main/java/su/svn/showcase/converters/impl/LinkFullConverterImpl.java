/*
 * This file was last modified at 2020.03.31 20:28 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * LinkFullConverterImpl.java
 * $Id$
 */

package su.svn.showcase.converters.impl;

import su.svn.showcase.converters.LinkConverter;
import su.svn.showcase.converters.RecordConverter;
import su.svn.showcase.domain.Link;
import su.svn.showcase.dto.LinkFullDto;
import su.svn.showcase.dto.RecordFullDto;
import su.svn.showcase.utils.ReadyMap;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.UUID;

@Named("articleFull")
public class LinkFullConverterImpl extends AbstractConverter<UUID, Link, LinkFullDto>
       implements LinkConverter {

    @Inject
    @Named("recordFull")
    private RecordConverter recordConverter;

    @Override
    public LinkFullDto convert(@Nonnull Link entity) {
        return doConvert(new LinkFullDto(), entity, new ReadyMap());
    }

    @Override
    public LinkFullDto convert(@Nonnull Link entity, ReadyMap ready) {
        return doConvert(new LinkFullDto(), entity, ready);
    }

    private LinkFullDto doConvert(LinkFullDto dto, Link entity, ReadyMap ready) {
        if (entity.getDescriptions() != null) {
            // TODO
        }
        return super.convertByGetter(dto, entity);
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
        if (dto.getDescriptions() != null) {
            // TODO
        }
        return super.convertBySetter(entity, dto);
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
