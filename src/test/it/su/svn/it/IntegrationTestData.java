/*
 * This file was last modified at 2020.04.14 21:45 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * IntegrationTestData.java
 * $Id$
 */

package su.svn.it;

import su.svn.showcase.domain.*;
import su.svn.showcase.dto.*;
import su.svn.showcase.dto.jdo.*;

import java.util.*;

public class IntegrationTestData extends TestData
{

    public static final int UPPER_BOUND = 4;

    private static final Role[] roles = {
        Role.builder().id(UUID0).roleName("testRole0").build(),
        Role.builder().id(UUID1).roleName("testRole1").build(),
        Role.builder().id(UUID2).roleName("testRole2").build(),
        Role.builder().id(UUID3).roleName("testRole3").build(),
    };

    private static final RoleJdo[] roleBaseDtos = {
        RoleJdo.builder().id(UUID0).roleName("testRole0").build(),
        RoleJdo.builder().id(UUID1).roleName("testRole1").build(),
        RoleJdo.builder().id(UUID2).roleName("testRole2").build(),
        RoleJdo.builder().id(UUID3).roleName("testRole3").build(),
    };

    private static final UserRole[] userRoles = {
        UserRole.builder().id(UUID0).roleName("testUserRole0").dateTime(NOW).build(),
        UserRole.builder().id(UUID1).roleName("testUserRole1").dateTime(NOW).build(),
        UserRole.builder().id(UUID2).roleName("testUserRole2").dateTime(NOW).build(),
        UserRole.builder().id(UUID3).roleName("testUserRole3").dateTime(NOW).build(),
    };

    private static final UserRoleJdo[] USER_ROLE_JDOS = {
        UserRoleJdo.builder().id(UUID0).roleName("testUserRole0").dateTime(NOW).build(),
        UserRoleJdo.builder().id(UUID1).roleName("testUserRole1").dateTime(NOW).build(),
        UserRoleJdo.builder().id(UUID2).roleName("testUserRole2").dateTime(NOW).build(),
        UserRoleJdo.builder().id(UUID3).roleName("testUserRole3").dateTime(NOW).build(),
    };

    private static final UserLogin[] userLogins = {
        UserLogin.builder()
            .id(UUID0)
            .login("loginTest0")
            .password("passwordTest0")
            .dateTime(NOW)
            .build(),
        UserLogin.builder()
            .id(UUID1)
            .login("loginTest1")
            .password("passwordTest1")
            .dateTime(NOW)
            .build(),
        UserLogin.builder()
            .id(UUID2)
            .login("loginTest2")
            .password("passwordTest2")
            .dateTime(NOW)
            .build(),
        UserLogin.builder()
            .id(UUID3)
            .login("loginTest3")
            .password("passwordTest3")
            .dateTime(NOW)
            .build(),
    };

    private static final UserOnlyLoginDto[] userOnlyLoginDtos = {
        UserOnlyLoginDto.builder()
            .id(UUID0)
            .login("loginTest0")
            .build(),
        UserOnlyLoginDto.builder()
            .id(UUID1)
            .login("loginTest1")
            .build(),
        UserOnlyLoginDto.builder()
            .id(UUID2)
            .login("loginTest2")
            .build(),
        UserOnlyLoginDto.builder()
            .id(UUID3)
            .login("loginTest3")
            .build(),
    };

    private static final Tag[] tags = {
        Tag.builder().id(SID0).tag("tagTest0").visible(false).dateTime(NOW).build(),
        Tag.builder().id(SID1).tag("tagTest1").visible(true).dateTime(NOW).build(),
        Tag.builder().id(SID2).tag("tagTest2").visible(false).dateTime(NOW).build(),
        Tag.builder().id(SID3).tag("tagTest3").visible(true).dateTime(NOW).build(),
    };

    private static final TagJdo[] TAG_JDOS = {
        TagJdo.builder().id(SID0).tag("tagTest0").visible(false).dateTime(NOW).build(),
        TagJdo.builder().id(SID1).tag("tagTest1").visible(true).dateTime(NOW).build(),
        TagJdo.builder().id(SID2).tag("tagTest2").visible(false).dateTime(NOW).build(),
        TagJdo.builder().id(SID3).tag("tagTest3").visible(true).dateTime(NOW).build(),
    };

