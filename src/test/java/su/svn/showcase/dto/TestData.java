/*
 * This file was last modified at 2020.03.19 22:42 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * TestData.java
 * $Id$
 */

package su.svn.showcase.dto;

import java.util.Collections;

import static su.svn.utils.TestData.*;

@SuppressWarnings("WeakerAccess")
public class TestData {

    private static final RoleBaseDto roleBaseDto0 = RoleBaseDto.builder()
            .id(UUID0)
            .roleName("testRole0")
            .build();
    private static final RoleBaseDto roleBaseDto1 = RoleBaseDto.builder()
            .id(UUID1)
            .roleName("testRole1")
            .build();

    private static final UserRoleBaseDto userRoleBaseDto0 = UserRoleBaseDto.builder()
            .id(UUID0)
            .dateTime(NOW)
            .roleName("testRole0")
            .build();
    private static final UserRoleBaseDto userRoleBaseDto1 = UserRoleBaseDto.builder()
            .id(UUID1)
            .dateTime(NOW)
            .roleName("testRole1")
            .build();

    private static final UserRoleFullDto userRoleFullDto0 = UserRoleFullDto.builder()
            .id(UUID0)
            .dateTime(NOW)
            .roleName("testRole0")
            .build();
    private static final UserRoleFullDto userRoleFullDto1 = UserRoleFullDto.builder()
            .id(UUID1)
            .dateTime(NOW)
            .roleName("testRole1")
            .build();

    private static final UserOnlyLoginBaseDto userOnlyLoginBaseDto0 = UserOnlyLoginBaseDto.builder()
            .id(UUID0)
            .login("loginTest0")
            .build();
    private static final UserOnlyLoginBaseDto userOnlyLoginBaseDto1 = UserOnlyLoginBaseDto.builder()
            .id(UUID1)
            .login("loginTest1")
            .build();

    private static final UserLoginFullDto userLoginFullDto0 = UserLoginFullDto.builder()
            .id(UUID0)
            .dateTime(NOW)
            .login("loginTest0")
            .password("passwordTest0")
            .build();
    public static final UserLoginFullDto userLoginFullDto1 = UserLoginFullDto.builder()
            .id(UUID1)
            .dateTime(NOW)
            .login("loginTest1")
            .password("passwordTest1")
            .build();

    private static final TagBaseDto tagBaseDto0 = TagBaseDto.builder()
            .id(SID0)
            .tag("tagTest0")
            .dateTime(NOW)
            .visible(true)
            .build();
    private static final TagBaseDto tagBaseDto1 = TagBaseDto.builder()
            .id(SID1)
            .tag("tagTest1")
            .dateTime(NOW)
            .visible(true)
            .build();

    private static final TagFullDto tagFullDto0 = TagFullDto.builder()
            .id(SID0)
            .tag("tagTest0")
            .dateTime(NOW)
            .visible(true)
            .build();
    private static final TagFullDto tagFullDto1 = TagFullDto.builder()
            .id(SID1)
            .tag("tagTest1")
            .dateTime(NOW)
            .visible(true)
            .build();

    private static final RecordBaseDto recordBaseDto0 = RecordBaseDto.builder()
            .id(UUID0)
            .createDateTime(NOW)
            .editDateTime(NOW)
            .index(13)
            .type(RecordBaseDto.class.getSimpleName())
            .build();
    private static final RecordBaseDto recordBaseDto1 = RecordBaseDto.builder()
            .id(UUID1)
            .createDateTime(NOW)
            .editDateTime(NOW)
            .index(11)
            .type(RecordBaseDto.class.getSimpleName())
            .build();

    private static final RecordFullDto recordFullDto0 = RecordFullDto.builder()
            .id(UUID0)
            .createDateTime(NOW)
            .editDateTime(NOW)
            .index(13)
            .type(NewsEntryFullDto.class.getSimpleName())
            .build();
    private static final RecordFullDto recordFullDto1 = RecordFullDto.builder()
            .id(UUID1)
            .createDateTime(NOW)
            .editDateTime(NOW)
            .index(11)
            .type(NewsEntryFullDto.class.getSimpleName())
            .build();

    private static final NewsGroupBaseDto newsGroupBaseDto0 = NewsGroupBaseDto.builder()
            .id(UUID0)
            .dateTime(NOW)
            .group("groupTest0")
            .build();
    private static final NewsGroupBaseDto newsGroupBaseDto1 = NewsGroupBaseDto.builder()
            .id(UUID1)
            .dateTime(NOW)
            .group("groupTest1")
            .build();

    private static final NewsEntryBaseDto newsEntryBaseDto0 = NewsEntryBaseDto.builder()
            .id(UUID0)
            .dateTime(NOW)
            .title("titleTest0")
            .content("contentTest0")
            .build();
    private static final NewsEntryBaseDto newsEntryBaseDto1 = NewsEntryBaseDto.builder()
            .id(UUID1)
            .dateTime(NOW)
            .title("titleTest1")
            .content("contentTest1")
            .build();

    private static final NewsEntryFullDto newsEntryFullDto0 = NewsEntryFullDto.builder()
            .id(UUID0)
            .dateTime(NOW)
            .title("titleTest0")
            .content("contentTest0")
            .build();
    private static final NewsEntryFullDto newsEntryFullDto1 = NewsEntryFullDto.builder()
            .id(UUID1)
            .dateTime(NOW)
            .title("titleTest1")
            .content("contentTest1")
            .build();

    private static final NewsLinksBaseDto newsLinksBaseDto0 = NewsLinksBaseDto.builder()
            .id(UUID0)
            .dateTime(NOW)
            .title("titleTest0")
            .build();
    private static final NewsLinksBaseDto newsLinksBaseDto1 = NewsLinksBaseDto.builder()
            .id(UUID1)
            .dateTime(NOW)
            .title("titleTest1")
            .build();

