/*
 * This file was last modified at 2020.04.14 21:45 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * RecordConverter.java
 * $Id$
 */

package su.svn.showcase.converters;

import su.svn.showcase.domain.Record;
import su.svn.showcase.dto.jdo.RecordJdo;

import java.util.UUID;

public interface RecordConverter extends EntityConverter<UUID, Record, RecordJdo> {
}
