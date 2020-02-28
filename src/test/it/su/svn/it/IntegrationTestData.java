/*
 * This file was last modified at 2020.02.24 20:09 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * IntegrationTestData.java
 * $Id$
 */

package su.svn.it;

import su.svn.showcase.domain.*;
import su.svn.showcase.dto.*;

import java.util.*;

public class IntegrationTestData extends TestData {

    public static final int UPPER_BOUND = 4;

    private static final Role[] roles = {
            Role.builder().id(ROLE_UUID0).roleName("testRole0").build(),
            Role.builder().id(ROLE_UUID1).roleName("testRole1").build(),
            Role.builder().id(ROLE_UUID2).roleName("testRole2").build(),
            Role.builder().id(ROLE_UUID3).roleName("testRole3").build(),
    };

    private static final RoleBaseDto[] roleBaseDtos = {
            RoleBaseDto.builder().id(ROLE_UUID0).roleName("testRole0").build(),
            RoleBaseDto.builder().id(ROLE_UUID1).roleName("testRole1").build(),
            RoleBaseDto.builder().id(ROLE_UUID2).roleName("testRole2").build(),
            RoleBaseDto.builder().id(ROLE_UUID3).roleName("testRole3").build(),
    };

    private static final UserRole[] userRoles = {
            UserRole.builder().id(USER_ROLE_UUID0).roleName("testUserRole0").dateTime(NOW).build(),
            UserRole.builder().id(USER_ROLE_UUID1).roleName("testUserRole1").dateTime(NOW).build(),
            UserRole.builder().id(USER_ROLE_UUID2).roleName("testUserRole2").dateTime(NOW).build(),
            UserRole.builder().id(USER_ROLE_UUID3).roleName("testUserRole3").dateTime(NOW).build(),
    };

    private static final UserRoleFullDto[] userRoleFullDtos = {
            UserRoleFullDto.builder().id(USER_ROLE_UUID0).roleName("testUserRole0").dateTime(NOW).build(),
            UserRoleFullDto.builder().id(USER_ROLE_UUID1).roleName("testUserRole1").dateTime(NOW).build(),
            UserRoleFullDto.builder().id(USER_ROLE_UUID2).roleName("testUserRole2").dateTime(NOW).build(),
            UserRoleFullDto.builder().id(USER_ROLE_UUID3).roleName("testUserRole3").dateTime(NOW).build(),
    };

    private static final UserLogin[] userLogins = {
            UserLogin.builder()
                    .id(USER_LOGIN_UUID0)
                    .login("loginTest0")
                    .password("passwordTest0")
                    .dateTime(NOW)
                    .build(),
            UserLogin.builder()
                    .id(USER_LOGIN_UUID1)
                    .login("loginTest1")
                    .password("passwordTest1")
                    .dateTime(NOW)
                    .build(),
            UserLogin.builder()
                    .id(USER_LOGIN_UUID2)
                    .login("loginTest2")
                    .password("passwordTest2")
                    .dateTime(NOW)
                    .build(),
            UserLogin.builder()
                    .id(USER_LOGIN_UUID3)
                    .login("loginTest3")
                    .password("passwordTest3")
                    .dateTime(NOW)
                    .build(),
    };

    private static final UserOnlyLoginBaseDto[] userOnlyLoginBaseDtos = {
            UserOnlyLoginBaseDto.builder()
                    .id(USER_LOGIN_UUID0)
                    .login("loginTest0")
                    .build(),
            UserOnlyLoginBaseDto.builder()
                    .id(USER_LOGIN_UUID1)
                    .login("loginTest1")
                    .build(),
            UserOnlyLoginBaseDto.builder()
                    .id(USER_LOGIN_UUID2)
                    .login("loginTest2")
                    .build(),
            UserOnlyLoginBaseDto.builder()
                    .id(USER_LOGIN_UUID3)
                    .login("loginTest3")
                    .build(),
    };

    private static final Tag[] tags = {
            Tag.builder().id(TAG_ID0).tag("tagTest0").visible(false).dateTime(NOW).build(),
            Tag.builder().id(TAG_ID1).tag("tagTest1").visible(true).dateTime(NOW).build(),
            Tag.builder().id(TAG_ID2).tag("tagTest2").visible(false).dateTime(NOW).build(),
            Tag.builder().id(TAG_ID3).tag("tagTest3").visible(true).dateTime(NOW).build(),
    };

