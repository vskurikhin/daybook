/*
 * This file was last modified at 2020.04.01 22:50 by Victor N. Skurikhin.
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
import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Stateless(name = "recordFullConverter")
public class RecordFullConverterImpl extends AbstractConverter<UUID, Record, RecordFullDto>  implements RecordConverter {

    @EJB(beanName = "articleFullConverter")
    private ArticleConverter articleConverter;

    @EJB(beanName = "newsEntryFullConverter")
    private NewsEntryConverter newsEntryConverter;

    @EJB(beanName = "newsLinksFullConverter")
    private NewsLinksConverter newsLinksConverter;

    @EJB(beanName = "userOnlyLoginConverter")
    private UserLoginConverter userLoginConverter;

    @EJB(beanName = "tagBaseConverter")
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
            NewsEntryFullDto newsEntryFullDto = convertUuid(entity.getNewsEntry(), ready, newsEntryConverter::convert);
            System.out.println("newsEntryFullDto = " + newsEntryFullDto); // TODO remove
            dto.setNewsEntry(convertUuid(entity.getNewsEntry(), ready, newsEntryConverter::convert));
        }
        if (entity.getNewsLinks() != null) {
            dto.setNewsLinks(convertUuid(entity.getNewsLinks(), ready, newsLinksConverter::convert));
        }
        if (entity.getArticle() != null) {
            dto.setArticle(convertUuid(entity.getArticle(), ready, articleConverter::convert));
        }
        if (entity.getUserLogin() != null) {
            dto.setUserLogin(convertUuid(entity.getUserLogin(), ready, userLoginConverter::convert));
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
        if (dto.getUserLogin() != null) {
            entity.setUserLogin(convertUuid((UserOnlyLoginBaseDto) dto.getUserLogin(), ready, userLoginConverter::convert));
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
