/*
 * This file was last modified at 2020.02.06 22:29 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * TestData.java
 * $Id$
 */

package su.svn.showcase.domain;

import su.svn.utils.SerializeUtil;

import java.util.*;

import static su.svn.utils.TestData.*;

public class TestData {

    private static final Role role0 = Role.builder()
            .id(ROLE_UUID0)
            .roleName("testRole0")
            .build();
    private static final Role role1 = Role.builder()
            .id(ROLE_UUID1)
            .roleName("testRole1")
            .build();

    private static final UserLogin userLogin0 = UserLogin.builder()
            .id(USER_LOGIN_UUID0)
            .login("loginTest0")
            .password("passwordTest0")
            .dateTime(NOW)
            .build();
    private static final UserLogin userLogin1 = UserLogin.builder()
            .id(USER_LOGIN_UUID1)
            .login("loginTest1")
            .password("passwordTest1")
            .dateTime(NOW)
            .build();

    private static final UserRole userRole0 = UserRole.builder()
            .id(USER_ROLE_UUID0)
            .dateTime(NOW)
            .roleName("testRole0")
            .build();
    private static final UserRole userRole1 = UserRole.builder()
            .id(USER_ROLE_UUID1)
            .dateTime(NOW)
            .roleName("testRole1")
            .build();

    private static final Tag tag0 = Tag.builder()
            .id(TAG_ID0)
            .tag("tagTest0")
            .dateTime(NOW)
            .visible(true)
            .build();
    private static final Tag tag1 = Tag.builder()
            .id(TAG_ID1)
            .tag("tagTest1")
            .dateTime(NOW)
            .visible(true)
            .build();

    private static final Record record0 = Record.builder()
            .id(RECORD_UUID0)
            .createDateTime(NOW)
            .editDateTime(NOW)
            .index(13)
            .type("testType")
            .build();
    private static final Record record1 = Record.builder()
            .id(RECORD_UUID1)
            .createDateTime(NOW)
            .editDateTime(NOW)
            .index(11)
            .type("testType")
            .build();

    private static final NewsGroup newsGroup0 = NewsGroup.builder()
            .id(NEWS_ENTRY_UUID0)
            .dateTime(NOW)
            .group("groupTest0")
            .build();
    private static final NewsGroup newsGroup1 = NewsGroup.builder()
            .id(NEWS_ENTRY_UUID1)
            .dateTime(NOW)
            .group("groupTest1")
            .build();

    private static final NewsEntry newsEntry0 = NewsEntry.builder()
            .id(NEWS_ENTRY_UUID0)
            .dateTime(NOW)
            .title("titleTest0")
            .content("contentTest0")
            .build();
    private static final NewsEntry newsEntry1 = NewsEntry.builder()
            .id(NEWS_ENTRY_UUID1)
            .dateTime(NOW)
            .title("titleTest1")
            .content("contentTest1")
            .build();

    static {
        userLogin0.setRoles(newList(getUserRole0()));
        userLogin1.setRoles(newList(getUserRole1()));

        userRole0.setUserLogin(getUserLogin0());
        userRole1.setUserLogin(getUserLogin1());

        tag0.setRecords(newSet(getRecord0()));
        tag1.setRecords(newSet(getRecord1()));

        record0.setUserLogin(getUserLogin0());
        record0.setTags(newSet(getTag0()));

        record1.setUserLogin(getUserLogin1());
        record1.setTags(newSet(getTag1()));

        newsGroup0.setNewsEntries(newList(getNewsEntry0()));
        newsGroup1.setNewsEntries(newList(getNewsEntry1()));

        newsEntry0.setRecord(getRecord0());
        newsEntry0.setNewsGroup(getNewsGroup0());

        newsEntry1.setRecord(getRecord1());
        newsEntry1.setNewsGroup(getNewsGroup1());
    }

    public static Role getRole0() {
        return role0;
    }
    public static Role getRole1() {
        return role1;
    }
    public static Role getCloneOfRole1() {
        Role role = SerializeUtil.clone(getRole1());
        assert role != null;
        return role;
    }

    public static UserLogin getUserLogin0() {
        return userLogin0;
    }
    public static UserLogin getUserLogin1() {
        return userLogin1;
    }
    public static UserLogin getCloneOfUserLogin1() {
        UserLogin userLogin = SerializeUtil.clone(getUserLogin1());
        assert userLogin != null;
        userLogin.setRoles(Collections.emptyList());
        return userLogin;
    }

    public static UserRole getUserRole0() {
        return userRole0;
    }
    public static UserRole getUserRole1() {
        return userRole1;
    }
    public static UserRole getCloneOfUserRole1() {
        UserRole userRole = SerializeUtil.clone(getUserRole1());
        assert userRole != null;
        userRole.getUserLogin().setRoles(Collections.emptyList());
        return userRole;
    }

    public static Tag getTag0() {
        return tag0;
    }
    public static Tag getTag1() {
        return tag1;
    }
    public static Tag getCloneOfTag1() {
        Tag tag = SerializeUtil.clone(tag1);
        assert tag != null;
        tag.setRecords(Collections.emptySet());
        return tag;
    }

    public static Record getRecord0() {
        return record0;
    }
    public static Record getRecord1() {
        return record1;
    }
    public static Record getCloneOfRecord1() {
        Record record = SerializeUtil.clone(record1);
        assert record != null;
        record.setTags(Collections.emptySet());
        record.getUserLogin().setRoles(Collections.emptyList());
        return record;
    }


    public static NewsGroup getNewsGroup0() {
        return newsGroup0;
    }
    public static NewsGroup getNewsGroup1() {
        return newsGroup1;
    }
    public static NewsGroup getCloneOfNewsGroup1() {
        NewsGroup newsGroup = SerializeUtil.clone(newsGroup1);
        assert newsGroup != null;
        newsGroup.setNewsEntries(Collections.emptyList());
        return newsGroup;
    }

    public static NewsEntry getNewsEntry0() {
        return newsEntry0;
    }
    public static NewsEntry getNewsEntry1() {
        return newsEntry1;
    }
    public static NewsEntry getCloneOfNewsEntry() {
        NewsEntry newsEntry = SerializeUtil.clone(newsEntry1);
        assert newsEntry != null;
        newsEntry.getNewsGroup().setNewsEntries(Collections.emptyList());
        newsEntry.getRecord().getUserLogin().setRoles(Collections.emptyList());
        newsEntry.getRecord().setTags(Collections.emptySet());
        return newsEntry;
    }
}