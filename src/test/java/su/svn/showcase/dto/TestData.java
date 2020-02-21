/*
 * This file was last modified at 2020.02.17 22:15 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * TestData.java
 * $Id$
 */

package su.svn.showcase.dto;

import static su.svn.utils.TestData.*;


public class TestData {

    private static final RoleBaseDto roleBaseDto0 = RoleBaseDto.builder()
            .id(ROLE_UUID0)
            .roleName("testRole0")
            .build();
    private static final RoleBaseDto roleBaseDto1 = RoleBaseDto.builder()
            .id(ROLE_UUID1)
            .roleName("testRole1")
            .build();

    private static final UserRoleFullDto userRoleFullDto0 = UserRoleFullDto.builder()
            .id(USER_LOGIN_UUID0)
            .dateTime(NOW)
            .roleName("testRole0")
            .build();
    private static final UserRoleFullDto userRoleFullDto1 = UserRoleFullDto.builder()
            .id(USER_LOGIN_UUID1)
            .dateTime(NOW)
            .roleName("testRole1")
            .build();

    private static final UserLoginBaseDto userLoginBaseDto0 = UserLoginBaseDto.builder()
            .id(USER_LOGIN_UUID0)
            .login("loginTest0")
            .password("passwordTest0")
            .dateTime(NOW)
            .build();
    private static final UserLoginBaseDto userLoginBaseDto1 = UserLoginBaseDto.builder()
            .id(USER_LOGIN_UUID1)
            .login("loginTest1")
            .password("passwordTest1")
            .dateTime(NOW)
            .build();

    private static final TagBaseDto tagBaseDto0 = TagBaseDto.builder()
            .id(TAG_ID0)
            .tag("tagTest0")
            .dateTime(NOW)
            .visible(true)
            .build();
    private static final TagBaseDto tagBaseDto1 = TagBaseDto.builder()
            .id(TAG_ID1)
            .tag("tagTest1")
            .dateTime(NOW)
            .visible(true)
            .build();

    private static final TagFullDto tagFullDto0 = TagFullDto.builder()
            .id(TAG_ID0)
            .tag("tagTest0")
            .dateTime(NOW)
            .visible(true)
            .build();
    private static final TagFullDto tagFullDto1 = TagFullDto.builder()
            .id(TAG_ID1)
            .tag("tagTest1")
            .dateTime(NOW)
            .visible(true)
            .build();

    private static final RecordBaseDto recordBaseDto0 = RecordBaseDto.builder()
            .id(RECORD_UUID0)
            .createDateTime(NOW)
            .editDateTime(NOW)
            .index(13)
            .type("testType0")
            .build();
    private static final RecordBaseDto recordBaseDto1 = RecordBaseDto.builder()
            .id(RECORD_UUID1)
            .createDateTime(NOW)
            .editDateTime(NOW)
            .index(11)
            .type("testType1")
            .build();

    private static final RecordFullDto recordFullDto0 = RecordFullDto.builder()
            .id(RECORD_UUID0)
            .createDateTime(NOW)
            .editDateTime(NOW)
            .index(13)
            .type("testType")
            .build();
    private static final RecordFullDto recordFullDto1 = RecordFullDto.builder()
            .id(RECORD_UUID1)
            .createDateTime(NOW)
            .editDateTime(NOW)
            .index(11)
            .type("testType")
            .build();

    private static final NewsGroupBaseDto newsGroupBaseDto0 = NewsGroupBaseDto.builder()
            .id(NEWS_GROUP_UUID0)
            .dateTime(NOW)
            .group("groupTest0")
            .build();
    private static final NewsGroupBaseDto newsGroupBaseDto1 = NewsGroupBaseDto.builder()
            .id(NEWS_GROUP_UUID1)
            .dateTime(NOW)
            .group("groupTest1")
            .build();

    private static final NewsEntryBaseDto newsEntryBaseDto0 = NewsEntryBaseDto.builder()
            .id(NEWS_ENTRY_UUID0)
            .dateTime(NOW)
            .title("titleTest0")
            .content("contentTest0")
            .build();
    private static final NewsEntryBaseDto newsEntryBaseDto1 = NewsEntryBaseDto.builder()
            .id(NEWS_ENTRY_UUID1)
            .dateTime(NOW)
            .title("titleTest1")
            .content("contentTest1")
            .build();

    public static RoleBaseDto cloneRoleBaseDto0() {
        return assertClone(roleBaseDto0);
    }
    public static RoleBaseDto cloneRoleBaseDto1() {
        return assertClone(roleBaseDto1);
    }

    public static UserRoleFullDto cloneUserRoleFullDto0() {
        return assertClone(userRoleFullDto0);
    }
    public static UserRoleFullDto cloneUserRoleFullDto1() {
        return assertClone(userRoleFullDto1);
    }

    public static UserLoginBaseDto cloneUserLoginBaseDto0() {
        return assertClone(userLoginBaseDto0);
    }
    public static UserLoginBaseDto cloneUserLoginBaseDto1() {
        return assertClone(userLoginBaseDto1);
    }


    public static TagBaseDto getTagBaseDto0() {
        return assertClone(tagBaseDto0);
    }
    public static TagBaseDto getTagBaseDto1() {
        return assertClone(tagBaseDto1);
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

    public static NewsGroupBaseDto cloneNewsGroupBaseDto0() {
        return assertClone(newsGroupBaseDto0);
    }
    public static NewsGroupBaseDto cloneNewsGroupBaseDto1() {
        return assertClone(newsGroupBaseDto1);
    }


    public static NewsEntryBaseDto getNewsEntryBaseDto0() {
        return assertClone(newsEntryBaseDto0);
    }
    public static NewsEntryBaseDto getNewsEntryBaseDto1() {
        return assertClone(newsEntryBaseDto1);
    }


    static {
        tagFullDto0.setRecords(newSet(cloneRecordBaseDto0()));
        tagFullDto1.setRecords(newSet(cloneRecordBaseDto1()));

        userRoleFullDto0.setRole(cloneRoleBaseDto0());
        userRoleFullDto0.setUserLogin(cloneUserLoginBaseDto0());
        userRoleFullDto1.setRole(cloneRoleBaseDto1());
        userRoleFullDto1.setUserLogin(cloneUserLoginBaseDto1());

        recordFullDto0.setUserLogin(cloneUserLoginBaseDto0());
        recordFullDto1.setUserLogin(cloneUserLoginBaseDto1());
        recordFullDto0.setTags(newSet(cloneTagBaseDto0()));
        recordFullDto1.setTags(newSet(cloneTagBaseDto1()));
    }
}
//EOF
