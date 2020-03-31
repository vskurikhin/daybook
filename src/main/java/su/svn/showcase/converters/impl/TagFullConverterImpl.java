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
import javax.inject.Inject;
import javax.inject.Named;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Named("tagFull")
public class TagFullConverterImpl extends AbstractConverter<String, Tag, TagFullDto> implements TagConverter {

    @Inject
    private @Named("recordBase") RecordConverter recordConverter;

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
                .map(functionRecordToDto(ready))
                .collect(Collectors.toSet()));
        }
        return super.convertByGetter(dto, entity);
    }

    private Function<Record, RecordFullDto> functionRecordToDto(ReadyMap ready) {
        return record -> getOrConvertUuidDto(record, ready, recordConverter::convert);
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
                .map(functionRecordDtoToEntity(ready))
                .collect(Collectors.toSet());
            entity.setRecords(records);
        }
        return super.convertBySetter(entity, dto);
    }

    private Function<RecordDto, Record> functionRecordDtoToEntity(ReadyMap ready) {
        return recordDto -> getOrConvertUuidEntity((RecordFullDto) recordDto, ready, recordConverter::convert);
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
