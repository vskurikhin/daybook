/*
 * This file was last modified at 2020.04.15 00:03 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * RecordAbstractConverter.java
 * $Id$
 */

package su.svn.showcase.converters.impl;

import su.svn.showcase.converters.AbstractConverter;
import su.svn.showcase.converters.ArticleConverter;
import su.svn.showcase.converters.NewsEntryConverter;
import su.svn.showcase.converters.NewsLinksConverter;
import su.svn.showcase.converters.TagConverter;
import su.svn.showcase.domain.Record;
import su.svn.showcase.domain.Tag;
import su.svn.showcase.dto.TagDto;
import su.svn.showcase.dto.UserOnlyLoginDto;
import su.svn.showcase.dto.enums.RecordTypesEnum;
import su.svn.showcase.dto.jdo.ArticleJdo;
import su.svn.showcase.dto.jdo.NewsEntryJdo;
import su.svn.showcase.dto.jdo.NewsLinksJdo;
import su.svn.showcase.dto.jdo.RecordJdo;
import su.svn.showcase.dto.jdo.TagJdo;
import su.svn.showcase.exceptions.ErrorCase;
import su.svn.showcase.interfaces.Typing;
import su.svn.showcase.utils.ReadyMap;

import javax.annotation.Nullable;
import javax.ejb.Stateless;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Stateless(name = "RecordFullConverter")
abstract class RecordAbstractConverter extends AbstractConverter<UUID, Record, RecordJdo> {

    abstract ArticleConverter getArticleConverter();

    abstract NewsEntryConverter getNewsEntryConverter();

    abstract NewsLinksConverter getNewsLinksConverter();

    abstract UserOnlyLoginConverter getUserLoginConverter();

    abstract TagConverter getTagConverter();

    RecordJdo doConvert(RecordJdo dto, Record entity, ReadyMap ready) {
        ReadyMap.Key key = new ReadyMap.UuidKey(dto.getId(), RecordJdo.class);
        if (ready.containsKey(key)) {
            Object value = ready.get(key);
            if (value instanceof RecordJdo) {
                return (RecordJdo) value;
            }
            throw ErrorCase.badType(value.getClass().getSimpleName());
        } else {
            ready.put(dto);
        }
        switch (Objects.requireNonNull(getPosition(entity))) {
            case ArticleJdo:
                dto.setArticle(getArticleConverter().convert(entity.getArticle(), ready));
                break;
            case NewsEntryJdo:
                dto.setNewsEntry(getNewsEntryConverter().convert(entity.getNewsEntry(), ready));
                break;
            case NewsLinksJdo:
                dto.setNewsLinks(getNewsLinksConverter().convert(entity.getNewsLinks(), ready));
                break;
        }
        if (entity.getUserLogin() != null) {
            dto.setUserLogin(getUserLoginConverter().convert(entity.getUserLogin(), ready));
        }
        if (entity.getTags() != null) {
            Set<TagDto> set = entity.getTags().stream()
                    .map(functionTagToDto(ready))
                    .collect(Collectors.toSet());
            dto.setTags(set);
        }
        return super.convertByGetter(dto, entity);
    }

    private Function<Tag, TagJdo> functionTagToDto(ReadyMap ready) {
        return entity -> getTagConverter().convert(entity, ready);
    }

    Record doConvert(Record entity, RecordJdo dto, ReadyMap ready) {
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
        switch (Objects.requireNonNull(getPosition(dto))) {
            case ArticleJdo:
                entity.setArticle(getArticleConverter().convert((ArticleJdo) dto.getArticle(), ready));
                break;
            case NewsEntryJdo:
                entity.setNewsEntry(getNewsEntryConverter().convert((NewsEntryJdo) dto.getNewsEntry(), ready));
                break;
            case NewsLinksJdo:
                entity.setNewsLinks(getNewsLinksConverter().convert((NewsLinksJdo) dto.getNewsLinks(), ready));
                break;
        }
        if (dto.getUserLogin() != null) { // TODO only check
            UserOnlyLoginDto userLogin = ((UserOnlyLoginDto) dto.getUserLogin());
        }
        if (dto.getTags() != null) {
            Set<Tag> set = dto.getTags().stream()
                    .map(functionTagDtoToEntity(ready))
                    .collect(Collectors.toSet());
            entity.setTags(set);
        }
        return super.convertBySetter(entity, dto);
    }

    @Nullable
    private RecordTypesEnum getPosition(Typing typing) {
        if (typing.getType() != null) {
            return RecordTypesEnum.valueOf(typing.getType());
        }
        return null;
    }

    private Function<TagDto, Tag> functionTagDtoToEntity(ReadyMap ready) {
        return dto -> getTagConverter().convert((TagJdo) dto, ready);
    }

    @Override
    protected Class<Record> getEClass() {
        return Record.class;
    }

    @Override
    protected Class<RecordJdo> getDClass() {
        return RecordJdo.class;
    }
}
//EOF
