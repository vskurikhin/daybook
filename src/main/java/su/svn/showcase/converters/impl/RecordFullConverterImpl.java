/*
 * This file was last modified at 2020.04.01 12:06 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * RecordFullConverterImpl.java
 * $Id$
 */

package su.svn.showcase.converters.impl;

import su.svn.showcase.converters.*;
import su.svn.showcase.domain.Record;
import su.svn.showcase.domain.Tag;
import su.svn.showcase.dto.*;
import su.svn.showcase.utils.ReadyMap;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Named("recordFull")
public class RecordFullConverterImpl extends AbstractConverter<UUID, Record, RecordFullDto>  implements RecordConverter {

    @Inject
    @Named("articleFull")
    private ArticleConverter articleConverter;

    @Inject
    @Named("newsEntryFull")
    private NewsEntryConverter newsEntryConverter;

    @Inject
    @Named("newsLinksFull")
    private NewsLinksConverter newsLinksConverter;

    @Inject
    @Named("tagBase")
    private TagConverter tagConverter;

    @Override
    public RecordFullDto convert(@Nonnull Record entity) {
        return doConvert(new RecordFullDto(), entity, new ReadyMap());
    }

    @Override
    public RecordFullDto convert(@Nonnull Record entity, ReadyMap ready) {
        return doConvert(new RecordFullDto(), entity, ready);
    }

    private RecordFullDto doConvert(RecordFullDto dto, Record entity, ReadyMap ready) {
        if (entity.getNewsEntry() != null) {
            dto.setNewsEntry(convertUuid(entity.getNewsEntry(), ready, newsEntryConverter::convert));
        }
        if (entity.getNewsLinks() != null) {
            dto.setNewsLinks(convertUuid(entity.getNewsLinks(), ready, newsLinksConverter::convert));
        }
        if (entity.getArticle() != null) {
            dto.setArticle(convertUuid(entity.getArticle(), ready, articleConverter::convert));
        }
        if (entity.getTags() != null) {
            Set<TagDto> set = entity.getTags().stream()
                    .map(functionTagToDto(ready))
                    .collect(Collectors.toSet());
            dto.setTags(set);
        }
        return super.convertByGetter(dto, entity);
    }

    private Function<Tag, TagFullDto> functionTagToDto(ReadyMap ready) {
        return entity -> convertString(entity, ready, tagConverter::convert);
    }

    @Override
    public Record convert(@Nonnull RecordFullDto dto) {
        return doConvert(new Record(dto.getId()), dto, new ReadyMap());
    }

    @Override
    public Record convert(@Nonnull RecordFullDto dto, ReadyMap ready) {
        return doConvert(new Record(dto.getId()), dto, ready);
    }

    private Record doConvert(Record entity, RecordFullDto dto, ReadyMap ready) {
        if (dto.getNewsEntry() != null) {
            entity.setNewsEntry(convertUuid((NewsEntryFullDto) dto.getNewsEntry(), ready, newsEntryConverter::convert));
        }
        if (dto.getNewsLinks() != null) {
            entity.setNewsLinks(convertUuid((NewsLinksFullDto) dto.getNewsLinks(), ready, newsLinksConverter::convert));
        }
        if (dto.getArticle() != null) {
            entity.setArticle(convertUuid((ArticleFullDto) dto.getArticle(), ready, articleConverter::convert));
        }
        if (dto.getTags() != null) {
            Set<Tag> set = dto.getTags().stream()
                    .map(functionTagDtoToEntity(ready))
                    .collect(Collectors.toSet());
            entity.setTags(set);
        }
        return super.convertBySetter(entity, dto);
    }

    private Function<TagDto, Tag> functionTagDtoToEntity(ReadyMap ready) {
        return dto -> convertString((TagFullDto) dto, ready, tagConverter::convert);
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
