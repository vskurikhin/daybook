/*
 * This file was last modified at 2020.04.12 13:16 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * LinkBaseConverter.java
 * $Id$
 */

package su.svn.showcase.converters.impl;

import su.svn.showcase.converters.LinkConverter;
import su.svn.showcase.domain.Link;
import su.svn.showcase.dto.jdo.LinkJdo;
import su.svn.showcase.utils.ReadyMap;

import javax.annotation.Nonnull;
import javax.ejb.Stateless;
import java.util.UUID;

@Stateless(name = "LinkBaseConverter")
public class LinkBaseConverter extends AbstractConverter<UUID, Link, LinkJdo>
       implements LinkConverter {

    @Override
    public LinkJdo convert(@Nonnull Link entity) {
        return super.convertByGetter(new LinkJdo(), entity);
    }

    @Override
    public LinkJdo convert(@Nonnull Link entity, ReadyMap ready) {
        return super.convertByGetter(new LinkJdo(), entity);
    }

    @Override
    public Link convert(@Nonnull LinkJdo dto) {
        return super.convertBySetter(new Link(dto.getId()), dto);
    }

    @Override
    public Link convert(@Nonnull LinkJdo dto, ReadyMap ready) {
        return super.convertBySetter(new Link(dto.getId()), dto);
    }

    @Override
    Class<Link> getEClass() {
        return Link.class;
    }

    @Override
    Class<LinkJdo> getDClass() {
        return LinkJdo.class;
    }
}