    private static final ArticleBaseDto articleBaseDto0 = ArticleBaseDto.builder()
            .id(UUID0)
            .dateTime(NOW)
            .title("titleTest0")
            .include("titleInclude0")
            .summary("titleSummary0")
            .build();
    private static final ArticleBaseDto articleBaseDto1 = ArticleBaseDto.builder()
            .id(UUID1)
            .dateTime(NOW)
            .title("titleTest1")
            .include("titleInclude1")
            .summary("titleSummary1")
            .build();

    static {
        tagFullDto0.setRecords(newSet(cloneRecordBaseDto0()));
        tagFullDto1.setRecords(newSet(cloneRecordBaseDto1()));

        userRoleFullDto0.setRole(cloneRoleBaseDto0());
        userRoleFullDto0.setUserLogin(cloneUserOnlyLoginBaseDto0());
        userRoleFullDto1.setRole(cloneRoleBaseDto1());
        userRoleFullDto1.setUserLogin(cloneUserOnlyLoginBaseDto1());

        userLoginFullDto0.setRoles(newList(cloneUserRoleBaseDto0()));
        userLoginFullDto1.setRoles(newList(cloneUserRoleBaseDto1()));

        recordFullDto0.setUserLogin(cloneUserOnlyLoginBaseDto0());
        recordFullDto1.setUserLogin(cloneUserOnlyLoginBaseDto1());
        recordFullDto0.setNewsEntry(cloneNewsEntryBaseDto0());
        recordFullDto1.setNewsEntry(cloneNewsEntryBaseDto1());
        recordFullDto0.setTags(newSet(cloneTagBaseDto0()));
        recordFullDto1.setTags(newSet(cloneTagBaseDto1()));

        newsEntryFullDto0.setRecord(cloneRecordFullDto0());
        newsEntryFullDto0.setNewsGroup(cloneNewsGroupBaseDto0());
        newsEntryFullDto1.setRecord(cloneRecordFullDto1());
        newsEntryFullDto1.setNewsGroup(cloneNewsGroupBaseDto1());
    }

    public static RoleBaseDto cloneRoleBaseDto0() {
        return assertClone(roleBaseDto0);
    }
    public static RoleBaseDto cloneRoleBaseDto1() {
        return assertClone(roleBaseDto1);
    }

    public static UserRoleBaseDto cloneUserRoleBaseDto0() {
        return assertClone(userRoleBaseDto0);
    }
    public static UserRoleBaseDto cloneUserRoleBaseDto1() {
        return assertClone(userRoleBaseDto1);
    }

    public static UserRoleFullDto cloneUserRoleFullDto0() {
        return assertClone(userRoleFullDto0);
    }
    public static UserRoleFullDto cloneUserRoleFullDto1() {
        return assertClone(userRoleFullDto1);
    }

    public static UserOnlyLoginBaseDto cloneUserOnlyLoginBaseDto0() {
        return assertClone(userOnlyLoginBaseDto0);
    }
    public static UserOnlyLoginBaseDto cloneUserOnlyLoginBaseDto1() {
        return assertClone(userOnlyLoginBaseDto1);
    }

    public static UserLoginFullDto cloneUserLoginFullDto0() {
        return assertClone(userLoginFullDto0);
    }
    public static UserLoginFullDto cloneUserLoginFullDto1() {
        return assertClone(userLoginFullDto1);
    }

    public static TagBaseDto cloneTagBaseDto0() {
        return assertClone(tagBaseDto0);
    }
    public static TagBaseDto cloneTagBaseDto1() {
        return assertClone(tagBaseDto1);
    }

    public static RecordBaseDto cloneRecordBaseDto0() {
        return assertClone(recordBaseDto0);
    }
    public static RecordBaseDto cloneRecordBaseDto1() {
        return assertClone(recordBaseDto1);
    }

    public static RecordFullDto cloneRecordFullDto0() {
        return assertClone(recordFullDto0);
    }
    public static RecordFullDto cloneRecordFullDto1() {
        return assertClone(recordFullDto1);
    }

    public static RecordFullDto clean(RecordFullDto dto) {
        dto.setNewsEntry(null);
        dto.setTags(Collections.emptySet());
        return dto;
    }

    public static NewsGroupBaseDto cloneNewsGroupBaseDto0() {
        return assertClone(newsGroupBaseDto0);
    }
    public static NewsGroupBaseDto cloneNewsGroupBaseDto1() {
        return assertClone(newsGroupBaseDto1);
    }

    public static NewsEntryBaseDto cloneNewsEntryBaseDto0() {
        return assertClone(newsEntryBaseDto0);
    }
    public static NewsEntryBaseDto cloneNewsEntryBaseDto1() {
        return assertClone(newsEntryBaseDto1);
    }

    public static NewsEntryFullDto cloneNewsEntryFullDto0() {
        return assertClone(newsEntryFullDto0);
    }
    public static NewsEntryFullDto cloneNewsEntryFullDto1() {
        return assertClone(newsEntryFullDto1);
    }

    public static NewsLinksBaseDto cloneNewsLinksBaseDto0() {
        return assertClone(newsLinksBaseDto0);
    }
    public static NewsLinksBaseDto cloneNewsLinksBaseDto1() {
        return assertClone(newsLinksBaseDto1);
    }

    public static ArticleBaseDto cloneArticleBaseDto0() {
        return assertClone(articleBaseDto0);
    }
    public static ArticleBaseDto cloneArticleBaseDto1() {
        return assertClone(articleBaseDto1);
    }
}
//EOF
