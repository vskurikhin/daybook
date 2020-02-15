/*
 * This file was last modified at 2020.02.06 22:29 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * NewsEntryTest.java
 * $Id$
 */

package su.svn.showcase.domain;

import org.junit.jupiter.api.*;
import su.svn.utils.ValidateUtil;

import java.time.LocalDateTime;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static su.svn.shared.Constants.UUID.ZERO;

@DisplayName("Class NewsEntry")
class NewsEntryTest {

    private final static LocalDateTime NOW = LocalDateTime.now();

    private NewsEntry newsEntry;

    @Test
    @DisplayName("is instantiated")
    void isInstantiatedWithNew() {
        new NewsEntry();
    }

    @Nested
    @DisplayName("when new with empty constructor")
    class WhenNew {
        @BeforeEach
        void createNew() {
            newsEntry = new NewsEntry();
        }

        @Test
        @DisplayName("default values")
        void defaults() {
            assertThat(newsEntry).hasFieldOrPropertyWithValue("id", null);
            assertThat(newsEntry).hasFieldOrPropertyWithValue("dateTime", null);
            assertThat(newsEntry).hasFieldOrPropertyWithValue("title", null);
            assertThat(newsEntry).hasFieldOrPropertyWithValue("content", null);
            assertThat(newsEntry).hasFieldOrPropertyWithValue("newsGroup", null);
        }

        @Test
        @DisplayName("Setters and getters")
        void testSettersAndGetters () {
            newsEntry.setDateTime(NOW);
            assertThat(newsEntry).hasFieldOrPropertyWithValue("dateTime", NOW);
            assertEquals(NOW, newsEntry.getDateTime());

            newsEntry.setTitle("testTitle");
            assertThat(newsEntry).hasFieldOrPropertyWithValue("title", "testTitle");
            assertEquals("testTitle", newsEntry.getTitle());

            newsEntry.setContent("testContent");
            assertThat(newsEntry).hasFieldOrPropertyWithValue("content", "testContent");
            assertEquals("testContent", newsEntry.getContent());

            NewsGroup newsGroup = NewsGroup.builder().id(ZERO).group("testGroup").dateTime(NOW).build();

            newsEntry.setNewsGroup(newsGroup);
            assertThat(newsEntry).hasFieldOrPropertyWithValue("newsGroup", newsGroup);
            assertEquals(newsGroup, newsEntry.getNewsGroup());
       }

        @Test
        @DisplayName("violation on code is null")
        void codeIsNull() {
            assertFalse(ValidateUtil.isNull(3, newsEntry).hasNext());
        }
    }

    @Nested
    @DisplayName("when new with all args constructor")
    class WhenNewAllArgsConstructor {

        UserLogin userLogin;

        Record record;

        NewsGroup newsGroup;

        @BeforeEach
        void createNew() {
            userLogin = UserLogin.builder()
                    .id(ZERO)
                    .dateTime(NOW)
                    .login("testLogin")
                    .password("testPassword")
                    .roles(Collections.emptyList())
                    .build();
            record = Record.builder()
                    .id(ZERO)
                    .userLogin(userLogin)
                    .createDateTime(NOW)
                    .editDateTime(NOW)
                    .index(13)
                    .type("testType")
                    .build();
            newsGroup = NewsGroup.builder().id(ZERO).group("testGroup").dateTime(NOW).build();
            newsEntry = new NewsEntry(ZERO, record, NOW, "testTitle", "testContent", newsGroup);
            newsEntry.setRecord(record);
        }

        @Test
        @DisplayName("is instantiated partial constructor")
        void isInstantiatedWithNew() {
            newsEntry = new NewsEntry(ZERO, record, NOW, "testTitle", "testContent", newsGroup);
            assertNotNull(newsEntry.getId());
            assertEquals(ZERO, newsEntry.getId());
            assertThat(newsEntry).hasFieldOrPropertyWithValue("dateTime", NOW);
            assertThat(newsEntry).hasFieldOrPropertyWithValue("title", "testTitle");
            assertThat(newsEntry).hasFieldOrPropertyWithValue("content", "testContent");
            assertThat(newsEntry).hasFieldOrPropertyWithValue("newsGroup", newsGroup);
        }

        @Test
        @DisplayName("is instantiated with builder")
        void isInstantiatedWithBuilder() {
            newsEntry = NewsEntry.builder()
                    .id(ZERO)
                    .dateTime(NOW)
                    .title("testTitle")
                    .content("testContent")
                    .newsGroup(newsGroup)
                    .build();
            assertThat(newsEntry).hasFieldOrPropertyWithValue("id", ZERO);
            assertThat(newsEntry).hasFieldOrPropertyWithValue("dateTime", NOW);
            assertThat(newsEntry).hasFieldOrPropertyWithValue("title", "testTitle");
            assertThat(newsEntry).hasFieldOrPropertyWithValue("content", "testContent");
            assertThat(newsEntry).hasFieldOrPropertyWithValue("newsGroup", newsGroup);
        }

        @Test
        @DisplayName("Equals and hashCode")
        void testEqualsAndHashCode() {
            assertNotEquals(new NewsEntry(), newsEntry);
            NewsEntry expected = new NewsEntry(ZERO, record, NOW, "testTitle", "testContent", newsGroup);
            expected.setRecord(record);
            assertEquals(expected.hashCode(), newsEntry.hashCode());
            assertEquals(expected, newsEntry);
        }

        @Test
        @DisplayName("The length of string from toString is great than zero")
        void testToString() {
            assertTrue(newsEntry.toString().length() > 0);
        }
    }
}
//EOF
