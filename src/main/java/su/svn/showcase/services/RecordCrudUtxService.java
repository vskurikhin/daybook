/*
 * This file was last modified at 2020.04.08 20:43 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * RecordCrudUtxService.java
 * $Id$
 */

package su.svn.showcase.services;

import su.svn.showcase.dto.RecordFullDto;

import java.util.UUID;

public interface RecordCrudUtxService extends CrudService<UUID, RecordFullDto> {
}
