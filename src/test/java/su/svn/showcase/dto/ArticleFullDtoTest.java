/*
 * This file was last modified at 2020.04.11 11:07 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * ArticleFullDtoTest.java
 * $Id$
 */

package su.svn.showcase.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import su.svn.showcase.domain.Article;
import su.svn.showcase.domain.Record;
import su.svn.utils.TestData;
import su.svn.utils.ValidateUtil;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static su.svn.shared.Constants.Types.UUID.ZERO;
import static su.svn.showcase.domain.TestData.cloneLink0;
import static su.svn.showcase.domain.TestData.cloneRecord0;
import static su.svn.showcase.dto.TestData.*;
import static su.svn.utils.TestData.NOW;

@DisplayName("Class ArticleFullDto")
class ArticleFullDtoTest {

    private ArticleFullDto articleFullDto;

    @Test
    @DisplayName("is instantiated")
    void isInstantiatedWithNew() {
        new ArticleFullDto();
    }

    @Nested
    @DisplayName("when new with empty constructor")
    class WhenNew {
        @BeforeEach
        void createNew() {
            articleFullDto = new ArticleFullDto();
        }

        @Test
        @DisplayName("default values")
        void defaults() {
            assertThat(articleFullDto).hasFieldOrPropertyWithValue("id", null);
            assertThat(articleFullDto).hasFieldOrPropertyWithValue("dateTime", null);
            assertThat(articleFullDto).hasFieldOrPropertyWithValue("title", null);
            assertThat(articleFullDto).hasFieldOrPropertyWithValue("include", null);
            assertThat(articleFullDto).hasFieldOrPropertyWithValue("anchor", null);
            assertThat(articleFullDto).hasFieldOrPropertyWithValue("summary", null);
        }

        @Test
        @DisplayName("Setters and getters")
        void testSettersAndGetters () {
            articleFullDto.setDateTime(NOW);
            assertThat(articleFullDto).hasFieldOrPropertyWithValue("dateTime", NOW);
            assertEquals(NOW, articleFullDto.getDateTime());

            articleFullDto.setTitle("testTitle");
            assertThat(articleFullDto).hasFieldOrPropertyWithValue("title", "testTitle");
            assertEquals("testTitle", articleFullDto.getTitle());

            articleFullDto.setInclude("testInclude");
            assertThat(articleFullDto).hasFieldOrPropertyWithValue("include", "testInclude");
            assertEquals("testInclude", articleFullDto.getInclude());

            articleFullDto.setSummary("testSummary");
            assertThat(articleFullDto).hasFieldOrPropertyWithValue("summary", "testSummary");
            assertEquals("testSummary", articleFullDto.getSummary());
        }

        @Test
        @DisplayName("violation on code is null")
        void codeIsNull() {
            assertFalse(ValidateUtil.isNull(1, articleFullDto).hasNext());
        }
    }

    @Nested
    @DisplayName("when new with all args constructor")
    class WhenNewAllArgsConstructor {

        Record record;
        RecordFullDto recordFullDto;

        NewsGroupFullDto newsGroupFullDto;

        Set<TagFullDto> tags;

        @BeforeEach
        void createNew() {
            record = cloneRecord0();
            recordFullDto = cloneRecordFullDto0();
            newsGroupFullDto = cloneNewsGroupFullDto0();
            tags = TestData.EMPTY_FULLDTO_TAGS;
            articleFullDto = cloneArticleFullDto0();
        }

        @Test
        @DisplayName("is instantiated partial constructor")
        void isInstantiatedWithNew() {
            articleFullDto = new ArticleFullDto(ZERO, NOW,
                    "titleTest0", "titleInclude0", "titleAnchor0", "titleSummary0",
                    cloneRecordFullDto0(), cloneLinkFullDto0());
            assertThat(articleFullDto).hasFieldOrPropertyWithValue("dateTime", NOW);
            assertThat(articleFullDto).hasFieldOrPropertyWithValue("title", "titleTest0");
        }

        @Test
        @DisplayName("is instantiated with builder")
        void isInstantiatedWithBuilder() {
            articleFullDto = ArticleFullDto.builder()
                    .id(ZERO)
                    .dateTime(NOW)
                    .title("titleTest0")
                    .build();
            assertThat(articleFullDto).hasFieldOrPropertyWithValue("id", ZERO);
            assertThat(articleFullDto).hasFieldOrPropertyWithValue("dateTime", NOW);
            assertThat(articleFullDto).hasFieldOrPropertyWithValue("title", "titleTest0");
        }

        @Test
        @DisplayName("Equals and hashCode")
        void testEqualsAndHashCode() {
            assertNotEquals(new ArticleFullDto(), articleFullDto);
            ArticleFullDto expected = new ArticleFullDto(ZERO, NOW,
                    "titleTest0", "titleInclude0", "titleAnchor0", "titleSummary0",
                    null, null);
            assertEquals(expected.hashCode(), articleFullDto.hashCode());
            assertEquals(expected, articleFullDto);
        }

        @Test
        @DisplayName("The length of string from toString is great than zero")
        void testToString() {
            assertTrue(articleFullDto.toString().length() > 0);
        }
    }
}
//EOF
