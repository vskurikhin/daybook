/*
 * This file was last modified at 2020.03.31 20:41 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * LinkDescriptionBaseConverterImpl.java
 * $Id$
 */

package su.svn.showcase.converters.impl;

import su.svn.showcase.converters.LinkDescriptionConverter;
import su.svn.showcase.domain.LinkDescription;
import su.svn.showcase.dto.LinkDescriptionFullDto;
import su.svn.showcase.utils.ReadyMap;

import javax.annotation.Nonnull;
import javax.inject.Named;
import java.util.UUID;

@Named("linkDescriptionBase")
public class LinkDescriptionBaseConverterImpl extends AbstractConverter<UUID, LinkDescription, LinkDescriptionFullDto>
       implements LinkDescriptionConverter {

    @Override
    public LinkDescriptionFullDto convert(@Nonnull LinkDescription entity) {
        return super.convertByGetter(new LinkDescriptionFullDto(), entity);
    }

    @Override
    public LinkDescriptionFullDto convert(@Nonnull LinkDescription entity, ReadyMap ready) {
        return super.convertByGetter(new LinkDescriptionFullDto(), entity);
    }

    @Override
    public LinkDescription convert(@Nonnull LinkDescriptionFullDto dto) {
        return super.convertBySetter(new LinkDescription(dto.getId()), dto);
    }

    @Override
    public LinkDescription convert(@Nonnull LinkDescriptionFullDto dto, ReadyMap ready) {
        return super.convertBySetter(new LinkDescription(dto.getId()), dto);
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
