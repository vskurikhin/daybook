/*
 * This file was last modified at 2020.03.16 17:13 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * LinkDescriptionTest.java
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
import static su.svn.showcase.domain.TestData.cloneLink0;
import static su.svn.showcase.domain.TestData.cloneNewsLinks0;
import static su.svn.utils.TestData.NOW;

@DisplayName("Class LinkDescription")
class LinkDescriptionTest {

    private Link link;
    private NewsLinks newsLinks;
    private LinkDescription linkDescription;

    @BeforeEach
    void createNew() {
        link = cloneLink0();
        newsLinks = cloneNewsLinks0();
        linkDescription = new LinkDescription();
    }

    @Test
    @DisplayName("is instantiated")
    void isInstantiatedWithNew() {
        new LinkDescription();
    }

    @Nested
    @DisplayName("when new with empty constructor")
    class WhenNew {

        @Test
        @DisplayName("default values")
        void defaults() {
            assertThat(linkDescription).hasFieldOrPropertyWithValue("id", null);
            assertThat(linkDescription).hasFieldOrPropertyWithValue("newsLinks", null);
            assertThat(linkDescription).hasFieldOrPropertyWithValue("link", null);
            assertThat(linkDescription).hasFieldOrPropertyWithValue("dateTime", null);
            assertThat(linkDescription).hasFieldOrPropertyWithValue("description", null);
            assertThat(linkDescription).hasFieldOrPropertyWithValue("details", null);
        }

        @Test
        @DisplayName("Setters and getters")
        void testSettersAndGetters () {
            linkDescription.setNewsLinks(newsLinks);
            assertThat(linkDescription).hasFieldOrPropertyWithValue("newsLinks", newsLinks);
            assertEquals(newsLinks, linkDescription.getNewsLinks());

            linkDescription.setLink(link);
            assertThat(linkDescription).hasFieldOrPropertyWithValue("link", link);
            assertEquals(link, linkDescription.getLink());

            linkDescription.setDateTime(NOW);
            assertThat(linkDescription).hasFieldOrPropertyWithValue("dateTime", NOW);
            assertEquals(NOW, linkDescription.getDateTime());

            linkDescription.setDescription("testDescription");
            assertThat(linkDescription).hasFieldOrPropertyWithValue("description", "testDescription");
            assertEquals("testDescription", linkDescription.getDescription());

            linkDescription.setDetails("testDetails");
            assertThat(linkDescription).hasFieldOrPropertyWithValue("details", "testDetails");
            assertEquals("testDetails", linkDescription.getDetails());
        }

        @Test
        @DisplayName("violation on code is null")
        void codeIsNull() {
            assertFalse(ValidateUtil.isNull(4, linkDescription).hasNext());
        }
    }

    @Nested
    @DisplayName("when new with all args constructor")
    class WhenNewAllArgsConstructor {
        @BeforeEach
        void createNew() {
            linkDescription = new LinkDescription(ZERO, newsLinks, link, NOW, "testDescription", "testDetails");
        }

        @Test
        @DisplayName("is instantiated partial constructor")
        void isInstantiatedWithNew() {
            linkDescription = new LinkDescription(ZERO, newsLinks, link, NOW, "testDescription", "testDetails");
            assertThat(linkDescription).hasFieldOrPropertyWithValue("id", ZERO);
            assertThat(linkDescription).hasFieldOrPropertyWithValue("newsLinks", newsLinks);
            assertThat(linkDescription).hasFieldOrPropertyWithValue("link", link);
            assertThat(linkDescription).hasFieldOrPropertyWithValue("dateTime", NOW);
            assertThat(linkDescription).hasFieldOrPropertyWithValue("description", "testDescription");
            assertThat(linkDescription).hasFieldOrPropertyWithValue("details", "testDetails");
        }

        @Test
        @DisplayName("is instantiated with builder")
        void isInstantiatedWithBuilder() {
            linkDescription = LinkDescription.builder()
                    .id(ZERO)
                    .newsLinks(newsLinks)
                    .link(link)
                    .dateTime(NOW)
                    .description("testDescription")
                    .details("testDetails")
                    .build();
            assertThat(linkDescription).hasFieldOrPropertyWithValue("id", ZERO);
            assertThat(linkDescription).hasFieldOrPropertyWithValue("newsLinks", newsLinks);
            assertThat(linkDescription).hasFieldOrPropertyWithValue("link", link);
            assertThat(linkDescription).hasFieldOrPropertyWithValue("dateTime", NOW);
            assertThat(linkDescription).hasFieldOrPropertyWithValue("description", "testDescription");
            assertThat(linkDescription).hasFieldOrPropertyWithValue("details", "testDetails");
        }

        @Test
        @DisplayName("Equals and hashCode")
        void testEqualsAndHashCode() {
            assertNotEquals(new LinkDescription(), linkDescription);
            LinkDescription expected = new LinkDescription(ZERO, newsLinks, link, NOW, "testDescription", "testDetails");
            assertEquals(expected.hashCode(), linkDescription.hashCode());
            assertEquals(expected, linkDescription);
        }

        @Test
        @DisplayName("The length of string from toString is great than zero")
        void testToString() {
            assertTrue(linkDescription.toString().length() > 0);
        }
    }
}
//EOF
