/*
 * This file was last modified at 2020.04.15 00:03 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * TagBaseConverter.java
 * $Id$
 */

package su.svn.showcase.converters.impl;

import su.svn.showcase.converters.AbstractConverter;
import su.svn.showcase.converters.TagConverter;
import su.svn.showcase.domain.Tag;
import su.svn.showcase.dto.jdo.TagJdo;
import su.svn.showcase.utils.ReadyMap;

import javax.annotation.Nonnull;
import javax.ejb.Stateless;

@Stateless(name = "TagBaseConverter")
public class TagBaseConverter extends AbstractConverter<String, Tag, TagJdo> implements TagConverter {

    @Override
    public TagJdo convert(@Nonnull Tag entity) {
        return super.convertByGetter(new TagJdo(), entity);
    }

    @Override
    public TagJdo convert(@Nonnull Tag entity, @Nonnull ReadyMap ready) {
        return super.convertByGetter(new TagJdo(), entity);
    }

    @Override
    public Tag convert(@Nonnull TagJdo dto) {
        return super.convertBySetter(new Tag(dto.getId()), dto);
    }

    @Override
    public Tag convert(@Nonnull TagJdo dto, @Nonnull ReadyMap ready) {
        return super.convertBySetter(new Tag(dto.getId()), dto);
    }

    @Override
    public Tag update(@Nonnull Tag entity, @Nonnull TagJdo dto) {
        return super.convertBySetter(entity, dto);
    }

    @Override
    public Tag update(@Nonnull Tag entity, @Nonnull TagJdo dto, @Nonnull ReadyMap ready) {
        return super.convertBySetter(entity, dto);
    }

    @Override
    protected Class<Tag> getEClass() {
        return Tag.class;
    }

    @Override
    protected Class<TagJdo> getDClass() {
        return TagJdo.class;
    }
}
//EOF
