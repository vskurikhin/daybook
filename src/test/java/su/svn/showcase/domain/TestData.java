/*
 * This file was last modified at 2020.04.14 16:50 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * TestData.java
 * $Id$
 */

package su.svn.showcase.domain;

import su.svn.showcase.dto.jdo.NewsEntryJdo;

import java.util.*;

import static su.svn.utils.TestData.*;

public class TestData {

    private static final Role role0 = Role.builder()
            .id(UUID0)
            .roleName("testRole0")
            .build();
    private static final Role role1 = Role.builder()
            .id(UUID1)
            .roleName("testRole1")
            .build();

    private static final UserLogin userLogin0 = UserLogin.builder()
            .id(UUID0)
            .login("loginTest0")
            .password("passwordTest0")
            .dateTime(NOW)
            .build();
    private static final UserLogin userLogin1 = UserLogin.builder()
            .id(UUID1)
            .login("loginTest1")
            .password("passwordTest1")
            .dateTime(NOW)
            .build();

    private static final UserRole userRole0 = UserRole.builder()
            .id(UUID0)
            .dateTime(NOW)
            .roleName("testRole0")
            .build();
    private static final UserRole userRole1 = UserRole.builder()
            .id(UUID1)
            .dateTime(NOW)
            .roleName("testRole1")
            .build();

    private static final Tag tag0 = Tag.builder()
            .id(SID0)
            .tag("tagTest0")
            .dateTime(NOW)
            .visible(true)
            .build();
    private static final Tag tag1 = Tag.builder()
            .id(SID1)
            .tag("tagTest1")
            .dateTime(NOW)
            .visible(true)
            .build();

    private static final Link link0 = Link.builder()
            .id(UUID0)
            .dateTime(NOW)
            .visible(true)
            .link("linkTest0")
            .build();
    private static final Link link1 = Link.builder()
            .id(UUID1)
            .dateTime(NOW)
            .visible(true)
            .link("linkTest1")
            .build();

    private static final LinkDescription linkDescription0 = LinkDescription.builder()
            .id(UUID0)
            .dateTime(NOW)
            .description("descriptionTest0")
            .details("detailsTest0")
            .build();
    private static final LinkDescription linkDescription1 = LinkDescription.builder()
            .id(UUID1)
            .dateTime(NOW)
            .description("descriptionTest1")
            .details("detailsTest1")
            .build();

    private static final Record record0 = Record.builder()
            .id(UUID0)
            .createDateTime(NOW)
            .editDateTime(NOW)
            .index(13)
            .type(NewsEntryJdo.class.getSimpleName())
            .build();
    private static final Record record1 = Record.builder()
            .id(UUID1)
            .createDateTime(NOW)
            .editDateTime(NOW)
            .index(11)
            .type(NewsEntryJdo.class.getSimpleName())
            .build();

    private static final NewsGroup newsGroup0 = NewsGroup.builder()
            .id(UUID0)
            .dateTime(NOW)
            .group("groupTest0")
            .build();
    private static final NewsGroup newsGroup1 = NewsGroup.builder()
            .id(UUID1)
            .dateTime(NOW)
            .group("groupTest1")
            .build();

    private static final NewsEntry newsEntry0 = NewsEntry.builder()
            .id(UUID0)
            .dateTime(NOW)
            .title("titleTest0")
            .content("contentTest0")
            .build();
    private static final NewsEntry newsEntry1 = NewsEntry.builder()
            .id(UUID1)
            .dateTime(NOW)
            .title("titleTest1")
            .content("contentTest1")
            .build();

    private static final NewsLinks newsLinks0 = NewsLinks.builder()
            .id(UUID0)
            .dateTime(NOW)
            .title("titleTest0")
            .build();
    private static final NewsLinks newsLinks1 = NewsLinks.builder()
            .id(UUID1)
            .dateTime(NOW)
            .title("titleTest1")
            .build();

    private static final Article article0 = Article.builder()
            .id(UUID0)
            .dateTime(NOW)
            .title("titleTest0")
            .include("includeTest0")
            .summary("summaryTest0")
            .build();
    private static final Article article1 = Article.builder()
            .id(UUID1)
            .dateTime(NOW)
            .title("titleTest1")
            .include("includeTest1")
            .summary("summaryTest1")
            .build();

    static {
        userLogin0.setRoles(newList(userRole0));
        userLogin1.setRoles(newList(userRole1));

        userRole0.setUserLogin(userLogin0);
        userRole0.setRole(role0);
        userRole1.setUserLogin(userLogin1);
        userRole1.setRole(role1);

        tag0.setRecords(newSet(record0));
        tag1.setRecords(newSet(record1));

        newsGroup0.setNewsEntries(newList(newsEntry0));
        newsGroup1.setNewsEntries(newList(newsEntry1));

        newsEntry0.setRecord(record0);
        newsEntry0.setNewsGroup(newsGroup0);

        newsEntry1.setRecord(record1);
        newsEntry1.setNewsGroup(newsGroup1);

        record0.setUserLogin(userLogin0);
        // TODO record0.setNewsEntry(newsEntry0);
        record0.setTags(newSet(tag0));

        record1.setUserLogin(userLogin1);
        // TODO record1.setNewsEntry(newsEntry1);
        record1.setTags(newSet(tag1));

        linkDescription0.setLink(link0);
        linkDescription1.setLink(link1);
        linkDescription0.setNewsLinks(newsLinks0);
        linkDescription1.setNewsLinks(newsLinks1);

        link0.setDescriptions(newSet(linkDescription0));
        link1.setDescriptions(newSet(linkDescription1));

        newsLinks0.setRecord(record0);
        newsLinks1.setRecord(record1);
        newsLinks0.setNewsGroup(newsGroup0);
        newsLinks1.setNewsGroup(newsGroup1);
        newsLinks1.setDescriptions(newSet(linkDescription1));
        newsLinks0.setDescriptions(newSet(linkDescription0));
        newsLinks1.setDescriptions(newSet(linkDescription1));

        article0.setRecord(record0);
        article1.setRecord(record1);
        article0.setLink(link0);
        article1.setLink(link1);
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

    public static Link cloneLink0() {
        return assertClone(link0);
    }
    public static Link cloneLink1() {
        return assertClone(link1);
    }

    public static Link clean(Link entity) {
        entity.setDescriptions(Collections.emptySet());
        return entity;
    }

    public static LinkDescription cloneLinkDescription0() {
        return assertClone(linkDescription0);
    }
    public static LinkDescription cloneLinkDescription1() {
        return assertClone(linkDescription1);
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

    public static NewsLinks cloneNewsLinks0() {
        return assertClone(newsLinks0);
    }
    public static NewsLinks cloneNewsLinks1() {
        return assertClone(newsLinks1);
    }

    public static NewsLinks clean(NewsLinks entity) {
        entity.getNewsGroup().setNewsEntries(Collections.emptyList());
        entity.getRecord().getUserLogin().setRoles(Collections.emptyList());
        entity.getRecord().setTags(Collections.emptySet());
        entity.setDescriptions(Collections.emptySet());
        return entity;
    }

    public static Article cloneArticle0() {
        return assertClone(article0);
    }
    public static Article cloneArticle1() {
        return assertClone(article1);
    }

    public static Article clean(Article entity) {
        entity.getRecord().getUserLogin().setRoles(Collections.emptyList());
        entity.getRecord().setTags(Collections.emptySet());
        entity.setLink(null);
        return entity;
    }
}