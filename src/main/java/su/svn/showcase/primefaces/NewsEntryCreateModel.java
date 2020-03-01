/*
 * This file was last modified at 2020.03.01 19:14 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * NewsEntryCreateModel.java
 * $Id$
 */

package su.svn.showcase.primefaces;

import lombok.Builder;
import lombok.Data;
import su.svn.showcase.converters.StringTagSetConverter;
import su.svn.showcase.dto.*;
import su.svn.showcase.services.*;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Data
@Builder(builderClassName = "Builder")
class NewsEntryCreateModel {

    private String title;
    private String tags;
    private String date;
    private String content;
    private String group;
    private String login;

    private final NewsEntryFullCrudService newsEntryCrudService;

    private final NewsGroupBaseCrudService newsGroupCrudService;

    private final RecordFullCrudService recordCrudService;

    private final RecordTagsStorageService recordTagsStorageService;

    private final UserOnlyLoginRoService userOnlyLoginRoService;

    public void save() {
        Objects.requireNonNull(newsEntryCrudService);
        Objects.requireNonNull(newsGroupCrudService);
        Objects.requireNonNull(recordCrudService);
        Objects.requireNonNull(recordTagsStorageService);
        Objects.requireNonNull(userOnlyLoginRoService);
        Objects.requireNonNull(title);
        this.login = "admin@mail.ru"; // Objects.requireNonNull(login); TODO
        Objects.requireNonNull(group);

        UserOnlyLoginBaseDto userLoginDto = UserOnlyLoginBaseDto.builder()
                .login(this.login)
                .build();
        UUID uuid = UUID.randomUUID();
        LocalDateTime now = LocalDateTime.now();
        RecordFullDto recordDto = RecordFullDto.builder()
                .id(uuid)
                .createDateTime(now)
                .editDateTime(now)
                .index(13)
                .type(NewsEntryFullDto.class.getSimpleName())
                .userLogin(userLoginDto)
                .build();
        NewsGroupBaseDto newsGroupBaseDto = newsGroupCrudService.readByGroup(group);
        NewsEntryFullDto newsEntryDto = NewsEntryFullDto.builder()
                .id(uuid)
                .record(recordDto)
                .dateTime(now)
                .title(title)
                .content(content)
                .newsGroup(newsGroupBaseDto)
                .build();
        recordDto.setNewsEntry(newsEntryDto);
        newsEntryCrudService.create(newsEntryDto);
        if (tags != null) {
            Set<TagBaseDto> tagSet = StringTagSetConverter.map(tags);
            recordTagsStorageService.addTagsToRecord(recordDto, tagSet);
        }
    }
}
//EOF
