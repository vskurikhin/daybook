/*
 * This file was last modified at 2020.04.15 00:03 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * RecordConverter.java
 * $Id$
 */

package su.svn.showcase.converters;

import su.svn.showcase.domain.Record;
import su.svn.showcase.dto.jdo.RecordJdo;

import javax.annotation.Nonnull;
import java.util.UUID;

public interface RecordConverter extends EntityConverter<UUID, Record, RecordJdo> {

    class Updater {

        public static Record update(@Nonnull Record entity, @Nonnull RecordJdo dto) {
            entity.setCreateDateTime(dto.getCreateDateTime());
            entity.setEditDateTime(dto.getEditDateTime());
            entity.setType(dto.getType());

            return entity;
        }
    }
}
