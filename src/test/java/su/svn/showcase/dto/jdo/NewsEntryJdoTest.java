/*
 * This file was last modified at 2020.04.14 16:50 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * NewsEntryJdoTest.java
 * $Id$
 */

package su.svn.showcase.dto.jdo;

import org.junit.jupiter.api.*;
import su.svn.utils.ValidateUtil;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static su.svn.shared.Constants.Types.UUID.ZERO;
import static su.svn.utils.TestData.NOW;

@DisplayName("Class NewsEntryJdo")
class NewsEntryJdoTest {

    private NewsEntryJdo newsEntryJdo;

    @Test
    @DisplayName("is instantiated")
    void isInstantiatedWithNew() {
        new NewsEntryJdo();
    }

    @Nested
    @DisplayName("when new with empty constructor")
    class WhenNew {
        @BeforeEach
        void createNew() {
            newsEntryJdo = new NewsEntryJdo();
        }

        @Test
        @DisplayName("default values")
        void defaults() {
            assertThat(newsEntryJdo).hasFieldOrPropertyWithValue("id", null);
            assertThat(newsEntryJdo).hasFieldOrPropertyWithValue("dateTime", null);
            assertThat(newsEntryJdo).hasFieldOrPropertyWithValue("title", null);
            assertThat(newsEntryJdo).hasFieldOrPropertyWithValue("content", null);
        }

        @Test
        @DisplayName("Setters and getters")
        void testSettersAndGetters () {
            newsEntryJdo.setTitle("testTitle");
            assertThat(newsEntryJdo).hasFieldOrPropertyWithValue("title", "testTitle");
            assertEquals("testTitle", newsEntryJdo.getTitle());

            newsEntryJdo.setDateTime(NOW);
            assertThat(newsEntryJdo).hasFieldOrPropertyWithValue("dateTime", NOW);
            assertEquals(NOW, newsEntryJdo.getDateTime());

            newsEntryJdo.setContent("testContent");
            assertThat(newsEntryJdo).hasFieldOrPropertyWithValue("content", "testContent");
            assertEquals("testContent", newsEntryJdo.getContent());
        }

        @Test
        @DisplayName("violation on code is null")
        void codeIsNull() {
            assertFalse(ValidateUtil.isNull(1, newsEntryJdo).hasNext());
        }
    }

    @Nested
    @DisplayName("when new with all args constructor")
    class WhenNewAllArgsConstructor {

        @BeforeEach
        void createNew() {
            newsEntryJdo = new NewsEntryJdo(ZERO, NOW, "titleTest0", "contentTest0", null, null);
        }

        @Test
        @DisplayName("is instantiated partial constructor")
        void isInstantiatedWithNew() {
            newsEntryJdo = new NewsEntryJdo(ZERO, NOW, "titleTest0", "contentTest0", null, null);
            assertThat(newsEntryJdo).hasFieldOrPropertyWithValue("dateTime", NOW);
            assertThat(newsEntryJdo).hasFieldOrPropertyWithValue("title", "titleTest0");
            assertThat(newsEntryJdo).hasFieldOrPropertyWithValue("content", "contentTest0");
        }

        @Test
        @DisplayName("is instantiated with builder")
        void isInstantiatedWithBuilder() {
            newsEntryJdo = NewsEntryJdo.builder()
                    .id(ZERO)
                    .dateTime(NOW)
                    .title("titleTest0")
                    .content("contentTest0")
                    .build();
            assertThat(newsEntryJdo).hasFieldOrPropertyWithValue("id", ZERO);
            assertThat(newsEntryJdo).hasFieldOrPropertyWithValue("dateTime", NOW);
            assertThat(newsEntryJdo).hasFieldOrPropertyWithValue("title", "titleTest0");
            assertThat(newsEntryJdo).hasFieldOrPropertyWithValue("content", "contentTest0");
        }

        @Test
        @DisplayName("Equals and hashCode")
        void testEqualsAndHashCode() {
            assertNotEquals(new NewsEntryJdo(), newsEntryJdo);
            NewsEntryJdo expected = new NewsEntryJdo(ZERO, NOW, "titleTest0", "contentTest0", null, null);
            assertEquals(expected.hashCode(), newsEntryJdo.hashCode());
            assertEquals(expected, newsEntryJdo);
        }

        @Test
        @DisplayName("The length of string from toString is great than zero")
        void testToString() {
            assertTrue(newsEntryJdo.toString().length() > 0);
        }
    }
}
//EOF
