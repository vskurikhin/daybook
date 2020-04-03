/*
 * This file was last modified at 2020.04.02 18:19 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * RecordFullConverterImpl.java
 * $Id$
 */

package su.svn.showcase.converters.impl;

import su.svn.showcase.converters.ArticleConverter;
import su.svn.showcase.converters.NewsEntryConverter;
import su.svn.showcase.converters.NewsLinksConverter;
import su.svn.showcase.converters.RecordConverter;
import su.svn.showcase.converters.TagConverter;
import su.svn.showcase.converters.UserLoginConverter;
import su.svn.showcase.domain.Record;
import su.svn.showcase.domain.Tag;
import su.svn.showcase.dto.*;
import su.svn.showcase.exceptions.ErrorCase;
import su.svn.showcase.utils.ReadyMap;

import javax.annotation.Nonnull;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@SuppressWarnings("DuplicatedCode")
@Stateless(name = "RecordFullConverter")
public class RecordFullConverterImpl extends AbstractConverter<UUID, Record, RecordFullDto>  implements RecordConverter {

    @EJB(beanName = "ArticleFullConverter")
    private ArticleConverter articleConverter;

    @EJB(beanName = "NewsEntryFullConverter")
    private NewsEntryConverter newsEntryConverter;

    @EJB(beanName = "NewsLinksFullConverter")
    private NewsLinksConverter newsLinksConverter;

    @EJB(beanName = "UserOnlyLoginConverter")
    private UserLoginConverter userLoginConverter;

    @EJB(beanName = "TagBaseConverter")
    private TagConverter tagConverter;

    @Override
    public RecordFullDto convert(@Nonnull Record entity) {
        return doConvert(new RecordFullDto(entity.getId()), entity, new ReadyMap());
    }

    @Override
    public RecordFullDto convert(@Nonnull Record entity, ReadyMap ready) {
        return doConvert(new RecordFullDto(entity.getId()), entity, ready);
    }

    private RecordFullDto doConvert(RecordFullDto dto, Record entity, ReadyMap ready) {
        ReadyMap.Key key = new ReadyMap.UuidKey(dto.getId(), RecordFullDto.class);
        if (ready.containsKey(key)) {
            Object value = ready.get(key);
            if (value instanceof RecordFullDto) {
                return (RecordFullDto) value;
            }
            throw ErrorCase.badType(value.getClass().getSimpleName());
        } else {
            ready.put(dto);
        }
        if (entity.getArticle() != null) {
            dto.setArticle(articleConverter.convert(entity.getArticle(), ready));
        }
        if (entity.getNewsEntry() != null) {
            dto.setNewsEntry(newsEntryConverter.convert(entity.getNewsEntry(), ready));
        }
        if (entity.getNewsLinks() != null) {
            dto.setNewsLinks(newsLinksConverter.convert(entity.getNewsLinks(), ready));
        }
        if (entity.getUserLogin() != null) { // TODO only check
            dto.setUserLogin(userLoginConverter.convert(entity.getUserLogin(), ready));
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
        return entity -> tagConverter.convert(entity, ready);
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
        ReadyMap.Key key = new ReadyMap.UuidKey(entity.getId(), Record.class);
        if (ready.containsKey(key)) {
            Object value = ready.get(key);
            if (value instanceof Record) {
                return (Record) value;
            }
            throw ErrorCase.badType(value.getClass().getSimpleName());
        } else {
            ready.put(entity);
        }
        if (dto.getArticle() != null) {
            entity.setArticle(articleConverter.convert((ArticleFullDto) dto.getArticle(), ready));
        }
        if (dto.getNewsEntry() != null) {
            entity.setNewsEntry(newsEntryConverter.convert((NewsEntryFullDto) dto.getNewsEntry(), ready));
        }
        if (dto.getNewsLinks() != null) {
            entity.setNewsLinks(newsLinksConverter.convert((NewsLinksFullDto) dto.getNewsLinks(), ready));
        }
        if (dto.getUserLogin() != null) {
            entity.setUserLogin(userLoginConverter.convert((UserOnlyLoginBaseDto) dto.getUserLogin(), ready));
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
        return dto -> tagConverter.convert((TagFullDto) dto, ready);
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
