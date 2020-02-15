/*
 * This file was last modified at 2020.02.12 23:11 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * NewsEntryDao.java
 * $Id$
 */

package su.svn.showcase.dao;

import su.svn.showcase.domain.NewsEntry;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface NewsEntryDao extends Dao<UUID, NewsEntry> {
}