    private static final TagBaseDto[] tagBaseDtos = {
            TagBaseDto.builder().id(TAG_ID0).tag("tagTest0").visible(false).dateTime(NOW).build(),
            TagBaseDto.builder().id(TAG_ID1).tag("tagTest1").visible(true).dateTime(NOW).build(),
            TagBaseDto.builder().id(TAG_ID2).tag("tagTest2").visible(false).dateTime(NOW).build(),
            TagBaseDto.builder().id(TAG_ID3).tag("tagTest3").visible(true).dateTime(NOW).build(),
    };

    private static final NewsGroup[] newsGroups = {
            NewsGroup.builder()
                    .id(NEWS_ENTRY_UUID0)
                    .dateTime(NOW)
                    .group("groupTest0")
                    .build(),
            NewsGroup.builder()
                    .id(NEWS_ENTRY_UUID1)
                    .dateTime(NOW)
                    .group("groupTest1")
                    .build(),
            NewsGroup.builder()
                    .id(NEWS_ENTRY_UUID2)
                    .dateTime(NOW)
                    .group("groupTest2")
                    .build(),
            NewsGroup.builder()
                    .id(NEWS_ENTRY_UUID3)
                    .dateTime(NOW)
                    .group("groupTest3")
                    .build()
    };

    private static final NewsGroupBaseDto[] newsGroupBaseDtos = {
            NewsGroupBaseDto.builder()
                    .id(NEWS_ENTRY_UUID0)
                    .dateTime(NOW)
                    .group("groupTest0")
                    .build(),
            NewsGroupBaseDto.builder()
                    .id(NEWS_ENTRY_UUID1)
                    .dateTime(NOW)
                    .group("groupTest1")
                    .build(),
            NewsGroupBaseDto.builder()
                    .id(NEWS_ENTRY_UUID2)
                    .dateTime(NOW)
                    .group("groupTest2")
                    .build(),
            NewsGroupBaseDto.builder()
                    .id(NEWS_ENTRY_UUID3)
                    .dateTime(NOW)
                    .group("groupTest3")
                    .build()
    };

    private static final Record[] records = {
            Record.builder()
                    .id(RECORD_UUID0)
                    .createDateTime(NOW)
                    .editDateTime(NOW)
                    .index(13)
                    .type(NewsEntryFullDto.class.getSimpleName())
                    .build(),
            Record.builder()
                    .id(RECORD_UUID1)
                    .createDateTime(NOW)
                    .editDateTime(NOW)
                    .index(1)
                    .type(NewsEntryFullDto.class.getSimpleName())
                    .build(),
            Record.builder()
                    .id(RECORD_UUID2)
                    .createDateTime(NOW)
                    .editDateTime(NOW)
                    .index(2)
                    .type(NewsEntryFullDto.class.getSimpleName())
                    .build(),
            Record.builder()
                    .id(RECORD_UUID3)
                    .createDateTime(NOW)
                    .editDateTime(NOW)
                    .index(3)
                    .type(NewsEntryFullDto.class.getSimpleName())
                    .build()
    };

    private static final RecordBaseDto[] recordBaseDtos = {
            RecordBaseDto.builder()
                    .id(RECORD_UUID0)
                    .createDateTime(NOW)
                    .editDateTime(NOW)
                    .index(13)
                    .type(null)
                    .build(),
            RecordBaseDto.builder()
                    .id(RECORD_UUID1)
                    .createDateTime(NOW)
                    .editDateTime(NOW)
                    .index(1)
                    .type(null)
                    .build(),
            RecordBaseDto.builder()
                    .id(RECORD_UUID2)
                    .createDateTime(NOW)
                    .editDateTime(NOW)
                    .index(2)
                    .type(null)
                    .build(),
            RecordBaseDto.builder()
                    .id(RECORD_UUID3)
                    .createDateTime(NOW)
                    .editDateTime(NOW)
                    .index(3)
                    .type(null)
                    .build()
    };

