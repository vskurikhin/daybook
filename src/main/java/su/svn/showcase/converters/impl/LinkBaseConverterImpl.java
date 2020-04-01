/*
 * This file was last modified at 2020.04.01 15:42 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * LinkBaseConverterImpl.java
 * $Id$
 */

package su.svn.showcase.converters.impl;

import su.svn.showcase.converters.LinkConverter;
import su.svn.showcase.domain.Link;
import su.svn.showcase.dto.LinkFullDto;
import su.svn.showcase.utils.ReadyMap;

import javax.annotation.Nonnull;
import javax.inject.Named;
import java.util.UUID;

@Named("linkBaseConverter")
public class LinkBaseConverterImpl extends AbstractConverter<UUID, Link, LinkFullDto>
       implements LinkConverter {

    @Override
    public LinkFullDto convert(@Nonnull Link entity) {
        return super.convertByGetter(new LinkFullDto(), entity);
    }

    @Override
    public LinkFullDto convert(@Nonnull Link entity, ReadyMap ready) {
        return super.convertByGetter(new LinkFullDto(), entity);
    }

    @Override
    public Link convert(@Nonnull LinkFullDto dto) {
        return super.convertBySetter(new Link(dto.getId()), dto);
    }

    @Override
    public Link convert(@Nonnull LinkFullDto dto, ReadyMap ready) {
        return super.convertBySetter(new Link(dto.getId()), dto);
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
