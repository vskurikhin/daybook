/*
 * This file was last modified at 2020.03.31 20:05 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * ArticleConverter.java
 * $Id$
 */

package su.svn.showcase.converters;

import su.svn.showcase.domain.Article;
import su.svn.showcase.domain.Record;
import su.svn.showcase.dto.ArticleFullDto;
import su.svn.showcase.dto.RecordFullDto;

import java.util.UUID;

public interface ArticleConverter extends EntityConverter<UUID, Article, ArticleFullDto> {
}