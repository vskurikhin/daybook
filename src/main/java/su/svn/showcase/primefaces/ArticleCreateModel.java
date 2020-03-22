/*
 * This file was last modified at 2020.03.21 23:39 by Victor N. Skurikhin.
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
import su.svn.showcase.services.ArticleFullCrudService;
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
    private String summary;
    private String link;
    private String tags;
    private String login;

    private final ArticleFullCrudService articleCrudService;

    private final LinkBaseCrudService linkBaseCrudService;

    private final RecordTagsStorageService recordTagsStorageService;

    public void save() {
        Objects.requireNonNull(articleCrudService);
        Objects.requireNonNull(linkBaseCrudService);
        Objects.requireNonNull(recordTagsStorageService);
        Objects.requireNonNull(title);
        Objects.requireNonNull(login);
        Objects.requireNonNull(link);

        UserOnlyLoginBaseDto userLoginDto = UserOnlyLoginBaseDto.builder()
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
                .type(ArticleFullDto.class.getSimpleName())
                .userLogin(userLoginDto)
                .build();
        LinkBaseDto linkBaseDto = LinkBaseDto.builder()
                .id(uuid)
                .dateTime(currentDateTime)
                .link(this.link)
                .build();
        linkBaseCrudService.create(linkBaseDto);
        ArticleFullDto articleDto = ArticleFullDto.builder()
                .id(uuid)
                .record(recordDto)
                .dateTime(currentDateTime)
                .title(this.title)
                .include(this.include)
                .summary(this.summary)
                .link(linkBaseDto)
                .build();
        LOGGER.info("articleDto = {}", articleDto); // TODO remove
        recordDto.setArticle(articleDto);
        articleCrudService.create(articleDto);
        if (tags != null) {
            Set<TagBaseDto> tagSet = StringTagSetConverter.map(tags);
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
