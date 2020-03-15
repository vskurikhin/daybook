/*
 * This file was last modified at 2020.03.15 16:05 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * NewsLinksTest.java
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
import static su.svn.utils.TestData.EMPTY_LINK_DESCRIPTIONS;
import static su.svn.utils.TestData.NOW;

@DisplayName("Class NewsLinks")
class NewsLinksTest {

    private Record record;

    private NewsGroup newsGroup;

    private NewsLinks newsLinks;

    @Test
    @DisplayName("is instantiated")
    void isInstantiatedWithNew() {
        new NewsLinks();
    }

    @Nested
    @DisplayName("when new with empty constructor")
    class WhenNew {
        @BeforeEach
        void createNew() {
            newsGroup = cloneNewsGroup0();
            newsLinks = new NewsLinks();
        }

        @Test
        @DisplayName("default values")
        void defaults() {
            assertThat(newsLinks).hasFieldOrPropertyWithValue("id", null);
            assertThat(newsLinks).hasFieldOrPropertyWithValue("record", new Record());
            assertThat(newsLinks).hasFieldOrPropertyWithValue("dateTime", null);
            assertThat(newsLinks).hasFieldOrPropertyWithValue("title", null);
            assertThat(newsLinks).hasFieldOrPropertyWithValue("newsGroup", null);
            assertThat(newsLinks).hasFieldOrPropertyWithValue("links", null);
        }

        @Test
        @DisplayName("Setters and getters")
        void testSettersAndGetters () {
            newsLinks.setDateTime(NOW);
            assertThat(newsLinks).hasFieldOrPropertyWithValue("dateTime", NOW);
            assertEquals(NOW, newsLinks.getDateTime());

            newsLinks.setTitle("testTitle");
            assertThat(newsLinks).hasFieldOrPropertyWithValue("title", "testTitle");
            assertEquals("testTitle", newsLinks.getTitle());

            newsLinks.setNewsGroup(newsGroup);
            assertThat(newsLinks).hasFieldOrPropertyWithValue("newsGroup", newsGroup);
            assertEquals(newsGroup, newsLinks.getNewsGroup());

            newsLinks.setLinks(EMPTY_LINK_DESCRIPTIONS);
            assertThat(newsLinks).hasFieldOrPropertyWithValue("links", EMPTY_LINK_DESCRIPTIONS);
            assertEquals(EMPTY_LINK_DESCRIPTIONS, newsLinks.getLinks());
       }

        @Test
        @DisplayName("violation on code is null")
        void codeIsNull() {
            assertFalse(ValidateUtil.isNull(3, newsLinks).hasNext());
        }
    }

    @Nested
    @DisplayName("when new with all args constructor")
    class WhenNewAllArgsConstructor {

        NewsGroup newsGroup;

        @BeforeEach
        void createNew() {
            record = cloneRecord0();
            newsGroup = cloneNewsGroup0();
            newsLinks = cloneNewsLinks0();
        }

        @Test
        @DisplayName("is instantiated partial constructor")
        void isInstantiatedWithNew() {
            newsLinks = new NewsLinks(ZERO, record, newsGroup, NOW, "titleTest0", EMPTY_LINK_DESCRIPTIONS);
            assertNotNull(newsLinks.getId());
            assertEquals(ZERO, newsLinks.getId());
            assertThat(newsLinks).hasFieldOrPropertyWithValue("record", record);
            assertThat(newsLinks).hasFieldOrPropertyWithValue("dateTime", NOW);
            assertThat(newsLinks).hasFieldOrPropertyWithValue("title", "titleTest0");
            assertThat(newsLinks).hasFieldOrPropertyWithValue("newsGroup", newsGroup);
            assertThat(newsLinks).hasFieldOrPropertyWithValue("links", EMPTY_LINK_DESCRIPTIONS);
        }

        @Test
        @DisplayName("is instantiated with builder")
        void isInstantiatedWithBuilder() {
            newsLinks = NewsLinks.builder()
                    .id(ZERO)
                    .record(record)
                    .dateTime(NOW)
                    .title("testTitle")
                    .newsGroup(newsGroup)
                    .links(EMPTY_LINK_DESCRIPTIONS)
                    .build();
            assertThat(newsLinks).hasFieldOrPropertyWithValue("id", ZERO);
            assertThat(newsLinks).hasFieldOrPropertyWithValue("record", record);
            assertThat(newsLinks).hasFieldOrPropertyWithValue("dateTime", NOW);
            assertThat(newsLinks).hasFieldOrPropertyWithValue("title", "testTitle");
            assertThat(newsLinks).hasFieldOrPropertyWithValue("newsGroup", newsGroup);
            assertThat(newsLinks).hasFieldOrPropertyWithValue("links", EMPTY_LINK_DESCRIPTIONS);
        }

        @Test
        @DisplayName("Equals and hashCode")
        void testEqualsAndHashCode() {
            assertNotEquals(new NewsLinks(), newsLinks);
            NewsLinks expected = cloneNewsLinks0();
            assertEquals(expected.hashCode(), newsLinks.hashCode());
            assertEquals(expected, newsLinks);
        }

        @Test
        @DisplayName("The length of string from toString is great than zero")
        void testToString() {
            assertTrue(newsLinks.toString().length() > 0);
        }
    }
}
//EOF
