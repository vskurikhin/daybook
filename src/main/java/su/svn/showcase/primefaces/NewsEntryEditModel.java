/*
 * This file was last modified at 2020.04.07 23:20 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * NewsEntryEditModel.java
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
import su.svn.showcase.services.*;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = false)
@Builder(builderClassName = "Builder")
class NewsEntryEditModel extends AbstractModel {

    private static final Logger LOGGER = LoggerFactory.getLogger(NewsEntryEditModel.class);

    private UUID uuid;
    private String title;
    private String tags;
    private String date;
    private String content;
    private String group;
    private String login;

    private final NewsEntryCrudService newsEntryCrudService;

    private final RecordTagsStorageService recordTagsStorageService;

    public void save() {
        Objects.requireNonNull(uuid);
        Objects.requireNonNull(newsEntryCrudService);
        Objects.requireNonNull(recordTagsStorageService);
        Objects.requireNonNull(title);
        Objects.requireNonNull(login);
        Objects.requireNonNull(group);

        UserOnlyLoginBaseDto userLoginDto = UserOnlyLoginBaseDto.builder()
                .login(this.login)
                .build();
        LocalDateTime currentDateTime = parseLocalDateTime(this.date);
        RecordFullDto recordDto = RecordFullDto.builder()
                .id(uuid)
                .editDateTime(currentDateTime)
                .index(13)
                .type(NewsEntryFullDto.class.getSimpleName())
                .userLogin(userLoginDto)
                .build();
        NewsEntryFullDto newsEntryDto = NewsEntryFullDto.builder()
                .id(uuid)
                .record(recordDto)
                .dateTime(currentDateTime)
                .title(title)
                .content(content)
                .build();
        LOGGER.info("newsEntryDto = {}", newsEntryDto); // TODO remove
        newsEntryCrudService.update(newsEntryDto);
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
