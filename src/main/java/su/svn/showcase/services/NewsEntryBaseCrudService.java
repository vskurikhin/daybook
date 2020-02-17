/*
 * This file was last modified at 2020.02.16 00:14 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * NewsEntryBaseCrudService.java$
 * $Id$
 */

package su.svn.showcase.services;

import su.svn.showcase.dto.NewsEntryBaseDto;

import java.util.UUID;

public interface NewsEntryBaseCrudService extends CrudService<UUID, NewsEntryBaseDto> {
}
