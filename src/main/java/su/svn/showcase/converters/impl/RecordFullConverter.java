/*
 * This file was last modified at 2020.04.15 00:03 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * RecordFullConverter.java
 * $Id$
 */

package su.svn.showcase.converters.impl;

import su.svn.showcase.converters.ArticleConverter;
import su.svn.showcase.converters.NewsEntryConverter;
import su.svn.showcase.converters.NewsLinksConverter;
import su.svn.showcase.converters.RecordConverter;
import su.svn.showcase.converters.TagConverter;
import su.svn.showcase.domain.Record;
import su.svn.showcase.dto.jdo.RecordJdo;
import su.svn.showcase.utils.ReadyMap;

import javax.annotation.Nonnull;
import javax.ejb.EJB;
import javax.ejb.Stateless;

@Stateless(name = "RecordFullConverter")
public class RecordFullConverter extends RecordAbstractConverter implements RecordConverter {

    @EJB(beanName = "ArticleFullConverter")
    private ArticleConverter articleConverter;

    @EJB(beanName = "NewsEntryFullConverter")
    private NewsEntryConverter newsEntryConverter;

    @EJB(beanName = "NewsLinksFullConverter")
    private NewsLinksConverter newsLinksConverter;

    @EJB(beanName = "UserOnlyLoginConverter")
    private UserOnlyLoginConverter userLoginConverter;

    @EJB(beanName = "TagBaseConverter")
    private TagConverter tagConverter;

    @Override
    public RecordJdo convert(@Nonnull Record entity) {
        return doConvert(new RecordJdo(entity.getId()), entity, new ReadyMap());
    }

    @Override
    public RecordJdo convert(@Nonnull Record entity, @Nonnull ReadyMap ready) {
        return doConvert(new RecordJdo(entity.getId()), entity, ready);
    }

    @Override
    public Record convert(@Nonnull RecordJdo dto) {
        return doConvert(new Record(dto.getId()), dto, new ReadyMap());
    }

    @Override
    public Record convert(@Nonnull RecordJdo dto, @Nonnull ReadyMap ready) {
        return doConvert(new Record(dto.getId()), dto, ready);
    }

    @Override
    public Record update(@Nonnull Record entity, @Nonnull RecordJdo dto) {
        return doConvert(entity, dto, new ReadyMap());
    }

    @Override
    public Record update(@Nonnull Record entity, @Nonnull RecordJdo dto, @Nonnull ReadyMap ready) {
        return doConvert(entity, dto, ready);
    }

    @Override
    ArticleConverter getArticleConverter() {
        return articleConverter;
    }

    @Override
    NewsEntryConverter getNewsEntryConverter() {
        return newsEntryConverter;
    }

    @Override
    NewsLinksConverter getNewsLinksConverter() {
        return newsLinksConverter;
    }

    @Override
    UserOnlyLoginConverter getUserLoginConverter() {
        return userLoginConverter;
    }

    @Override
    TagConverter getTagConverter() {
        return tagConverter;
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