    private static final RecordFullDto[] recordFullDtos = {
            RecordFullDto.builder()
                    .id(RECORD_UUID0)
                    .createDateTime(NOW)
                    .editDateTime(NOW)
                    .index(13)
                    .type(NewsEntryFullDto.class.getSimpleName())
                    .build(),
            RecordFullDto.builder()
                    .id(RECORD_UUID1)
                    .createDateTime(NOW)
                    .editDateTime(NOW)
                    .index(1)
                    .type(NewsEntryFullDto.class.getSimpleName())
                    .build(),
            RecordFullDto.builder()
                    .id(RECORD_UUID2)
                    .createDateTime(NOW)
                    .editDateTime(NOW)
                    .index(2)
                    .type(NewsEntryFullDto.class.getSimpleName())
                    .build(),
            RecordFullDto.builder()
                    .id(RECORD_UUID3)
                    .createDateTime(NOW)
                    .editDateTime(NOW)
                    .index(3)
                    .type(NewsEntryFullDto.class.getSimpleName())
                    .build()
    };

    private static final NewsEntry[] newsEntries = {
            NewsEntry.builder()
                    .id(NEWS_ENTRY_UUID0)
                    .dateTime(NOW)
                    .title("titleTest0")
                    .content("contentTest0")
                    .build(),
            NewsEntry.builder()
                    .id(NEWS_ENTRY_UUID1)
                    .dateTime(NOW)
                    .title("titleTest1")
                    .content("contentTest1")
                    .build(),
            NewsEntry.builder()
                    .id(NEWS_ENTRY_UUID2)
                    .dateTime(NOW)
                    .title("titleTest2")
                    .content("contentTest2")
                    .build(),
            NewsEntry.builder()
                    .id(NEWS_ENTRY_UUID3)
                    .dateTime(NOW)
                    .title("titleTest3")
                    .content("contentTest3")
                    .build()
    };

    private static final NewsEntryBaseDto[] newsEntryBaseDtos = {
            NewsEntryBaseDto.builder()
                    .id(NEWS_ENTRY_UUID0)
                    .dateTime(NOW)
                    .title("titleTest0")
                    .content("contentTest0")
                    .build(),
            NewsEntryBaseDto.builder()
                    .id(NEWS_ENTRY_UUID1)
                    .dateTime(NOW)
                    .title("titleTest1")
                    .content("contentTest1")
                    .build(),
            NewsEntryBaseDto.builder()
                    .id(NEWS_ENTRY_UUID2)
                    .dateTime(NOW)
                    .title("titleTest2")
                    .content("contentTest2")
                    .build(),
            NewsEntryBaseDto.builder()
                    .id(NEWS_ENTRY_UUID3)
                    .dateTime(NOW)
                    .title("titleTest3")
                    .content("contentTest3")
                    .build()
    };

    private static final NewsEntryFullDto[] newsEntryFullDtos = {
            NewsEntryFullDto.builder()
                    .id(NEWS_ENTRY_UUID0)
                    .dateTime(NOW)
                    .title("titleTest0")
                    .content("contentTest0")
                    .build(),
            NewsEntryFullDto.builder()
                    .id(NEWS_ENTRY_UUID1)
                    .dateTime(NOW)
                    .title("titleTest1")
                    .content("contentTest1")
                    .build(),
            NewsEntryFullDto.builder()
                    .id(NEWS_ENTRY_UUID2)
                    .dateTime(NOW)
                    .title("titleTest2")
                    .content("contentTest2")
                    .build(),
            NewsEntryFullDto.builder()
                    .id(NEWS_ENTRY_UUID3)
                    .dateTime(NOW)
                    .title("titleTest3")
                    .content("contentTest3")
                    .build()
    };

    static {
        newsGroups[0].setNewsEntries(new ArrayList<>());
        for (int i = 0; i < UPPER_BOUND; ++i) {
            userRoles[i].setRole(roles[i]);
            userRoles[i].setUserLogin(userLogins[i]);
            userRoleFullDtos[i].setRole(roleBaseDtos[i]);
            userRoleFullDtos[i].setUserLogin(userOnlyLoginBaseDtos[i]);
            userLogins[i].setRoles(newList(userRoles[i]));
            // tags[i].setRecords(newSet());
            newsGroups[0].getNewsEntries().add(newsEntries[i]);
            records[i].setUserLogin(userLogins[i]);
            records[i].setNewsEntry(newsEntries[i]);
            recordFullDtos[i].setUserLogin(userOnlyLoginBaseDtos[i]);
            recordFullDtos[i].setNewsEntry(newsEntryFullDtos[i]);
            recordFullDtos[i].setTags(newSet(tagBaseDtos[i]));
            newsEntries[i].setNewsGroup(newsGroups[0]);
            newsEntries[i].setRecord(records[i]);
            newsEntryFullDtos[i].setRecord(recordFullDtos[i]);
            newsEntryFullDtos[i].setNewsGroup(newsGroupBaseDtos[i]);
        }
    }

