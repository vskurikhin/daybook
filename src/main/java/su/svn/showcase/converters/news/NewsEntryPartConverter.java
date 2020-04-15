/*
 * This file was last modified at 2020.04.15 22:24 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * NewsEntryPartConverter.java
 * $Id$
 */

package su.svn.showcase.converters.news;

import su.svn.showcase.converters.NewsEntryConverter;
import su.svn.showcase.converters.NewsGroupConverter;
import su.svn.showcase.converters.RecordConverter;
import su.svn.showcase.domain.NewsEntry;
import su.svn.showcase.dto.jdo.NewsEntryJdo;
import su.svn.showcase.utils.ReadyMap;

import javax.annotation.Nonnull;
import javax.ejb.EJB;
import javax.ejb.Stateless;

@Stateless(name = "NewsEntryPartConverter")
public class NewsEntryPartConverter extends NewsEntryAbstractConverter implements NewsEntryConverter {

    @EJB(beanName = "RecordPartConverter")
    private RecordConverter recordConverter;

    @EJB(beanName = "NewsGroupBaseConverter")
    private NewsGroupConverter newsGroupConverter;

    @Override
    public NewsEntryJdo convert(@Nonnull NewsEntry entity) {
        return doConvert(new NewsEntryJdo(entity.getId()), entity, new ReadyMap());
    }

    @Override
    public NewsEntryJdo convert(@Nonnull NewsEntry entity, @Nonnull ReadyMap ready) {
        return doConvert(new NewsEntryJdo(entity.getId()), entity, ready);
    }

    @Override
    RecordConverter getRecordConverter() {
        return recordConverter;
    }

    @Override
    NewsGroupConverter getNewsGroupConverter() {
        return newsGroupConverter;
    }

    @Override
    public NewsEntry convert(@Nonnull NewsEntryJdo dto) {
        return doConvert(new NewsEntry(dto.getId()), dto, new ReadyMap());
    }

    @Override
    public NewsEntry convert(@Nonnull NewsEntryJdo dto, @Nonnull ReadyMap ready) {
        return doConvert(new NewsEntry(dto.getId()), dto, ready);
    }

    @Override
    public NewsEntry update(@Nonnull NewsEntry entity, @Nonnull NewsEntryJdo dto) {
        return doConvert(entity, dto, new ReadyMap());
    }

    @Override
    public NewsEntry update(@Nonnull NewsEntry entity, @Nonnull NewsEntryJdo dto, @Nonnull ReadyMap ready) {
        return doConvert(entity, dto, ready);
    }
}
//EOF