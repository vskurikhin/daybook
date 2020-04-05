/*
 * This file was last modified at 2020.04.01 22:50 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * RecordBaseConverterImpl.java
 * $Id$
 */

package su.svn.showcase.converters.impl;

import su.svn.showcase.converters.RecordConverter;
import su.svn.showcase.domain.Record;
import su.svn.showcase.dto.RecordFullDto;
import su.svn.showcase.utils.ReadyMap;

import javax.annotation.Nonnull;
import javax.ejb.Stateless;
import java.util.UUID;

@Stateless(name = "RecordBaseConverter")
public class RecordBaseConverter extends AbstractConverter<UUID, Record, RecordFullDto>  implements RecordConverter {

    @Override
    public RecordFullDto convert(@Nonnull Record entity) {
        return super.convertByGetter(new RecordFullDto(), entity);
    }

    @Override
    public RecordFullDto convert(@Nonnull Record entity, ReadyMap ready) {
        return super.convertByGetter(new RecordFullDto(), entity);
    }

    @Override
    public Record convert(@Nonnull RecordFullDto dto) {
        return super.convertBySetter(new Record(dto.getId()), dto);
    }

    @Override
    public Record convert(@Nonnull RecordFullDto dto, ReadyMap ready) {
        return super.convertBySetter(new Record(dto.getId()), dto);
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