    public static Role cloneRole(int i) {
        Role role = clone(roles[i]);
        assert role != null;
        return role;
    }

    public static RoleBaseDto cloneRoleBaseDto(int i) {
        RoleBaseDto role = clone(roleBaseDtos[i]);
        assert role != null;
        return role;
    }

    public static UserRole cloneUserRole(int i) {
        UserRole role = clone(userRoles[i]);
        assert role != null;
        return role;
    }

    public static UserRole clean(UserRole role) {
        role.setUserLogin(null);
        return role;
    }

    public static UserRoleFullDto cloneUserRoleFullDto(int i) {
        UserRoleFullDto role = clone(userRoleFullDtos[i]);
        assert role != null;
        return role;
    }

    public static UserRoleFullDto clean(UserRoleFullDto role) {
        role.setUserLogin(null);
        return role;
    }

    public static UserLogin cloneUserLogin(int i) {
        UserLogin login = clone(userLogins[i]);
        assert login != null;
        return login;
    }

    public static UserLogin clean(UserLogin login) {
        login.setRoles(Collections.emptyList());
        return login;
    }

    public static Tag cloneTag(int i) {
        Tag tag = clone(tags[i]);
        assert tag != null;
        return tag;
    }

    public static Tag clean(Tag tag) {
        tag.setRecords(Collections.emptySet());
        return tag;
    }

    public static TagBaseDto cloneTagBaseDto(int i) {
        TagBaseDto tag = clone(tagBaseDtos[i]);
        assert tag != null;
        return tag;
    }

    public static NewsGroup cloneNewsGroup(int i) {
        NewsGroup entity = clone(newsGroups[i]);
        assert entity != null;
        return entity;
    }

    public static NewsGroup clean(NewsGroup entity) {
        entity.setNewsEntries(Collections.emptyList());
        return entity;
    }

    public static NewsGroupBaseDto cloneNewsGroupBaseDto(int i) {
        NewsGroupBaseDto dto = clone(newsGroupBaseDtos[i]);
        assert dto != null;
        return dto;
    }

    public static Record cloneRecord(int i) {
        Record entity = clone(records[i]);
        assert entity != null;
        return entity;
    }

    public static Record clean(Record entity) {
        entity.setUserLogin(null);
        entity.setNewsEntry(null);
        entity.setTags(Collections.emptySet());
        return entity;
    }

    public static RecordBaseDto cloneRecordBaseDto(int i) {
        RecordBaseDto dto = clone(recordBaseDtos[i]);
        assert dto != null;
        return dto;
    }

    public static RecordFullDto cloneRecordFullDto(int i) {
        RecordFullDto dto = clone(recordFullDtos[i]);
        assert dto != null;
        return dto;
    }

    public static RecordFullDto clean(RecordFullDto dto) {
        if ( dto.getNewsEntry() instanceof NewsEntryFullDto) {
            ((NewsEntryFullDto) dto.getNewsEntry()).setRecord(null);
        }
        dto.setTags(Collections.emptySet());
        return dto;
    }

    public static NewsEntry cloneNewsEntry(int i) {
        NewsEntry entity = clone(newsEntries[i]);
        assert entity != null;
        return entity;
    }

    public static NewsEntry clean(NewsEntry entity) {
        entity.setNewsGroup(null);
        return entity;
    }

    public static NewsEntryBaseDto cloneNewsEntryBaseDto(int i) {
        NewsEntryBaseDto dto = clone(newsEntryBaseDtos[i]);
        assert dto != null;
        return dto;
    }

    public static NewsEntryFullDto cloneNewsEntryFullDto(int i) {
        NewsEntryFullDto dto = clone(newsEntryFullDtos[i]);
        assert dto != null;
        return dto;
    }
}