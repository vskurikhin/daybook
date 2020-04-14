/*
 * This file was last modified at 2020.04.14 20:47 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * ArticleCreateModel.java
 * $Id$
 */

package su.svn.showcase.primefaces;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import su.svn.showcase.converters.StringTagSetConverter;
import su.svn.showcase.dto.*;
import su.svn.showcase.dto.jdo.ArticleJdo;
import su.svn.showcase.dto.jdo.LinkJdo;
import su.svn.showcase.dto.RecordFullDto;
import su.svn.showcase.dto.jdo.TagJdo;
import su.svn.showcase.services.ArticleCrudService;
import su.svn.showcase.services.LinkBaseCrudService;
import su.svn.showcase.services.RecordTagsStorageService;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = false)
@Builder(builderClassName = "Builder")
class ArticleCreateModel extends AbstractModel {

    private static final Logger LOGGER = LoggerFactory.getLogger(ArticleCreateModel.class);

    private String title;
    private String include;
    private String date;
    private String anchor;
    private String summary;
    private String link;
    private String tags;
    private String login;

    private final ArticleCrudService articleCrudService;

    private final LinkBaseCrudService linkBaseCrudService;

    private final RecordTagsStorageService recordTagsStorageService;

    public void save() {
        Objects.requireNonNull(articleCrudService);
        Objects.requireNonNull(linkBaseCrudService);
        Objects.requireNonNull(recordTagsStorageService);
        Objects.requireNonNull(title);
        Objects.requireNonNull(anchor);
        Objects.requireNonNull(login);
        Objects.requireNonNull(link);

        UserOnlyLoginDto userLoginDto = UserOnlyLoginDto.builder()
                .login(this.login)
                .build();
        UUID uuid = UUID.randomUUID();
        LocalDateTime nowDateTime = LocalDateTime.now();
        LocalDateTime currentDateTime = parseLocalDateTime(this.date);
        RecordFullDto recordDto = RecordFullDto.builder()
                .id(uuid)
                .createDateTime(nowDateTime)
                .editDateTime(currentDateTime)
                .index(13)
                .type(ArticleJdo.class.getSimpleName())
                .userLogin(userLoginDto)
                .build();
        LinkJdo linkJdo = LinkJdo.builder()
                .id(uuid)
                .dateTime(currentDateTime)
                .link(this.link)
                .build();
        // linkBaseCrudService.create(linkBaseDto);
        ArticleJdo articleDto = ArticleJdo.builder()
                .id(uuid)
                .record(recordDto)
                .dateTime(currentDateTime)
                .title(this.title)
                .include(this.include)
                .anchor(this.anchor)
                .summary(this.summary)
                .link(linkJdo)
                .build();
        LOGGER.info("articleDto = {}", articleDto); // TODO remove
        recordDto.setArticle(articleDto);
        articleCrudService.create(articleDto);
        if (tags != null) {
            Set<TagJdo> tagSet = StringTagSetConverter.map(tags);
            LOGGER.info("recordDto = {}", recordDto); // TODO remove
            recordTagsStorageService.addTagsToRecord(recordDto, tagSet);
        }
    }

    @Override
    Logger getLogger() {
        return LOGGER;
    }
}
//EOF
