/*
 * This file was last modified at 2020.04.01 15:42 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * NewsGroupFullConverterImpl.java
 * $Id$
 */

package su.svn.showcase.converters.impl;

import su.svn.showcase.converters.NewsEntryConverter;
import su.svn.showcase.converters.NewsGroupConverter;
import su.svn.showcase.converters.LinkConverter;
import su.svn.showcase.converters.RecordConverter;
import su.svn.showcase.domain.NewsEntry;
import su.svn.showcase.domain.NewsGroup;
import su.svn.showcase.domain.Tag;
import su.svn.showcase.dto.*;
import su.svn.showcase.utils.ReadyMap;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Named("newsGroupFull")
public class NewsGroupFullConverterImpl extends AbstractConverter<UUID, NewsGroup, NewsGroupFullDto>
       implements NewsGroupConverter {

    @Inject
    @Named("recordFull")
    private RecordConverter recordConverter;

    @Inject
    @Named("newsEntryBase")
    private NewsEntryConverter newsEntryConverter;

    @Inject
    @Named("linkBaseConverter")
    private LinkConverter linkConverter;

    @Override
    public NewsGroupFullDto convert(@Nonnull NewsGroup entity) {
        return doConvert(new NewsGroupFullDto(), entity, new ReadyMap());
    }

    @Override
    public NewsGroupFullDto convert(@Nonnull NewsGroup entity, ReadyMap ready) {
        return doConvert(new NewsGroupFullDto(), entity, ready);
    }

    private NewsGroupFullDto doConvert(NewsGroupFullDto dto, NewsGroup entity, ReadyMap ready) {
        if (entity.getNewsEntries() != null) {
            Set<NewsEntryDto> set = entity.getNewsEntries().stream()
                    .map(functionTagToDto(ready))
                    .collect(Collectors.toSet());
            dto.setNewsEntries(set);
        }
        return super.convertByGetter(dto, entity);
    }

    private Function<NewsEntry, NewsEntryFullDto> functionTagToDto(ReadyMap ready) {
        return entity -> convertUuid(entity, ready, newsEntryConverter::convert);
    }

    @Override
    public NewsGroup convert(@Nonnull NewsGroupFullDto dto) {
        return doConvert(new NewsGroup(dto.getId()), dto, new ReadyMap());
    }

    @Override
    public NewsGroup convert(@Nonnull NewsGroupFullDto dto, ReadyMap ready) {
        return doConvert(new NewsGroup(dto.getId()), dto, ready);
    }

    private NewsGroup doConvert(NewsGroup entity, NewsGroupFullDto dto, ReadyMap ready) {
        if (dto.getNewsEntries() != null) {
            List<NewsEntry> list = dto.getNewsEntries().stream()
                    .map(functionTagDtoToEntity(ready))
                    .collect(Collectors.toList());
            entity.setNewsEntries(list);
        }
        return super.convertBySetter(entity, dto);
    }

    private Function<NewsEntryDto, NewsEntry> functionTagDtoToEntity(ReadyMap ready) {
        return dto -> convertUuid((NewsEntryFullDto) dto, ready, newsEntryConverter::convert);
    }

    @Override
    Class<NewsGroup> getEClass() {
        return NewsGroup.class;
    }

    @Override
    Class<NewsGroupFullDto> getDClass() {
        return NewsGroupFullDto.class;
    }
}
