/*
 * This file was last modified at 2020.02.21 22:20 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * NewsEntryFullCrudService.java
 * $Id$
 */

package su.svn.showcase.services;

import su.svn.showcase.dto.NewsEntryFullDto;

import java.util.UUID;

public interface NewsEntryFullCrudService extends CrudService<UUID, NewsEntryFullDto> {
}