    private static final NewsGroup[] newsGroups = {
        NewsGroup.builder()
            .id(UUID0)
            .dateTime(NOW)
            .group("groupTest0")
            .build(),
        NewsGroup.builder()
            .id(UUID1)
            .dateTime(NOW)
            .group("groupTest1")
            .build(),
        NewsGroup.builder()
            .id(UUID2)
            .dateTime(NOW)
            .group("groupTest2")
            .build(),
        NewsGroup.builder()
            .id(UUID3)
            .dateTime(NOW)
            .group("groupTest3")
            .build()
    };

    private static final NewsGroupJdo[] newsGroupJdos = {
            NewsGroupJdo.builder()
                    .id(UUID0)
                    .dateTime(NOW)
                    .group("groupTest0")
                    .build(),
            NewsGroupJdo.builder()
                    .id(UUID1)
                    .dateTime(NOW)
                    .group("groupTest1")
                    .build(),
            NewsGroupJdo.builder()
                    .id(UUID2)
                    .dateTime(NOW)
                    .group("groupTest2")
                    .build(),
            NewsGroupJdo.builder()
                    .id(UUID3)
                    .dateTime(NOW)
                    .group("groupTest3")
                    .build()
    };

    private static final Record[] records = {
        Record.builder()
            .id(UUID0)
            .createDateTime(NOW)
            .editDateTime(NOW)
            .index(13)
            .type(NewsEntryJdo.class.getSimpleName())
            .build(),
        Record.builder()
            .id(UUID1)
            .createDateTime(NOW)
            .editDateTime(NOW)
            .index(1)
            .type(NewsEntryJdo.class.getSimpleName())
            .build(),
        Record.builder()
            .id(UUID2)
            .createDateTime(NOW)
            .editDateTime(NOW)
            .index(2)
            .type(NewsEntryJdo.class.getSimpleName())
            .build(),
        Record.builder()
            .id(UUID3)
            .createDateTime(NOW)
            .editDateTime(NOW)
            .index(3)
            .type(NewsEntryJdo.class.getSimpleName())
            .build(),
        Record.builder()
            .id(UUID4)
            .createDateTime(NOW)
            .editDateTime(NOW)
            .index(4)
            .type(ArticleJdo.class.getSimpleName())
            .build(),
        Record.builder()
            .id(UUID5)
            .createDateTime(NOW)
            .editDateTime(NOW)
            .index(5)
            .type(ArticleJdo.class.getSimpleName())
            .build(),
        Record.builder()
            .id(UUID6)
            .createDateTime(NOW)
            .editDateTime(NOW)
            .index(6)
            .type(ArticleJdo.class.getSimpleName())
            .build(),
        Record.builder()
            .id(UUID7)
            .createDateTime(NOW)
            .editDateTime(NOW)
            .index(7)
            .type(ArticleJdo.class.getSimpleName())
            .build()
    };

    private static final RecordJdo[] RECORD_JDOS = {
        RecordJdo.builder()
            .id(UUID0)
            .createDateTime(NOW)
            .editDateTime(NOW)
            .index(13)
            .type(NewsEntryJdo.class.getSimpleName())
            .build(),
        RecordJdo.builder()
            .id(UUID1)
            .createDateTime(NOW)
            .editDateTime(NOW)
            .index(1)
            .type(NewsEntryJdo.class.getSimpleName())
            .build(),
        RecordJdo.builder()
            .id(UUID2)
            .createDateTime(NOW)
            .editDateTime(NOW)
            .index(2)
            .type(NewsEntryJdo.class.getSimpleName())
            .build(),
        RecordJdo.builder()
            .id(UUID3)
            .createDateTime(NOW)
            .editDateTime(NOW)
            .index(3)
            .type(NewsEntryJdo.class.getSimpleName())
            .build(),
        RecordJdo.builder()
            .id(UUID4)
            .createDateTime(NOW)
            .editDateTime(NOW)
            .index(4)
            .type(ArticleJdo.class.getSimpleName())
            .build(),
        RecordJdo.builder()
            .id(UUID5)
            .createDateTime(NOW)
            .editDateTime(NOW)
            .index(5)
            .type(ArticleJdo.class.getSimpleName())
            .build(),
        RecordJdo.builder()
            .id(UUID6)
            .createDateTime(NOW)
            .editDateTime(NOW)
            .index(6)
            .type(ArticleJdo.class.getSimpleName())
            .build(),
        RecordJdo.builder()
            .id(UUID7)
            .createDateTime(NOW)
            .editDateTime(NOW)
            .index(7)
            .type(ArticleJdo.class.getSimpleName())
            .build()
    };

