/*
 * This file was last modified at 2020.04.14 21:45 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * TestData.java
 * $Id$
 */

package su.svn.showcase.dto;

import su.svn.showcase.dto.jdo.*;

import java.util.Collections;

import static su.svn.utils.TestData.*;

@SuppressWarnings("WeakerAccess")
public class TestData {

    private static final RoleJdo roleJdo0 = RoleJdo.builder()
            .id(UUID0)
            .roleName("testRole0")
            .build();
    private static final RoleJdo roleJdo1 = RoleJdo.builder()
            .id(UUID1)
            .roleName("testRole1")
            .build();

    private static final UserRoleJdo userRoleJdo0 = UserRoleJdo.builder()
            .id(UUID0)
            .dateTime(NOW)
            .roleName("testRole0")
            .build();
    private static final UserRoleJdo userRoleJdo1 = UserRoleJdo.builder()
            .id(UUID1)
            .dateTime(NOW)
            .roleName("testRole1")
            .build();

    private static final UserOnlyLoginDto userOnlyLoginDto0 = UserOnlyLoginDto.builder()
            .id(UUID0)
            .login("loginTest0")
            .build();
    private static final UserOnlyLoginDto userOnlyLoginDto1 = UserOnlyLoginDto.builder()
            .id(UUID1)
            .login("loginTest1")
            .build();

    private static final UserLoginJdo userLoginJdo0 = UserLoginJdo.builder()
            .id(UUID0)
            .dateTime(NOW)
            .login("loginTest0")
            .password("passwordTest0")
            .build();
    public static final UserLoginJdo userLoginJdo1 = UserLoginJdo.builder()
            .id(UUID1)
            .dateTime(NOW)
            .login("loginTest1")
            .password("passwordTest1")
            .build();

    private static final TagJdo tagJdo0 = TagJdo.builder()
            .id(SID0)
            .tag("tagTest0")
            .dateTime(NOW)
            .visible(true)
            .build();
    private static final TagJdo tagJdo1 = TagJdo.builder()
            .id(SID1)
            .tag("tagTest1")
            .dateTime(NOW)
            .visible(true)
            .build();

    private static final RecordJdo RECORD_JDO_0 = RecordJdo.builder()
            .id(UUID0)
            .createDateTime(NOW)
            .editDateTime(NOW)
            .index(13)
            .type(NewsEntryJdo.class.getSimpleName())
            .build();
    private static final RecordJdo RECORD_JDO_1 = RecordJdo.builder()
            .id(UUID1)
            .createDateTime(NOW)
            .editDateTime(NOW)
            .index(11)
            .type(NewsEntryJdo.class.getSimpleName())
            .build();

    private static final NewsGroupJdo newsGroupJdo0 = NewsGroupJdo.builder()
            .id(UUID0)
            .dateTime(NOW)
            .group("groupTest0")
            .build();
    private static final NewsGroupJdo newsGroupJdo1 = NewsGroupJdo.builder()
            .id(UUID1)
            .dateTime(NOW)
            .group("groupTest1")
            .build();

    private static final NewsEntryJdo newsEntryJdo0 = NewsEntryJdo.builder()
            .id(UUID0)
            .dateTime(NOW)
            .title("titleTest0")
            .content("contentTest0")
            .build();
    private static final NewsEntryJdo newsEntryJdo1 = NewsEntryJdo.builder()
            .id(UUID1)
            .dateTime(NOW)
            .title("titleTest1")
            .content("contentTest1")
            .build();

    private static final LinkJdo linksFullDto0 = LinkJdo.builder()
            .id(UUID0)
            .dateTime(NOW)
            .link("linkTest0")
            .visible(true)
            .build();
    private static final LinkJdo linksFullDto1 = LinkJdo.builder()
            .id(UUID1)
            .dateTime(NOW)
            .link("linkTest1")
            .visible(true)
            .build();

    private static final NewsLinksJdo newsLinksJdo0 = NewsLinksJdo.builder()
            .id(UUID0)
            .dateTime(NOW)
            .title("titleTest0")
            .build();
    private static final NewsLinksJdo newsLinksJdo1 = NewsLinksJdo.builder()
            .id(UUID1)
            .dateTime(NOW)
            .title("titleTest1")
            .build();

    private static final ArticleJdo articleJdo0 = ArticleJdo.builder()
            .id(UUID0)
            .dateTime(NOW)
            .title("titleTest0")
            .include("titleInclude0")
            .anchor("titleAnchor0")
            .summary("titleSummary0")
            .build();
    private static final ArticleJdo articleJdo1 = ArticleJdo.builder()
            .id(UUID1)
            .dateTime(NOW)
            .title("titleTest1")
            .include("titleInclude1")
            .anchor("titleAnchor1")
            .summary("titleSummary1")
            .build();

