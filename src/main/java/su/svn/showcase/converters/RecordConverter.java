/*
 * This file was last modified at 2020.03.22 22:59 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * TagConverter.java
 * $Id$
 */

package su.svn.showcase.converters;

import su.svn.showcase.domain.Record;
import su.svn.showcase.dto.RecordFullDto;

import java.util.UUID;

public interface RecordConverter extends EntityConverter<UUID, Record, RecordFullDto> {
}
