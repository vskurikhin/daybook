/*
 * This file was last modified at 2020.04.01 22:50 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * NewsGroupBaseConverterImpl.java
 * $Id$
 */

package su.svn.showcase.converters.impl;

import su.svn.showcase.converters.NewsGroupConverter;
import su.svn.showcase.domain.NewsGroup;
import su.svn.showcase.dto.NewsGroupFullDto;
import su.svn.showcase.utils.ReadyMap;

import javax.annotation.Nonnull;
import javax.ejb.Stateless;
import java.util.UUID;

@Stateless(name = "NewsGroupBaseConverter")
public class NewsGroupBaseConverter extends AbstractConverter<UUID, NewsGroup, NewsGroupFullDto>
       implements NewsGroupConverter {

    @Override
    public NewsGroupFullDto convert(@Nonnull NewsGroup entity) {
        return super.convertByGetter(new NewsGroupFullDto(), entity);
    }

    @Override
    public NewsGroupFullDto convert(@Nonnull NewsGroup entity, ReadyMap ready) {
        return super.convertByGetter(new NewsGroupFullDto(), entity);
    }

    @Override
    public NewsGroup convert(@Nonnull NewsGroupFullDto dto) {
        return super.convertBySetter(new NewsGroup(dto.getId()), dto);
    }

    @Override
    public NewsGroup convert(@Nonnull NewsGroupFullDto dto, ReadyMap ready) {
        return super.convertBySetter(new NewsGroup(dto.getId()), dto);
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
