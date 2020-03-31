/*
 * This file was last modified at 2020.03.22 22:59 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * TagConverter.java
 * $Id$
 */

package su.svn.showcase.converters.impl;

import su.svn.showcase.converters.RecordConverter;
import su.svn.showcase.converters.TagConverter;
import su.svn.showcase.domain.Record;
import su.svn.showcase.domain.Tag;
import su.svn.showcase.dto.*;
import su.svn.showcase.utils.ReadyMap;

import javax.annotation.Nonnull;
import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Set;
import java.util.stream.Collectors;

@Stateless
public class TagConverterImpl extends AbstractConverter<String, Tag, TagFullDto> implements TagConverter {

    @Inject
    private RecordConverter recordConverter;

    @Override
    public TagFullDto convert(@Nonnull Tag entity) {
        return doConvert(new TagFullDto(), entity, new ReadyMap());
    }

    @Override
    public TagFullDto convert(@Nonnull Tag entity, ReadyMap ready) {
        return doConvert(new TagFullDto(), entity, ready);
    }

    private TagFullDto doConvert(TagFullDto dto, Tag entity, ReadyMap ready) {
        if (entity.getRecords() != null) {
            dto.setRecords(entity.getRecords().stream()
                .map(RecordBaseDto::new)
                .collect(Collectors.toSet()));
        }
        return super.convertBySetter(dto, entity);
    }

    @Override
    public Tag convert(@Nonnull TagFullDto dto) {
        return doConvert(new Tag(dto.getId()), dto, new ReadyMap());
    }

    @Override
    public Tag convert(@Nonnull TagFullDto dto, ReadyMap ready) {
        return doConvert(new Tag(dto.getId()), dto, ready);
    }

    private Tag doConvert(Tag entity, TagFullDto dto, ReadyMap ready) {
        if (dto.getRecords() != null) {
            Set<Record> records = dto.getRecords().stream()
                .map(recordDto -> getOrConvert(recordDto, ready))
                .collect(Collectors.toSet());
            entity.setRecords(records);
        }
        return super.convertBySetter(entity, dto);
    }

    private RecordFullDto getOrConvert(Record record, ReadyMap ready) {
        if (ready.containsKey(record.getId())) {
            return (RecordFullDto) ready.getDto(record.getId());
        }
        return ready.putByUuidKey(recordConverter.convert(record, ready));
    }

    private Record getOrConvert(RecordDto dto, ReadyMap ready) {
        if (ready.containsKey(dto.getId())) {
            return (Record) ready.getEntity(dto.getId());
        }
        return ready.putByUuidKey(recordConverter.convert((RecordFullDto) dto, ready));
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
