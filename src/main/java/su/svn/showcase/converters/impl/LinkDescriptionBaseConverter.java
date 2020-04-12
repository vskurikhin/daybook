/*
 * This file was last modified at 2020.04.01 22:50 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * LinkDescriptionBaseConverterImpl.java
 * $Id$
 */

package su.svn.showcase.converters.impl;

import su.svn.showcase.converters.LinkDescriptionConverter;
import su.svn.showcase.domain.LinkDescription;
import su.svn.showcase.dto.jdo.LinkDescriptionJdo;
import su.svn.showcase.utils.ReadyMap;

import javax.annotation.Nonnull;
import javax.ejb.Stateless;
import java.util.UUID;

@Stateless(name = "LinkDescriptionBaseConverter")
public class LinkDescriptionBaseConverter extends AbstractConverter<UUID, LinkDescription, LinkDescriptionJdo>
       implements LinkDescriptionConverter {

    @Override
    public LinkDescriptionJdo convert(@Nonnull LinkDescription entity) {
        return super.convertByGetter(new LinkDescriptionJdo(), entity);
    }

    @Override
    public LinkDescriptionJdo convert(@Nonnull LinkDescription entity, ReadyMap ready) {
        return super.convertByGetter(new LinkDescriptionJdo(), entity);
    }

    @Override
    public LinkDescription convert(@Nonnull LinkDescriptionJdo dto) {
        return super.convertBySetter(new LinkDescription(dto.getId()), dto);
    }

    @Override
    public LinkDescription convert(@Nonnull LinkDescriptionJdo dto, ReadyMap ready) {
        return super.convertBySetter(new LinkDescription(dto.getId()), dto);
    }

    @Override
    Class<LinkDescription> getEClass() {
        return LinkDescription.class;
    }

    @Override
    Class<LinkDescriptionJdo> getDClass() {
        return LinkDescriptionJdo.class;
    }
}
