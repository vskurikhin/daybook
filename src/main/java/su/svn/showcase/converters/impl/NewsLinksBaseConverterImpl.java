/*
 * This file was last modified at 2020.04.01 17:19 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * NewsLinksBaseConverterImpl.java
 * $Id$
 */

package su.svn.showcase.converters.impl;

import su.svn.showcase.converters.NewsLinksConverter;
import su.svn.showcase.domain.NewsLinks;
import su.svn.showcase.dto.NewsLinksFullDto;
import su.svn.showcase.utils.ReadyMap;

import javax.annotation.Nonnull;
import javax.inject.Named;
import java.util.UUID;

@Named("newsLinksBaseConverter")
public class NewsLinksBaseConverterImpl extends AbstractConverter<UUID, NewsLinks, NewsLinksFullDto>
       implements NewsLinksConverter {

    @Override
    public NewsLinksFullDto convert(@Nonnull NewsLinks entity) {
        return super.convertByGetter(new NewsLinksFullDto(), entity);
    }

    @Override
    public NewsLinksFullDto convert(@Nonnull NewsLinks entity, ReadyMap ready) {
        return super.convertByGetter(new NewsLinksFullDto(), entity);
    }

    @Override
    public NewsLinks convert(@Nonnull NewsLinksFullDto dto) {
        return super.convertBySetter(new NewsLinks(dto.getId()), dto);
    }

    @Override
    public NewsLinks convert(@Nonnull NewsLinksFullDto dto, ReadyMap ready) {
        return super.convertBySetter(new NewsLinks(dto.getId()), dto);
    }

    @Override
    Class<NewsLinks> getEClass() {
        return NewsLinks.class;
    }

    @Override
    Class<NewsLinksFullDto> getDClass() {
        return NewsLinksFullDto.class;
    }
}
