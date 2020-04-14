/*
 * This file was last modified at 2020.04.14 20:47 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * NewsEntryCreateModel.java
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
import su.svn.showcase.dto.jdo.NewsEntryJdo;
import su.svn.showcase.dto.jdo.NewsGroupJdo;
import su.svn.showcase.dto.RecordFullDto;
import su.svn.showcase.dto.jdo.TagJdo;
import su.svn.showcase.services.*;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = false)
@Builder(builderClassName = "Builder")
class NewsEntryCreateModel extends AbstractModel {

    private static final Logger LOGGER = LoggerFactory.getLogger(NewsEntryCreateModel.class);

    private String title;
    private String tags;
    private String date;
    private String content;
    private String group;
    private String login;

    private final NewsEntryCrudService newsEntryCrudService;

    private final NewsGroupCrudService newsGroupCrudService;

    private final RecordTagsStorageService recordTagsStorageService;

    public void save() {
        Objects.requireNonNull(newsEntryCrudService);
        Objects.requireNonNull(newsGroupCrudService);
        Objects.requireNonNull(recordTagsStorageService);
        Objects.requireNonNull(title);
        Objects.requireNonNull(login);
        Objects.requireNonNull(group);

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
                .type(NewsEntryJdo.class.getSimpleName())
                .userLogin(userLoginDto)
                .build();
        NewsGroupJdo newsGroupJdo = newsGroupCrudService.readByGroup(group);
        NewsEntryJdo newsEntryDto = NewsEntryJdo.builder()
                .id(uuid)
                .record(recordDto)
                .dateTime(currentDateTime)
                .title(title)
                .content(content)
                .newsGroup(newsGroupJdo)
                .build();
        LOGGER.info("newsEntryDto = {}", newsEntryDto); // TODO remove
        recordDto.setNewsEntry(newsEntryDto);
        newsEntryCrudService.create(newsEntryDto);
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
