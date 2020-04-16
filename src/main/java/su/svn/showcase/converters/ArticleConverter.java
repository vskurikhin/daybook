/*
 * This file was last modified at 2020.04.15 00:03 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * ArticleConverter.java
 * $Id$
 */

package su.svn.showcase.converters;

import su.svn.showcase.domain.Article;
import su.svn.showcase.dto.jdo.ArticleJdo;

import javax.annotation.Nonnull;
import java.util.UUID;

public interface ArticleConverter extends EntityConverter<UUID, Article, ArticleJdo> {

    class Updater {

        public static Article update(@Nonnull Article entity, @Nonnull ArticleJdo dto) {
            entity.setDateTime(dto.getDateTime());
            entity.setTitle(dto.getTitle());
            entity.setInclude(dto.getInclude());
            entity.setAnchor(dto.getAnchor());
            entity.setSummary(dto.getSummary());

            return entity;
        }
    }
}
