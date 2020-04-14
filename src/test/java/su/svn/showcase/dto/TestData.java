/*
 * This file was last modified at 2020.04.14 17:33 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * TestData.java
 * $Id$
 */

package su.svn.showcase.dto;

import su.svn.showcase.dto.jdo.ArticleJdo;
import su.svn.showcase.dto.jdo.LinkJdo;
import su.svn.showcase.dto.jdo.NewsEntryJdo;

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

    private static final UserOnlyLoginDto USER_ONLY_LOGIN_DTO_0 = UserOnlyLoginDto.builder()
            .id(UUID0)
            .login("loginTest0")
            .build();
    private static final UserOnlyLoginDto USER_ONLY_LOGIN_DTO_1 = UserOnlyLoginDto.builder()
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
            .type(NewsEntryJdo.class.getSimpleName())
            .build();
    private static final RecordFullDto recordFullDto1 = RecordFullDto.builder()
            .id(UUID1)
            .createDateTime(NOW)
            .editDateTime(NOW)
            .index(11)
            .type(NewsEntryJdo.class.getSimpleName())
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

    private static final NewsGroupFullDto newsGroupFullDto0 = NewsGroupFullDto.builder()
            .id(UUID0)
            .dateTime(NOW)
            .group("groupTest0")
            .build();
    private static final NewsGroupFullDto newsGroupFullDto1 = NewsGroupFullDto.builder()
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

    private static final NewsLinksFullDto newsLinksFullDto0 = NewsLinksFullDto.builder()
            .id(UUID0)
            .dateTime(NOW)
            .title("titleTest0")
            .build();
    private static final NewsLinksFullDto newsLinksFullDto1 = NewsLinksFullDto.builder()
            .id(UUID1)
            .dateTime(NOW)
            .title("titleTest1")
            .build();

    private static final ArticleJdo ARTICLE_JDO_0 = ArticleJdo.builder()
            .id(UUID0)
            .dateTime(NOW)
            .title("titleTest0")
            .include("titleInclude0")
            .anchor("titleAnchor0")
            .summary("titleSummary0")
            .build();
    private static final ArticleJdo ARTICLE_JDO_1 = ArticleJdo.builder()
            .id(UUID1)
            .dateTime(NOW)
            .title("titleTest1")
            .include("titleInclude1")
            .anchor("titleAnchor1")
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

        newsEntryJdo0.setNewsGroup(cloneNewsGroupBaseDto0());
        newsEntryJdo1.setNewsGroup(cloneNewsGroupBaseDto1());

        newsGroupFullDto0.setNewsEntries(newSet(cloneNewsEntryJdo0()));
        newsGroupFullDto1.setNewsEntries(newSet(cloneNewsEntryJdo1()));

        recordFullDto0.setUserLogin(cloneUserOnlyLoginBaseDto0());
        recordFullDto1.setUserLogin(cloneUserOnlyLoginBaseDto1());
        recordFullDto0.setNewsEntry(cloneNewsEntryJdo0());
        recordFullDto1.setNewsEntry(cloneNewsEntryJdo1());
        recordFullDto0.setTags(newSet(cloneTagBaseDto0()));
        recordFullDto1.setTags(newSet(cloneTagBaseDto1()));

        newsEntryJdo0.setRecord(cloneRecordFullDto0());
        newsEntryJdo1.setRecord(cloneRecordFullDto1());
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

    public static UserOnlyLoginDto cloneUserOnlyLoginBaseDto0() {
        return assertClone(USER_ONLY_LOGIN_DTO_0);
    }
    public static UserOnlyLoginDto cloneUserOnlyLoginBaseDto1() {
        return assertClone(USER_ONLY_LOGIN_DTO_1);
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

    public static TagFullDto cloneTagFullDto0() {
        return assertClone(tagFullDto0);
    }
    public static TagFullDto cloneTagFullDto1() {
        return assertClone(tagFullDto1);
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

    public static NewsGroupFullDto cloneNewsGroupFullDto0() {
        return assertClone(newsGroupFullDto0);
    }
    public static NewsGroupFullDto cloneNewsGroupFullDto1() {
        return assertClone(newsGroupFullDto1);
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

    public static NewsLinksBaseDto cloneNewsLinksBaseDto0() {
        return assertClone(newsLinksBaseDto0);
    }
    public static NewsLinksBaseDto cloneNewsLinksBaseDto1() {
        return assertClone(newsLinksBaseDto1);
    }

    public static NewsLinksFullDto cloneNewsLinksFullDto0() {
        return assertClone(newsLinksFullDto0);
    }
    public static NewsLinksFullDto cloneNewsLinksFullDto1() {
        return assertClone(newsLinksFullDto1);
    }

    public static ArticleJdo cloneArticleJdo0() {
        return assertClone(ARTICLE_JDO_0);
    }
    public static ArticleJdo cloneArticleJdo1() {
        return assertClone(ARTICLE_JDO_1);
    }
}
//EOF