    private static final NewsEntry[] newsEntries = {
        NewsEntry.builder()
            .id(UUID0)
            .dateTime(NOW)
            .title("titleTest0")
            .content("contentTest0")
            .build(),
        NewsEntry.builder()
            .id(UUID1)
            .dateTime(NOW)
            .title("titleTest1")
            .content("contentTest1")
            .build(),
        NewsEntry.builder()
            .id(UUID2)
            .dateTime(NOW)
            .title("titleTest2")
            .content("contentTest2")
            .build(),
        NewsEntry.builder()
            .id(UUID3)
            .dateTime(NOW)
            .title("titleTest3")
            .content("contentTest3")
            .build()
    };

    private static final NewsEntryJdo[] newsEntryJdos = {
        NewsEntryJdo.builder()
            .id(UUID0)
            .dateTime(NOW)
            .title("titleTest0")
            .content("contentTest0")
            .build(),
        NewsEntryJdo.builder()
            .id(UUID1)
            .dateTime(NOW)
            .title("titleTest1")
            .content("contentTest1")
            .build(),
        NewsEntryJdo.builder()
            .id(UUID2)
            .dateTime(NOW)
            .title("titleTest2")
            .content("contentTest2")
            .build(),
        NewsEntryJdo.builder()
            .id(UUID3)
            .dateTime(NOW)
            .title("titleTest3")
            .content("contentTest3")
            .build()
    };

    private static final Link[] links = {
        Link.builder()
            .id(UUID0)
            .dateTime(NOW)
            .visible(true)
            .link("linkTest0")
            .build(),
        Link.builder()
            .id(UUID1)
            .dateTime(NOW)
            .visible(true)
            .link("linkTest1")
            .build(),
        Link.builder()
            .id(UUID2)
            .dateTime(NOW)
            .visible(true)
            .link("linkTest2")
            .build(),
        Link.builder()
            .id(UUID3)
            .dateTime(NOW)
            .visible(true)
            .link("linkTest3")
            .build(),
        Link.builder()
            .id(UUID4)
            .dateTime(NOW)
            .visible(true)
            .link("linkTest4")
            .build(),
        Link.builder()
            .id(UUID5)
            .dateTime(NOW)
            .visible(true)
            .link("linkTest5")
            .build(),
        Link.builder()
            .id(UUID6)
            .dateTime(NOW)
            .visible(true)
            .link("linkTest6")
            .build(),
        Link.builder()
            .id(UUID7)
            .dateTime(NOW)
            .visible(true)
            .link("linkTest7")
            .build()
    };

    private static final LinkJdo[] linkJdos = {
        LinkJdo.builder()
            .id(UUID0)
            .dateTime(NOW)
            .visible(true)
            .link("linkTest0")
            .build(),
        LinkJdo.builder()
            .id(UUID1)
            .dateTime(NOW)
            .visible(true)
            .link("linkTest1")
            .build(),
        LinkJdo.builder()
            .id(UUID2)
            .dateTime(NOW)
            .visible(true)
            .link("linkTest2")
            .build(),
        LinkJdo.builder()
            .id(UUID3)
            .dateTime(NOW)
            .visible(true)
            .link("linkTest3")
            .build(),
        LinkJdo.builder()
            .id(UUID4)
            .dateTime(NOW)
            .visible(true)
            .link("linkTest4")
            .build(),
        LinkJdo.builder()
            .id(UUID5)
            .dateTime(NOW)
            .visible(true)
            .link("linkTest5")
            .build(),
        LinkJdo.builder()
            .id(UUID6)
            .dateTime(NOW)
            .visible(true)
            .link("linkTest6")
            .build(),
        LinkJdo.builder()
            .id(UUID7)
            .dateTime(NOW)
            .visible(true)
            .link("linkTest7")
            .build()
    };