    static {
        tagJdo0.setRecords(newSet(cloneRecordFullDto0()));
        tagJdo1.setRecords(newSet(cloneRecordFullDto1()));

        userRoleJdo0.setRole(cloneRoleJdo0());
        userRoleJdo0.setUserLogin(cloneUserOnlyLoginBaseDto0());
        userRoleJdo1.setRole(cloneRoleJdo1());
        userRoleJdo1.setUserLogin(cloneUserOnlyLoginBaseDto1());

        userLoginJdo0.setRoles(newList(cloneUserRoleFullDto0()));
        userLoginJdo1.setRoles(newList(cloneUserRoleFullDto1()));

        newsEntryJdo0.setNewsGroup(cloneNewsGroupFullDto0());
        newsEntryJdo1.setNewsGroup(cloneNewsGroupFullDto1());

        newsGroupJdo0.setNewsEntries(newSet(cloneNewsEntryJdo0()));
        newsGroupJdo1.setNewsEntries(newSet(cloneNewsEntryJdo1()));

        RECORD_JDO_0.setUserLogin(cloneUserOnlyLoginBaseDto0());
        RECORD_JDO_1.setUserLogin(cloneUserOnlyLoginBaseDto1());
        RECORD_JDO_0.setNewsEntry(cloneNewsEntryJdo0());
        RECORD_JDO_1.setNewsEntry(cloneNewsEntryJdo1());
        RECORD_JDO_0.setTags(newSet(cloneTagBaseDto0()));
        RECORD_JDO_1.setTags(newSet(cloneTagBaseDto1()));

        newsEntryJdo0.setRecord(cloneRecordFullDto0());
        newsEntryJdo1.setRecord(cloneRecordFullDto1());
    }

    public static RoleJdo cloneRoleJdo0() {
        return assertClone(roleJdo0);
    }
    public static RoleJdo cloneRoleJdo1() {
        return assertClone(roleJdo1);
    }

    public static UserRoleJdo cloneUserRoleFullDto0() {
        return assertClone(userRoleJdo0);
    }
    public static UserRoleJdo cloneUserRoleFullDto1() {
        return assertClone(userRoleJdo1);
    }

    public static UserOnlyLoginDto cloneUserOnlyLoginBaseDto0() {
        return assertClone(userOnlyLoginDto0);
    }
    public static UserOnlyLoginDto cloneUserOnlyLoginBaseDto1() {
        return assertClone(userOnlyLoginDto1);
    }

    public static UserLoginJdo cloneUserLoginFullDto0() {
        return assertClone(userLoginJdo0);
    }
    public static UserLoginJdo cloneUserLoginFullDto1() {
        return assertClone(userLoginJdo1);
    }

    public static TagJdo cloneTagBaseDto0() {
        return assertClone(tagJdo0);
    }
    public static TagJdo cloneTagBaseDto1() {
        return assertClone(tagJdo1);
    }

    public static TagJdo cloneTagFullDto0() {
        return assertClone(tagJdo0);
    }
    public static TagJdo cloneTagFullDto1() {
        return assertClone(tagJdo1);
    }

    public static RecordJdo cloneRecordFullDto0() {
        return assertClone(RECORD_JDO_0);
    }
    public static RecordJdo cloneRecordFullDto1() {
        return assertClone(RECORD_JDO_1);
    }

    public static RecordJdo clean(RecordJdo dto) {
        dto.setNewsEntry(null);
        dto.setTags(Collections.emptySet());
        return dto;
    }

    public static NewsGroupJdo cloneNewsGroupFullDto0() {
        return assertClone(newsGroupJdo0);
    }
    public static NewsGroupJdo cloneNewsGroupFullDto1() {
        return assertClone(newsGroupJdo1);
    }

    public static NewsEntryJdo cloneNewsEntryJdo0() {
        return assertClone(newsEntryJdo0);
    }
    public static NewsEntryJdo cloneNewsEntryJdo1() {
        return assertClone(newsEntryJdo1);
    }

    public static LinkJdo cloneLinkFullDto0() {
        return assertClone(linksFullDto0);
    }
    public static LinkJdo cloneLinkFullDto1() {
        return assertClone(linksFullDto1);
    }

    public static NewsLinksJdo cloneNewsLinksJdo0() {
        return assertClone(newsLinksJdo0);
    }
    public static NewsLinksJdo cloneNewsLinksJdo1() {
        return assertClone(newsLinksJdo1);
    }

    public static ArticleJdo cloneArticleJdo0() {
        return assertClone(articleJdo0);
    }
    public static ArticleJdo cloneArticleJdo1() {
        return assertClone(articleJdo1);
    }
}
//EOF
