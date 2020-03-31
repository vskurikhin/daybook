/*
 * This file was last modified at 2020.03.22 22:59 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * TagConverter.java
 * $Id$
 */

package su.svn.showcase.converters.impl;

import su.svn.showcase.converters.TagConverter;
import su.svn.showcase.domain.Tag;
import su.svn.showcase.dto.TagFullDto;
import su.svn.showcase.utils.ReadyMap;

import javax.annotation.Nonnull;
import javax.inject.Named;

@Named("tagBase")
public class TagBaseConverterImpl extends AbstractConverter<String, Tag, TagFullDto> implements TagConverter {

    @Override
    public TagFullDto convert(@Nonnull Tag entity) {
        return super.convertByGetter(new TagFullDto(), entity);
    }

    @Override
    public TagFullDto convert(@Nonnull Tag entity, ReadyMap ready) {
        return super.convertByGetter(new TagFullDto(), entity);
    }

    @Override
    public Tag convert(@Nonnull TagFullDto dto) {
        return super.convertBySetter(new Tag(dto.getId()), dto);
    }

    @Override
    public Tag convert(@Nonnull TagFullDto dto, ReadyMap ready) {
        return super.convertBySetter(new Tag(dto.getId()), dto);
    }

    @Override
    Class<Tag> getEClass() {
        return Tag.class;
    }

    @Override
    Class<TagFullDto> getDClass() {
        return TagFullDto.class;
    }
}
