/*
 * This file was last modified at 2020.03.01 23:31 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * NewsEntryEditModel.java
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
class NewsEntryEditModel {

    private UUID uuid;
    private String title;
    private String tags;
    private String date;
    private String content;
    private String group;
    private String login;

    private final NewsEntryFullCrudService newsEntryCrudService;

    private final NewsGroupBaseCrudService newsGroupCrudService;

    private final RecordTagsStorageService recordTagsStorageService;

    public void save() {
        Objects.requireNonNull(uuid);
        Objects.requireNonNull(newsEntryCrudService);
        Objects.requireNonNull(newsGroupCrudService);
        Objects.requireNonNull(recordTagsStorageService);
        Objects.requireNonNull(title);
        this.login = "admin@mail.ru"; // Objects.requireNonNull(login); TODO
        Objects.requireNonNull(group);

        UserOnlyLoginBaseDto userLoginDto = UserOnlyLoginBaseDto.builder()
                .login(this.login)
                .build();
        LocalDateTime now = LocalDateTime.now();
        NewsGroupBaseDto newsGroupBaseDto = newsGroupCrudService.readByGroup(group);
        NewsEntryFullDto newsEntryDto = NewsEntryFullDto.builder()
                .id(uuid)
                .dateTime(now)
                .title(title)
                .content(content)
                //.newsGroup(newsGroupBaseDto)
                .build();
        // recordDto.setNewsEntry(newsEntryDto);
        System.out.println("newsEntryDto = " + newsEntryDto);
        newsEntryCrudService.update(newsEntryDto);
        if (tags != null) {
            RecordFullDto recordDto = RecordFullDto.builder()
                    .id(uuid)
                    .editDateTime(now)
                    .index(13)
                    .type(NewsEntryFullDto.class.getSimpleName())
                    .userLogin(userLoginDto)
                    .build();
            Set<TagBaseDto> tagSet = StringTagSetConverter.map(tags);
            System.out.println("recordDto = " + recordDto);
            recordTagsStorageService.addTagsToRecord(recordDto, tagSet);
        }
    }
}
//EOF
