/*
 * This file was last modified at 2020.04.14 19:52 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * NewsGroupCrudService.java
 * $Id$
 */

package su.svn.showcase.services;

import su.svn.showcase.dto.jdo.NewsGroupJdo;

import java.util.UUID;

public interface NewsGroupCrudService extends CrudService<UUID, NewsGroupJdo> {
    NewsGroupJdo readByGroup(String group);
}