    private static final Article[] articles = {
        Article.builder()
            .id(UUID0)
            .dateTime(NOW)
            .title("titleTest0")
            .include("includeTest0")
            .summary("summaryTest0")
            .build(),
        Article.builder()
            .id(UUID1)
            .dateTime(NOW)
            .title("titleTest1")
            .include("includeTest1")
            .summary("summaryTest1")
            .build(),
        Article.builder()
            .id(UUID2)
            .dateTime(NOW)
            .title("titleTest2")
            .include("includeTest2")
            .summary("summaryTest2")
            .build(),
        Article.builder()
            .id(UUID3)
            .dateTime(NOW)
            .title("titleTest3")
            .include("includeTest3")
            .summary("summaryTest3")
            .build(),
        Article.builder()
            .id(UUID4)
            .dateTime(NOW)
            .title("titleTest4")
            .include("includeTest4")
            .summary("summaryTest4")
            .build(),
        Article.builder()
            .id(UUID5)
            .dateTime(NOW)
            .title("titleTest5")
            .include("includeTest5")
            .summary("summaryTest5")
            .build(),
        Article.builder()
            .id(UUID6)
            .dateTime(NOW)
            .title("titleTest6")
            .include("includeTest6")
            .summary("summaryTest6")
            .build(),
        Article.builder()
            .id(UUID7)
            .dateTime(NOW)
            .title("titleTest7")
            .include("includeTest7")
            .summary("summaryTest7")
            .build()
    };

    private static final ArticleJdo[] articleJdos = {
        ArticleJdo.builder()
            .id(UUID0)
            .dateTime(NOW)
            .title("titleTest0")
            .include("includeTest0")
            .summary("summaryTest0")
            .build(),
        ArticleJdo.builder()
            .id(UUID1)
            .dateTime(NOW)
            .title("titleTest1")
            .include("includeTest1")
            .summary("summaryTest1")
            .build(),
        ArticleJdo.builder()
            .id(UUID2)
            .dateTime(NOW)
            .title("titleTest2")
            .include("includeTest2")
            .summary("summaryTest2")
            .build(),
        ArticleJdo.builder()
            .id(UUID3)
            .dateTime(NOW)
            .title("titleTest3")
            .include("includeTest3")
            .summary("summaryTest3")
            .build(),
        ArticleJdo.builder()
            .id(UUID4)
            .dateTime(NOW)
            .title("titleTest4")
            .include("includeTest4")
            .summary("summaryTest4")
            .build(),
        ArticleJdo.builder()
            .id(UUID5)
            .dateTime(NOW)
            .title("titleTest5")
            .include("includeTest5")
            .summary("summaryTest5")
            .build(),
        ArticleJdo.builder()
            .id(UUID6)
            .dateTime(NOW)
            .title("titleTest6")
            .include("includeTest6")
            .summary("summaryTest6")
            .build(),
        ArticleJdo.builder()
            .id(UUID7)
            .dateTime(NOW)
            .title("titleTest7")
            .include("includeTest7")
            .summary("summaryTest7")
            .build()
    };


    static {
        newsGroups[0].setNewsEntries(new ArrayList<>());
        for (int i = 0; i < UPPER_BOUND; ++i) {
            userRoles[i].setRole(roles[i]);
            userRoles[i].setUserLogin(userLogins[i]);
            USER_ROLE_JDOS[i].setRole(roleBaseDtos[i]);
            USER_ROLE_JDOS[i].setUserLogin(userOnlyLoginDtos[i]);
            userLogins[i].setRoles(newList(userRoles[i]));
            // tags[i].setRecords(newSet());
            newsGroups[0].getNewsEntries().add(newsEntries[i]);
            records[i].setUserLogin(userLogins[i]);
            records[i].setNewsEntry(newsEntries[i]);
            RECORD_JDOS[i].setUserLogin(userOnlyLoginDtos[i]);
            RECORD_JDOS[i].setNewsEntry(newsEntryJdos[i]);
            RECORD_JDOS[i].setTags(newSet(TAG_JDOS[i]));
            newsEntries[i].setNewsGroup(newsGroups[0]);
            newsEntries[i].setRecord(records[i]);
            newsEntryJdos[i].setRecord(RECORD_JDOS[i]);
            newsEntryJdos[i].setNewsGroup(newsGroupJdos[i]);
        }
        for (int i = 4; i < UPPER_BOUND + 4; ++i) {
            records[i].setArticle(articles[i]);
            articles[i].setRecord(records[i]);
            articles[i].setLink(links[i]);
            links[i].setArticle(articles[i]);
            articleJdos[i].setRecord(RECORD_JDOS[i]);
            RECORD_JDOS[i].setUserLogin(userOnlyLoginDtos[i - 4]);
            RECORD_JDOS[i].setArticle(articleJdos[i]);
        }
    }

