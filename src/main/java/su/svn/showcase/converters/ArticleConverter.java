/*
 * This file was last modified at 2020.04.06 22:03 by Victor N. Skurikhin.
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
import su.svn.showcase.utils.FieldUtil;

import javax.annotation.Nonnull;
import java.util.UUID;

public interface ArticleConverter extends EntityConverter<UUID, Article, ArticleFullDto> {

    class Updater {

        public static Article update(@Nonnull Article entity, @Nonnull ArticleFullDto dto) {
            FieldUtil.updateIfNotNull(entity::setDateTime, dto.getDateTime());
            FieldUtil.updateIfNotNull(entity::setTitle, dto.getTitle());
            FieldUtil.updateIfNotNull(entity::setInclude, dto.getInclude());
            FieldUtil.updateIfNotNull(entity::setAnchor, dto.getAnchor());
            FieldUtil.updateIfNotNull(entity::setSummary, dto.getSummary());

            return entity;
        }
    }
}
