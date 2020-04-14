/*
 * This file was last modified at 2020.04.15 00:03 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * RecordBaseConverter.java
 * $Id$
 */

package su.svn.showcase.converters.impl;

import su.svn.showcase.converters.AbstractConverter;
import su.svn.showcase.converters.RecordConverter;
import su.svn.showcase.domain.Record;
import su.svn.showcase.dto.jdo.RecordJdo;
import su.svn.showcase.utils.ReadyMap;

import javax.annotation.Nonnull;
import javax.ejb.Stateless;
import java.util.UUID;

@Stateless(name = "RecordBaseConverter")
public class RecordBaseConverter extends AbstractConverter<UUID, Record, RecordJdo> implements RecordConverter {

    @Override
    public RecordJdo convert(@Nonnull Record entity) {
        return super.convertByGetter(new RecordJdo(), entity);
    }

    @Override
    public RecordJdo convert(@Nonnull Record entity, @Nonnull ReadyMap ready) {
        return super.convertByGetter(new RecordJdo(), entity);
    }

    @Override
    public Record convert(@Nonnull RecordJdo dto) {
        return super.convertBySetter(new Record(dto.getId()), dto);
    }

    @Override
    public Record convert(@Nonnull RecordJdo dto, @Nonnull ReadyMap ready) {
        return super.convertBySetter(new Record(dto.getId()), dto);
    }

    @Override
    public Record update(@Nonnull Record entity, @Nonnull RecordJdo dto) {
        return super.convertBySetter(entity, dto);
    }

    @Override
    public Record update(@Nonnull Record entity, @Nonnull RecordJdo dto, @Nonnull ReadyMap ready) {
        return super.convertBySetter(entity, dto);
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
