/*
 * This file was last modified at 2020.02.17 22:14 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * TestData.java
 * $Id$
 */

package su.svn.showcase.domain;

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
            .type("testType0")
            .build();
    private static final Record record1 = Record.builder()
            .id(RECORD_UUID1)
            .createDateTime(NOW)
            .editDateTime(NOW)
            .index(11)
            .type("testType1")
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
        userLogin0.setRoles(newList(cloneUserRole0()));
        userLogin1.setRoles(newList(cloneUserRole1()));

        userRole0.setUserLogin(cloneUserLogin0());
        userRole1.setUserLogin(cloneUserLogin1());

        tag0.setRecords(newSet(cloneRecord0()));
        tag1.setRecords(newSet(cloneRecord1()));

        record0.setUserLogin(cloneUserLogin0());
        record0.setTags(newSet(cloneTag0()));

        record1.setUserLogin(cloneUserLogin1());
        record1.setTags(newSet(cloneTag1()));

        newsGroup0.setNewsEntries(newList(cloneNewsEntry0()));
        newsGroup1.setNewsEntries(newList(cloneNewsEntry1()));

        newsEntry0.setRecord(cloneRecord0());
        newsEntry0.setNewsGroup(cloneNewsGroup0());

        newsEntry1.setRecord(cloneRecord1());
        newsEntry1.setNewsGroup(cloneNewsGroup1());
    }

    public static Role cloneRole0() {
        return assertClone(role0);
    }
    public static Role cloneRole1() {
        return assertClone(role1);
    }

    public static UserLogin cloneUserLogin0() {
        return assertClone(userLogin0);
    }
    public static UserLogin cloneUserLogin1() {
        return assertClone(userLogin1);
    }

    public static UserLogin clean(UserLogin login) {
        login.setRoles(Collections.emptyList());
        return login;
    }

    public static UserRole cloneUserRole0() {
        return assertClone(userRole0);
    }
    public static UserRole cloneUserRole1() {
        return assertClone(userRole1);
    }

    public static UserRole clean(UserRole role) {
        role.getUserLogin().setRoles(Collections.emptyList());
        return role;
    }

    public static Tag cloneTag0() {
        return assertClone(tag0);
    }
    public static Tag cloneTag1() {
        return assertClone(tag1);
    }

    public static Tag clean(Tag tag) {
        tag.setRecords(Collections.emptySet());
        return tag;
    }

    public static Record cloneRecord0() {
        return assertClone(record0);
    }
    public static Record cloneRecord1() {
        return assertClone(record1);
    }

    public static Record clean(Record entity) {
        entity.setNewsEntry(null);
        entity.setTags(Collections.emptySet());
        entity.getUserLogin().setRoles(Collections.emptyList());
        return entity;
    }

    public static NewsGroup cloneNewsGroup0() {
        return assertClone(newsGroup0);
    }
    public static NewsGroup cloneNewsGroup1() {
        return assertClone(newsGroup1);
    }

    public static NewsGroup clean(NewsGroup entity) {
        entity.setNewsEntries(Collections.emptyList());
        return entity;
    }

    public static NewsEntry cloneNewsEntry0() {
        return assertClone(newsEntry0);
    }
    public static NewsEntry cloneNewsEntry1() {
        return assertClone(newsEntry1);
    }

    public static NewsEntry clean(NewsEntry entity) {
        entity.getNewsGroup().setNewsEntries(Collections.emptyList());
        entity.getRecord().getUserLogin().setRoles(Collections.emptyList());
        entity.getRecord().setTags(Collections.emptySet());
        return entity;
    }
}