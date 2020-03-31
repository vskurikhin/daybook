/*
 * This file was last modified at 2020.03.31 09:17 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * ArticleConverterImpl.java
 * $Id$
 */

package su.svn.showcase.converters.impl;

import su.svn.showcase.converters.ArticleConverter;
import su.svn.showcase.converters.RecordConverter;
import su.svn.showcase.domain.Article;
import su.svn.showcase.dto.*;
import su.svn.showcase.utils.ReadyMap;

import javax.annotation.Nonnull;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.UUID;

@Named("articleFull")
public class ArticleFullConverterImpl extends AbstractConverter<UUID, Article, ArticleFullDto>  implements ArticleConverter {

    @Inject
    @Named("recordFull")
    private RecordConverter recordConverter;

    @Override
    public ArticleFullDto convert(@Nonnull Article entity) {
        return doConvert(new ArticleFullDto(), entity, new ReadyMap());
    }

    @Override
    public ArticleFullDto convert(@Nonnull Article entity, ReadyMap ready) {
        return doConvert(new ArticleFullDto(), entity, ready);
    }

    private ArticleFullDto doConvert(ArticleFullDto dto, Article entity, ReadyMap ready) {
        if (entity.getRecord() != null) {
            dto.setRecord(getOrConvertUuidDto(entity.getRecord(), ready, recordConverter::convert));
        }
        if (entity.getLink() != null) {
            // TODO
        }
        return super.convertByGetter(dto, entity);
    }

    @Override
    public Article convert(@Nonnull ArticleFullDto dto) {
        return doConvert(new Article(dto.getId()), dto, new ReadyMap());
    }

    @Override
    public Article convert(@Nonnull ArticleFullDto dto, ReadyMap ready) {
        return doConvert(new Article(dto.getId()), dto, ready);
    }

    private Article doConvert(Article entity, ArticleFullDto dto, ReadyMap ready) {
        if (dto.getRecord() != null) {
            entity.setRecord(getOrConvertUuidEntity((RecordFullDto) dto.getRecord(), ready, recordConverter::convert));
        }
        if (dto.getLink() != null) {
            // TODO
        }
        return super.convertBySetter(entity, dto);
    }

    @Override
    Class<Article> getEClass() {
        return Article.class;
    }

    @Override
    Class<ArticleFullDto> getDClass() {
        return ArticleFullDto.class;
    }
}
