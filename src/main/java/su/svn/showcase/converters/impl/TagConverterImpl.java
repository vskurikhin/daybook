/*
 * This file was last modified at 2020.03.22 22:59 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * TagConverter.java
 * $Id$
 */

package su.svn.showcase.converters.impl;

import su.svn.showcase.converters.EntityConverter;
import su.svn.showcase.converters.TagConverter;
import su.svn.showcase.domain.Record;
import su.svn.showcase.domain.Tag;
import su.svn.showcase.dto.RecordBaseDto;
import su.svn.showcase.dto.TagFullDto;
import su.svn.showcase.services.ConverterRegistryService;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Set;
import java.util.stream.Collectors;

@Stateless
public class TagConverterImpl extends AbstractConverter<String, Tag, TagFullDto> implements TagConverter {

    @Inject
    private ConverterRegistryService converterRegistry;

    @PostConstruct
    private void init() {
        converterRegistry.put(Tag.class, TagFullDto.class, this);
    }

    @Override
    public TagFullDto convert(@Nonnull Tag entity) {
        return doConvert(new TagFullDto(), entity);
    }

    private TagFullDto doConvert(TagFullDto dto, Tag entity) {
        // TODO visited
        if (entity.getRecords() != null) {
            dto.setRecords(entity.getRecords().stream()
                .map(RecordBaseDto::new) // TODO invoke converter
                .collect(Collectors.toSet()));
        }
        return convert(dto, entity);
    }

    @Override
    public Tag convert(@Nonnull TagFullDto dto) {
        return doConvert(new Tag(dto.getId()), dto);
    }

    private Tag doConvert(Tag entity, TagFullDto dto) {
        // TODO visited
        if (dto.getRecords() != null) {
            Set<Record> records = dto.getRecords().stream()
                .map(dto1 -> dto1.update(new Record(dto1.getId()))) // TODO invoke converter
                .collect(Collectors.toSet());
            entity.setRecords(records);
        }
        return convert(entity, dto);
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