    public static Role cloneRole(int i)
    {
        Role role = clone(roles[i]);
        assert role != null;
        return role;
    }

    public static RoleJdo cloneRoleFullDto(int i)
    {
        RoleJdo role = clone(roleBaseDtos[i]);
        assert role != null;
        return role;
    }

    public static UserRole cloneUserRole(int i)
    {
        UserRole role = clone(userRoles[i]);
        assert role != null;
        return role;
    }

    public static UserRole clean(UserRole role)
    {
        role.setUserLogin(null);
        return role;
    }

    public static UserRoleJdo cloneUserRoleFullDto(int i)
    {
        UserRoleJdo role = clone(USER_ROLE_JDOS[i]);
        assert role != null;
        return role;
    }

    public static UserRoleJdo clean(UserRoleJdo role)
    {
        role.setUserLogin(null);
        return role;
    }

    public static UserLogin cloneUserLogin(int i)
    {
        UserLogin login = clone(userLogins[i]);
        assert login != null;
        return login;
    }

    public static UserLogin clean(UserLogin login)
    {
        login.setRoles(Collections.emptyList());
        return login;
    }

    public static Tag cloneTag(int i)
    {
        Tag tag = clone(tags[i]);
        assert tag != null;
        return tag;
    }

    public static Tag clean(Tag tag)
    {
        tag.setRecords(Collections.emptySet());
        return tag;
    }

    public static TagJdo cloneTagFullDto(int i)
    {
        TagJdo tag = clone(TAG_JDOS[i]);
        assert tag != null;
        return tag;
    }

    public static NewsGroup cloneNewsGroup(int i)
    {
        NewsGroup entity = clone(newsGroups[i]);
        assert entity != null;
        return entity;
    }

    public static NewsGroup clean(NewsGroup entity)
    {
        entity.setNewsEntries(Collections.emptyList());
        return entity;
    }

    public static Record cloneRecord(int i)
    {
        Record entity = clone(records[i]);
        assert entity != null;
        return entity;
    }

    public static Record clean(Record entity)
    {
        entity.setUserLogin(null);
        entity.setNewsEntry(null);
        entity.setTags(Collections.emptySet());
        return entity;
    }

    public static RecordJdo cloneRecordFullDto(int i)
    {
        RecordJdo dto = clone(RECORD_JDOS[i]);
        assert dto != null;
        return dto;
    }

    public static RecordJdo clean(RecordJdo dto)
    {
        if (dto.getNewsEntry() instanceof NewsEntryJdo) {
            ((NewsEntryJdo) dto.getNewsEntry()).setRecord(null);
        }
        dto.setTags(Collections.emptySet());
        return dto;
    }

    public static NewsEntry cloneNewsEntry(int i)
    {
        NewsEntry entity = clone(newsEntries[i]);
        assert entity != null;
        return entity;
    }

    public static NewsEntry clean(NewsEntry entity)
    {
        entity.setNewsGroup(null);
        return entity;
    }

    public static NewsEntryJdo cloneNewsEntryFullDto(int i)
    {
        NewsEntryJdo dto = clone(newsEntryJdos[i]);
        assert dto != null;
        return dto;
    }


    public static Link cloneLink(int i)
    {
        Link entity = clone(links[i]);
        assert entity != null;
        return entity;
    }

    public static Link clean(Link entity)
    {
        entity.setArticle(null);
        return entity;
    }

    public static LinkJdo cloneLinkJdo(int i)
    {
        LinkJdo dto = clone(linkJdos[i]);
        assert dto != null;
        return dto;
    }


    public static Article cloneArticle(int i)
    {
        Article entity = clone(articles[i]);
        assert entity != null;
        return entity;
    }

    public static Article clean(Article entity)
    {
        // entity.setLink(null);
        return entity;
    }

    public static ArticleJdo cloneArticleJdo(int i)
    {
        ArticleJdo dto = clone(articleJdos[i]);
        assert dto != null;
        return dto;
    }
}
