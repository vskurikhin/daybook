/*
 * This file was last modified at 2020.04.14 21:45 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * ArticleJdoTest.java
 * $Id$
 */

package su.svn.showcase.dto.jdo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import su.svn.showcase.domain.Record;
import su.svn.utils.TestData;
import su.svn.utils.ValidateUtil;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static su.svn.shared.Constants.Types.UUID.ZERO;
import static su.svn.showcase.domain.TestData.cloneRecord0;
import static su.svn.showcase.dto.TestData.*;
import static su.svn.utils.TestData.NOW;

@DisplayName("Class ArticleFullDto")
class ArticleJdoTest {

    private ArticleJdo articleJdo;

    @Test
    @DisplayName("is instantiated")
    void isInstantiatedWithNew() {
        new ArticleJdo();
    }

    @Nested
    @DisplayName("when new with empty constructor")
    class WhenNew {
        @BeforeEach
        void createNew() {
            articleJdo = new ArticleJdo();
        }

        @Test
        @DisplayName("default values")
        void defaults() {
            assertThat(articleJdo).hasFieldOrPropertyWithValue("id", null);
            assertThat(articleJdo).hasFieldOrPropertyWithValue("dateTime", null);
            assertThat(articleJdo).hasFieldOrPropertyWithValue("title", null);
            assertThat(articleJdo).hasFieldOrPropertyWithValue("include", null);
            assertThat(articleJdo).hasFieldOrPropertyWithValue("anchor", null);
            assertThat(articleJdo).hasFieldOrPropertyWithValue("summary", null);
        }

        @Test
        @DisplayName("Setters and getters")
        void testSettersAndGetters () {
            articleJdo.setDateTime(NOW);
            assertThat(articleJdo).hasFieldOrPropertyWithValue("dateTime", NOW);
            assertEquals(NOW, articleJdo.getDateTime());

            articleJdo.setTitle("testTitle");
            assertThat(articleJdo).hasFieldOrPropertyWithValue("title", "testTitle");
            assertEquals("testTitle", articleJdo.getTitle());

            articleJdo.setInclude("testInclude");
            assertThat(articleJdo).hasFieldOrPropertyWithValue("include", "testInclude");
            assertEquals("testInclude", articleJdo.getInclude());

            articleJdo.setSummary("testSummary");
            assertThat(articleJdo).hasFieldOrPropertyWithValue("summary", "testSummary");
            assertEquals("testSummary", articleJdo.getSummary());
        }

        @Test
        @DisplayName("violation on code is null")
        void codeIsNull() {
            assertFalse(ValidateUtil.isNull(1, articleJdo).hasNext());
        }
    }

    @Nested
    @DisplayName("when new with all args constructor")
    class WhenNewAllArgsConstructor {

        Record record;
        RecordJdo recordJdo;

        NewsGroupJdo newsGroupJdo;

        Set<TagJdo> tags;

        @BeforeEach
        void createNew() {
            record = cloneRecord0();
            recordJdo = cloneRecordFullDto0();
            newsGroupJdo = cloneNewsGroupFullDto0();
            tags = TestData.EMPTY_FULLDTO_TAGS;
            articleJdo = cloneArticleJdo0();
        }

        @Test
        @DisplayName("is instantiated partial constructor")
        void isInstantiatedWithNew() {
            articleJdo = new ArticleJdo(ZERO, NOW,
                    "titleTest0", "titleInclude0", "titleAnchor0", "titleSummary0",
                    cloneRecordFullDto0(), cloneLinkFullDto0());
            assertThat(articleJdo).hasFieldOrPropertyWithValue("dateTime", NOW);
            assertThat(articleJdo).hasFieldOrPropertyWithValue("title", "titleTest0");
        }

        @Test
        @DisplayName("is instantiated with builder")
        void isInstantiatedWithBuilder() {
            articleJdo = ArticleJdo.builder()
                    .id(ZERO)
                    .dateTime(NOW)
                    .title("titleTest0")
                    .build();
            assertThat(articleJdo).hasFieldOrPropertyWithValue("id", ZERO);
            assertThat(articleJdo).hasFieldOrPropertyWithValue("dateTime", NOW);
            assertThat(articleJdo).hasFieldOrPropertyWithValue("title", "titleTest0");
        }

        @Test
        @DisplayName("Equals and hashCode")
        void testEqualsAndHashCode() {
            assertNotEquals(new ArticleJdo(), articleJdo);
            ArticleJdo expected = new ArticleJdo(ZERO, NOW,
                    "titleTest0", "titleInclude0", "titleAnchor0", "titleSummary0",
                    null, null);
            assertEquals(expected.hashCode(), articleJdo.hashCode());
            assertEquals(expected, articleJdo);
        }

        @Test
        @DisplayName("The length of string from toString is great than zero")
        void testToString() {
            assertTrue(articleJdo.toString().length() > 0);
        }
    }
}
//EOF
