/*
 * This file was last modified at 2020.04.14 16:50 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * NewsEntryCrudService.java
 * $Id$
 */

package su.svn.showcase.services;

import su.svn.showcase.dto.jdo.NewsEntryJdo;

import java.util.UUID;

public interface NewsEntryCrudService extends CrudService<UUID, NewsEntryJdo> {
}
