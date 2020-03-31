/*
 * This file was last modified at 2020.03.31 09:17 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * RecordConverterImpl.java
 * $Id$
 */

package su.svn.showcase.converters.impl;

import su.svn.showcase.converters.RecordConverter;
import su.svn.showcase.converters.TagConverter;
import su.svn.showcase.domain.Record;
import su.svn.showcase.domain.Tag;
import su.svn.showcase.dto.RecordFullDto;
import su.svn.showcase.dto.TagDto;
import su.svn.showcase.dto.TagFullDto;
import su.svn.showcase.utils.ReadyMap;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class ArticleConverterImpl extends AbstractConverter<UUID, Record, RecordFullDto>  implements RecordConverter {

    @Inject
    private TagConverter tagConverter;

    @Override
    public RecordFullDto convert(@Nonnull Record entity) {
        return doConvert(new RecordFullDto(), entity, new ReadyMap());
    }

    @Override
    public RecordFullDto convert(@Nonnull Record entity, ReadyMap ready) {
        return doConvert(new RecordFullDto(), entity, ready);
    }

    private RecordFullDto doConvert(RecordFullDto recordFullDto, Record entity, ReadyMap ready) {
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
            Set<TagDto> set = entity.getTags().stream()
                    .map(tag -> getOrConvert(tag, ready))
                    .collect(Collectors.toSet());
            recordFullDto.setTags(set);
        }
        return super.convertBySetter(recordFullDto, entity);
    }

    @Override
    public Record convert(@Nonnull RecordFullDto dto) {
        return doConvert(new Record(dto.getId()), dto, new ReadyMap());
    }

    @Override
    public Record convert(@Nonnull RecordFullDto dto, ReadyMap ready) {
        return null;
    }

    private Record doConvert(Record record, RecordFullDto dto, ReadyMap ready) {
        if (dto.getNewsEntry() != null) {
            // TODO
        }
        if (dto.getNewsLinks() != null) {
            // TODO
        }
        if (dto.getArticle() != null) {
            // TODO
        }
        if (dto.getTags() != null) {
            Set<Tag> set = dto.getTags().stream()
                    .map(tagDto -> getOrConvert(tagDto, ready))
                    .collect(Collectors.toSet());
            record.setTags(set);
        }
        return super.convertBySetter(record, dto);
    }

    private TagFullDto getOrConvert(Tag tag, ReadyMap ready) {
        if (ready.containsKey(tag.getId())) {
            return (TagFullDto) ready.getDto(tag.getId());
        }
        return ready.putByStringKey(tagConverter.convert(tag, ready));
    }

    private Tag getOrConvert(TagDto dto, ReadyMap ready) {
        if (ready.containsKey(dto.getId())) {
            return (Tag) ready.getEntity(dto.getId());
        }
        return ready.putByStringKey(tagConverter.convert((TagFullDto) dto, ready));
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
