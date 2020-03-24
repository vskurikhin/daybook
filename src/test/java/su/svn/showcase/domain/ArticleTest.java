/*
 * This file was last modified at 2020.03.15 16:05 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * ArticleTest.java
 * $Id$
 */

package su.svn.showcase.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import su.svn.utils.ValidateUtil;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static su.svn.shared.Constants.Types.UUID.ZERO;
import static su.svn.showcase.domain.TestData.*;
import static su.svn.utils.TestData.NOW;

@DisplayName("Class Article")
class ArticleTest
{

    private Record record;

    private Link link;

    private Article article;

    @Test
    @DisplayName("is instantiated")
    void isInstantiatedWithNew() {
        new Article();
    }

    @Nested
    @DisplayName("when new with empty constructor")
    class WhenNew {
        @BeforeEach
        void createNew() {
            link = cloneLink0();
            record = cloneRecord0();
            article = new Article();
        }

        @Test
        @DisplayName("default values")
        void defaults() {
            assertThat(article).hasFieldOrPropertyWithValue("id", null);
            assertThat(article).hasFieldOrPropertyWithValue("record", new Record());
            assertThat(article).hasFieldOrPropertyWithValue("link", null);
            assertThat(article).hasFieldOrPropertyWithValue("dateTime", null);
            assertThat(article).hasFieldOrPropertyWithValue("title", null);
            assertThat(article).hasFieldOrPropertyWithValue("include", null);
            assertThat(article).hasFieldOrPropertyWithValue("anchor", null);
            assertThat(article).hasFieldOrPropertyWithValue("summary", null);
        }

        @Test
        @DisplayName("Setters and getters")
        void testSettersAndGetters () {

            article.setRecord(record);
            assertThat(article).hasFieldOrPropertyWithValue("record", record);
            assertEquals(record, article.getRecord());

            article.setLink(link);
            assertThat(article).hasFieldOrPropertyWithValue("link", link);
            assertEquals(link, article.getLink());


            article.setDateTime(NOW);
            assertThat(article).hasFieldOrPropertyWithValue("dateTime", NOW);
            assertEquals(NOW, article.getDateTime());

            article.setTitle("testTitle");
            assertThat(article).hasFieldOrPropertyWithValue("title", "testTitle");
            assertEquals("testTitle", article.getTitle());

            article.setInclude("testInclude");
            assertThat(article).hasFieldOrPropertyWithValue("include", "testInclude");
            assertEquals("testInclude", article.getInclude());

            article.setAnchor("testAnchor");
            assertThat(article).hasFieldOrPropertyWithValue("anchor", "testAnchor");
            assertEquals("testAnchor", article.getAnchor());

            article.setSummary("testSummary");
            assertThat(article).hasFieldOrPropertyWithValue("summary", "testSummary");
            assertEquals("testSummary", article.getSummary());
       }

        @Test
        @DisplayName("violation on code is null")
        void codeIsNull() {
            assertFalse(ValidateUtil.isNull(6, article).hasNext());
        }
    }

    @Nested
    @DisplayName("when new with all args constructor")
    class WhenNewAllArgsConstructor {

        @BeforeEach
        void createNew() {
            link = cloneLink0();
            record = cloneRecord0();
            article = cloneArticle0();
        }

        @Test
        @DisplayName("is instantiated partial constructor")
        void isInstantiatedWithNew() {
            article = new Article(ZERO, record, link, NOW, "titleTest0", "testInclude0", "testAnchor", "testSummary0");
            assertNotNull(article.getId());
            assertEquals(ZERO, article.getId());
            assertThat(article).hasFieldOrPropertyWithValue("record", record);
            assertThat(article).hasFieldOrPropertyWithValue("link", link);
            assertThat(article).hasFieldOrPropertyWithValue("dateTime", NOW);
            assertThat(article).hasFieldOrPropertyWithValue("title", "titleTest0");
            assertThat(article).hasFieldOrPropertyWithValue("include", "testInclude0");
            assertThat(article).hasFieldOrPropertyWithValue("anchor", "testAnchor");
            assertThat(article).hasFieldOrPropertyWithValue("summary", "testSummary0");
        }

        @Test
        @DisplayName("is instantiated with builder")
        void isInstantiatedWithBuilder() {
            article = Article.builder()
                    .id(ZERO)
                    .record(record)
                    .link(link)
                    .dateTime(NOW)
                    .title("titleTest0")
                    .include("testInclude0")
                    .anchor("testAnchor")
                    .summary("testSummary0")
                    .build();
            assertThat(article).hasFieldOrPropertyWithValue("id", ZERO);
            assertThat(article).hasFieldOrPropertyWithValue("record", record);
            assertThat(article).hasFieldOrPropertyWithValue("link", link);
            assertThat(article).hasFieldOrPropertyWithValue("dateTime", NOW);
            assertThat(article).hasFieldOrPropertyWithValue("title", "titleTest0");
            assertThat(article).hasFieldOrPropertyWithValue("include", "testInclude0");
            assertThat(article).hasFieldOrPropertyWithValue("anchor", "testAnchor");
            assertThat(article).hasFieldOrPropertyWithValue("summary", "testSummary0");
        }

        @Test
        @DisplayName("Equals and hashCode")
        void testEqualsAndHashCode() {
            assertNotEquals(new Article(), article);
            Article expected = cloneArticle0();
            assertEquals(expected.hashCode(), article.hashCode());
            assertEquals(expected, article);
        }

        @Test
        @DisplayName("The length of string from toString is great than zero")
        void testToString() {
            assertTrue(article.toString().length() > 0);
        }
    }
}
//EOF
