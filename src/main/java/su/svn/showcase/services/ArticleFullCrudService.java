/*
 * This file was last modified at 2020.03.20 19:57 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * ArticleFullCrudService.java
 * $Id$
 */

package su.svn.showcase.services;

import su.svn.showcase.dto.ArticleFullDto;

import java.util.UUID;

public interface ArticleFullCrudService extends CrudService<UUID, ArticleFullDto> {
}
