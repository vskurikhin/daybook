/*
 * This file was last modified at 2020.04.14 19:52 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * NewsLinksJdoTest.java
 * $Id$
 */

package su.svn.showcase.dto.jdo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import su.svn.showcase.domain.Record;
import su.svn.showcase.dto.RecordFullDto;
import su.svn.showcase.dto.TagBaseDto;
import su.svn.utils.TestData;
import su.svn.utils.ValidateUtil;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static su.svn.shared.Constants.Types.UUID.ZERO;
import static su.svn.showcase.domain.TestData.cloneRecord0;
import static su.svn.showcase.dto.TestData.*;
import static su.svn.utils.TestData.NOW;

@DisplayName("Class NewsLinksBaseDto")
class NewsLinksJdoTest {

    private su.svn.showcase.dto.jdo.NewsLinksJdo newsLinksJdo;

    @Test
    @DisplayName("is instantiated")
    void isInstantiatedWithNew() {
        new NewsLinksJdo();
    }

    @Nested
    @DisplayName("when new with empty constructor")
    class WhenNew {
        @BeforeEach
        void createNew() {
            newsLinksJdo = new su.svn.showcase.dto.jdo.NewsLinksJdo();
        }

        @Test
        @DisplayName("default values")
        void defaults() {
            assertThat(newsLinksJdo).hasFieldOrPropertyWithValue("id", null);
            assertThat(newsLinksJdo).hasFieldOrPropertyWithValue("dateTime", null);
            assertThat(newsLinksJdo).hasFieldOrPropertyWithValue("title", null);
        }

        @Test
        @DisplayName("Setters and getters")
        void testSettersAndGetters () {
            newsLinksJdo.setTitle("testTitle");
            assertThat(newsLinksJdo).hasFieldOrPropertyWithValue("title", "testTitle");
            assertEquals("testTitle", newsLinksJdo.getTitle());

            newsLinksJdo.setDateTime(NOW);
            assertThat(newsLinksJdo).hasFieldOrPropertyWithValue("dateTime", NOW);
            assertEquals(NOW, newsLinksJdo.getDateTime());
        }

        @Test
        @DisplayName("violation on code is null")
        void codeIsNull() {
            assertFalse(ValidateUtil.isNull(1, newsLinksJdo).hasNext());
        }
    }

    @Nested
    @DisplayName("when new with all args constructor")
    class WhenNewAllArgsConstructor {

        Record record;
        RecordFullDto recordFullDto;

        NewsGroupJdo newsGroupJdo;

        Set<TagBaseDto> tags;

        @BeforeEach
        void createNew() {
            record = cloneRecord0();
            recordFullDto = cloneRecordFullDto0();
            newsGroupJdo = cloneNewsGroupFullDto0();
            tags = TestData.EMPTY_BASEDTO_TAGS;
            newsLinksJdo = cloneNewsLinksJdo0();
        }

        @Test
        @DisplayName("is instantiated partial constructor")
        void isInstantiatedWithNew() {
            newsLinksJdo = new su.svn.showcase.dto.jdo.NewsLinksJdo(ZERO, NOW, "titleTest0", null, null, null);
            assertThat(newsLinksJdo).hasFieldOrPropertyWithValue("dateTime", NOW);
            assertThat(newsLinksJdo).hasFieldOrPropertyWithValue("title", "titleTest0");
        }

        @Test
        @DisplayName("is instantiated with builder")
        void isInstantiatedWithBuilder() {
            newsLinksJdo = su.svn.showcase.dto.jdo.NewsLinksJdo.builder()
                    .id(ZERO)
                    .dateTime(NOW)
                    .title("titleTest0")
                    .build();
            assertThat(newsLinksJdo).hasFieldOrPropertyWithValue("id", ZERO);
            assertThat(newsLinksJdo).hasFieldOrPropertyWithValue("dateTime", NOW);
            assertThat(newsLinksJdo).hasFieldOrPropertyWithValue("title", "titleTest0");
        }

        @Test
        @DisplayName("Equals and hashCode")
        void testEqualsAndHashCode() {
            assertNotEquals(new NewsLinksJdo(), newsLinksJdo);
            NewsLinksJdo expected = new NewsLinksJdo(ZERO, NOW, "titleTest0", null, null, null);
            assertEquals(expected.hashCode(), newsLinksJdo.hashCode());
            assertEquals(expected, newsLinksJdo);
        }

        @Test
        @DisplayName("The length of string from toString is great than zero")
        void testToString() {
            assertTrue(newsLinksJdo.toString().length() > 0);
        }
    }
}
//EOF
