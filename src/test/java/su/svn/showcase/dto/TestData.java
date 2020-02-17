/*
 * This file was last modified at 2020.02.09 22:43 by Victor N. Skurikhin.
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

    /*
    public static UserRoleFullDto getUserRoleFullDto0() {
        UserRoleFullDto userRoleFullDto0 = new UserRoleFullDto();
        userRoleFullDto0.setId(ROLE_UUID0);
        userRoleFullDto0.setUserLogin(getUserLoginBaseDto0());
        userRoleFullDto0.setDateTime(NOW);
        userRoleFullDto0.setRoleName("testRole0");
        return userRoleFullDto0;
    }
    public static UserRoleFullDto getUserRoleFullDto1() {
        UserRoleFullDto userRoleFullDto1 = new UserRoleFullDto();
        userRoleFullDto1.setId(USER_ROLE_UUID1);
        userRoleFullDto1.setUserLogin(getUserLoginBaseDto1());
        userRoleFullDto1.setDateTime(NOW);
        userRoleFullDto1.setRoleName("testRole1");
        return userRoleFullDto1;
    }
     */

    public static TagBaseDto getTagBaseDto0() {
        return tagBaseDto0;
    }
    public static TagBaseDto getTagBaseDto1() {
        return tagBaseDto1;
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

    public static RecordFullDto getRecordFullDto0() {
        return recordFullDto0;
    }
    public static RecordFullDto getRecordFullDto1() {
        return recordFullDto1;
    }

    public static NewsGroupBaseDto getNewsGroupBaseDto0() {
        return newsGroupBaseDto0;
    }
    public static NewsGroupBaseDto getNewsGroupBaseDto1() {
        return newsGroupBaseDto1;
    }

    /*
    public static NewsGroupFullDto getNewsGroupFullDto0() {
        NewsGroupFullDto newsGroup = new NewsGroupFullDto();
        newsGroup.setId(NEWS_GROUP_UUID0);
        newsGroup.setDateTime(NOW);
        newsGroup.setGroup("groupTest0");
        newsGroup.setNewsEntries(newList(getNewsEntryBaseDto0()));
        return newsGroup;
    }
    public static NewsGroupFullDto getNewsGroupFullDto1() {
        NewsGroupFullDto newsGroup = new NewsGroupFullDto();
        newsGroup.setId(NEWS_GROUP_UUID1);
        newsGroup.setDateTime(NOW);
        newsGroup.setGroup("groupTest1");
        newsGroup.setNewsEntries(newList(getNewsEntryBaseDto1()));
        return newsGroup;
    }
    public static NewsGroupFullDto getCloneOfNewsGroupFullDto1() {

        NewsGroupFullDto newsGroupFullDto = SerializeUtil.clone(getNewsGroupFullDto1());
        assert newsGroupFullDto != null;
        newsGroupFullDto.setNewsEntries(Collections.emptyList());
        return newsGroupFullDto;
    }
     */

    public static NewsEntryBaseDto getNewsEntryBaseDto0() {
        return newsEntryBaseDto0;
    }
    public static NewsEntryBaseDto getNewsEntryBaseDto1() {
        return newsEntryBaseDto1;
    }

    /*
    public static NewsEntryFullDto getNewsEntryFullDto0() {
        NewsEntryFullDto newsEntry = new NewsEntryFullDto();
        newsEntry.setId(NEWS_ENTRY_UUID0);
        newsEntry.setRecord(getRecordFullDto0());
        newsEntry.setDateTime(NOW);
        newsEntry.setTitle("titleTest0");
        newsEntry.setContent("contentTest0");
        newsEntry.setNewsGroup(getNewsGroupBaseDto0());
        return newsEntry;
    }
    public static NewsEntryFullDto getNewsEntryFullDto1() {
        NewsEntryFullDto newsEntry = new NewsEntryFullDto();
        newsEntry.setId(NEWS_ENTRY_UUID1);
        newsEntry.setRecord(getRecordFullDto1());
        newsEntry.setDateTime(NOW);
        newsEntry.setTitle("titleTest1");
        newsEntry.setContent("contentTest1");
        newsEntry.setNewsGroup(getNewsGroupBaseDto1());

        return newsEntry;
    }
    public static NewsEntryFullDto getCloneOfNewsEntryFullDto1() {

        NewsEntryFullDto newsEntryFullDto = SerializeUtil.clone(getNewsEntryFullDto1());
        assert newsEntryFullDto != null;
        newsEntryFullDto.getRecord().setTags(Collections.emptySet());
        return newsEntryFullDto;
    }
     */
    static {
        tagFullDto0.setRecords(newSet(cloneRecordBaseDto0()));
        tagFullDto1.setRecords(newSet(cloneRecordBaseDto1()));

        userRoleFullDto0.setUserLogin(cloneUserLoginBaseDto0());
        userRoleFullDto1.setUserLogin(cloneUserLoginBaseDto1());

        recordFullDto0.setUserLogin(cloneUserLoginBaseDto0());
        recordFullDto1.setUserLogin(cloneUserLoginBaseDto1());
        recordFullDto0.setTags(newSet(getTagBaseDto0()));
        recordFullDto1.setTags(newSet(getTagBaseDto1()));
    }
}
//EOF
