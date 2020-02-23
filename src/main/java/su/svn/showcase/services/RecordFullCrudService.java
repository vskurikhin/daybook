/*
 * This file was last modified at 2020.02.22 17:46 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * RecordFullCrudService.java
 * $Id$
 */

package su.svn.showcase.services;

import su.svn.showcase.dto.RecordFullDto;

import java.util.UUID;

public interface RecordFullCrudService extends CrudService<UUID, RecordFullDto> {
}
