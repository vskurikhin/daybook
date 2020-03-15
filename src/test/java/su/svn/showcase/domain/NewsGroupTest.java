/*
 * This file was last modified at 2020.03.15 12:34 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * NewsGroupTest.java
 * $Id$
 */

package su.svn.showcase.domain;

import org.junit.jupiter.api.*;
import su.svn.utils.ValidateUtil;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static su.svn.shared.Constants.Types.UUID.ZERO;
import static su.svn.utils.TestData.EMPTY_NEWS_ENTRIES;
import static su.svn.utils.TestData.NOW;

@DisplayName("Class NewsGroup")
class NewsGroupTest {

        private NewsGroup newsGroup;

        @Test
        @DisplayName("is instantiated")
        void isInstantiatedWithNew() {
                new NewsGroup();
        }

        @Nested
        @DisplayName("when new with empty constructor")
        class WhenNew {
                @BeforeEach
                void createNew() {
                        newsGroup = new NewsGroup();
                }

                @Test
                @DisplayName("default values")
                void defaults() {
                        assertThat(newsGroup).hasFieldOrPropertyWithValue("id", null);
                        assertThat(newsGroup).hasFieldOrPropertyWithValue("dateTime", null);
                        assertThat(newsGroup).hasFieldOrPropertyWithValue("group", null);
                        assertThat(newsGroup).hasFieldOrPropertyWithValue("newsEntries", null);
                }

                @Test
                @DisplayName("Setters and getters")
                void testSettersAndGetters () {
                        newsGroup.setDateTime(NOW);
                        assertThat(newsGroup).hasFieldOrPropertyWithValue("dateTime", NOW);
                        assertEquals(NOW, newsGroup.getDateTime());

                        newsGroup.setGroup("testGroup");
                        assertThat(newsGroup).hasFieldOrPropertyWithValue("group", "testGroup");
                        assertEquals("testGroup", newsGroup.getGroup());

                        newsGroup.setNewsEntries(EMPTY_NEWS_ENTRIES);
                        assertThat(newsGroup).hasFieldOrPropertyWithValue("newsEntries", EMPTY_NEWS_ENTRIES);
                        assertEquals(EMPTY_NEWS_ENTRIES, newsGroup.getNewsEntries());
                }

                @Test
                @DisplayName("violation on code is null")
                void codeIsNull() {
                        assertFalse(ValidateUtil.isNull(3, newsGroup).hasNext());
                }
        }

        @Nested
        @DisplayName("when new with all args constructor")
        class WhenNewAllArgsConstructor {

                @BeforeEach
                void createNew() {
                        newsGroup = new NewsGroup(ZERO, NOW, "testGroup", EMPTY_NEWS_ENTRIES);
                }

                @Test
                @DisplayName("is instantiated partial constructor")
                void isInstantiatedWithNew() {
                        newsGroup = new NewsGroup(ZERO, NOW, "testGroup", EMPTY_NEWS_ENTRIES);
                        assertThat(newsGroup).hasFieldOrPropertyWithValue("group", "testGroup");
                }

                @Test
                @DisplayName("is instantiated with builder")
                void isInstantiatedWithBuilder() {
                        newsGroup = NewsGroup.builder()
                                .id(ZERO)
                                .dateTime(NOW)
                                .group("testGroup")
                                .newsEntries(EMPTY_NEWS_ENTRIES).build();
                        assertThat(newsGroup).hasFieldOrPropertyWithValue("id", ZERO);
                        assertThat(newsGroup).hasFieldOrPropertyWithValue("dateTime", NOW);
                        assertThat(newsGroup).hasFieldOrPropertyWithValue("group", "testGroup");
                        assertThat(newsGroup).hasFieldOrPropertyWithValue("newsEntries", EMPTY_NEWS_ENTRIES);
                }

                @Test
                @DisplayName("Equals and hashCode")
                void testEqualsAndHashCode() {
                        assertNotEquals(new NewsGroup(), newsGroup);
                        NewsGroup expected = new NewsGroup(ZERO, NOW, "testGroup", EMPTY_NEWS_ENTRIES);
                        assertEquals(expected.hashCode(), newsGroup.hashCode());
                        assertEquals(expected, newsGroup);
                }

                @Test
                @DisplayName("The length of string from toString is great than zero")
                void testToString() {
                        assertTrue(newsGroup.toString().length() > 0);
                }
        }

}
//EOF
