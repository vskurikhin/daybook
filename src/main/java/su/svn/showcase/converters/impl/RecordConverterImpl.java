/*
 * This file was last modified at 2020.03.31 09:17 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * RecordConverterImpl.java
 * $Id$
 */

package su.svn.showcase.converters.impl;

import su.svn.showcase.converters.EntityConverter;
import su.svn.showcase.converters.RecordConverter;
import su.svn.showcase.converters.TagConverter;
import su.svn.showcase.domain.Record;
import su.svn.showcase.domain.Tag;
import su.svn.showcase.dto.RecordFullDto;
import su.svn.showcase.dto.TagFullDto;
import su.svn.showcase.services.ConverterRegistryService;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class RecordConverterImpl extends AbstractConverter<UUID, Record, RecordFullDto>  implements RecordConverter {


    @Inject
    private ConverterRegistryService converterRegistry;

    @PostConstruct
    private void init() {
        converterRegistry.put(Record.class, RecordFullDto.class, this);
    }

    @Override
    public RecordFullDto convert(@Nonnull Record entity) {
        return doConvert(new RecordFullDto(), entity);
    }

    private RecordFullDto doConvert(RecordFullDto recordFullDto, Record entity) {
        // TODO visited
        if (entity.getNewsEntry() != null) {
            // TODO
        }
        if (entity.getNewsLinks() != null) {
            // TODO
        }
        if (entity.getArticle() != null) {
            // TODO
        }
        if (entity.getTags() != null) {
            EntityConverter<String, Tag, TagFullDto> tagConverter = Objects.requireNonNull(getTagConverter());
            Set<TagFullDto> set = entity.getTags().stream()
                    .map(tagConverter::convert)
                    .collect(Collectors.toSet());
        }
        return convert(recordFullDto, entity);
    }

    @Nullable
    private EntityConverter<String, Tag, TagFullDto> getTagConverter() {
        Object o =  converterRegistry.get(Tag.class, TagFullDto.class);
        if (o instanceof TagConverter) {
            return (TagConverter) o;
        } else if (o != null) {
            //noinspection unchecked
            return (EntityConverter<String, Tag, TagFullDto>) o;
        }
        return null;
    }

    @Override
    public Record convert(@Nonnull RecordFullDto dto) {
        // TODO visited
        return doConvert(new Record(dto.getId()), dto);
    }

    private Record doConvert(Record record, RecordFullDto dto) {
        return convert(record, dto);
    }

    @Override
    Class<Record> getEClass() {
        return Record.class;
    }

    @Override
    Class<RecordFullDto> getDClass() {
        return RecordFullDto.class;
    }
}
