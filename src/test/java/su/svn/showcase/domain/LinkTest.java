/*
 * This file was last modified at 2020.03.18 15:08 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * LinkTest.java
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
import static su.svn.showcase.domain.TestData.cloneArticle0;
import static su.svn.utils.TestData.*;

@DisplayName("Class Link")
class LinkTest {

    private Link link;
    private Article article;

    @Test
    @DisplayName("is instantiated")
    void isInstantiatedWithNew() {
        new Link();
    }

    @Nested
    @DisplayName("when new with empty constructor")
    class WhenNew {
        @BeforeEach
        void createNew() {
            link = new Link();
        }

        @Test
        @DisplayName("default values")
        void defaults() {
            assertThat(link).hasFieldOrPropertyWithValue("id", null);
            assertThat(link).hasFieldOrPropertyWithValue("dateTime", null);
            assertThat(link).hasFieldOrPropertyWithValue("visible", null);
            assertThat(link).hasFieldOrPropertyWithValue("link", null);
        }

        @Test
        @DisplayName("Setters and getters")
        void testSettersAndGetters () {
            link.setDateTime(NOW);
            assertThat(link).hasFieldOrPropertyWithValue("dateTime", NOW);
            assertEquals(NOW, link.getDateTime());

            link.setVisible(true);
            assertThat(link).hasFieldOrPropertyWithValue("visible", true);
            assertEquals(true, link.getVisible());

            link.setLink("testLink");
            assertThat(link).hasFieldOrPropertyWithValue("link", "testLink");
            assertEquals("testLink", link.getLink());
        }

        @Test
        @DisplayName("violation on code is null")
        void codeIsNull() {
            assertFalse(ValidateUtil.isNull(3, link).hasNext());
        }
    }

    @Nested
    @DisplayName("when new with all args constructor")
    class WhenNewAllArgsConstructor {
        @BeforeEach
        void createNew() {
            article = cloneArticle0();
            link = new Link(ZERO, article, NOW, true, "testLink", EMPTY_LINK_DESCRIPTIONS);
        }

        @Test
        @DisplayName("is instantiated partial constructor")
        void isInstantiatedWithNew() {
            link = new Link(ZERO, article, NOW, true, "testLink", EMPTY_LINK_DESCRIPTIONS);
            assertThat(link).hasFieldOrPropertyWithValue("id", ZERO);
            assertThat(link).hasFieldOrPropertyWithValue("visible", true);
            assertThat(link).hasFieldOrPropertyWithValue("dateTime", NOW);
            assertThat(link).hasFieldOrPropertyWithValue("link", "testLink");
            assertThat(link).hasFieldOrPropertyWithValue("descriptions", EMPTY_LINK_DESCRIPTIONS);
        }

        @Test
        @DisplayName("is instantiated with builder")
        void isInstantiatedWithBuilder() {
            link = Link.builder()
                    .id(ZERO)
                    .link("testLink")
                    .visible(true)
                    .dateTime(NOW)
                    .descriptions(EMPTY_LINK_DESCRIPTIONS)
                    .build();
            assertThat(link).hasFieldOrPropertyWithValue("id", ZERO);
            assertThat(link).hasFieldOrPropertyWithValue("dateTime", NOW);
            assertThat(link).hasFieldOrPropertyWithValue("visible", true);
            assertThat(link).hasFieldOrPropertyWithValue("link", "testLink");
            assertThat(link).hasFieldOrPropertyWithValue("descriptions", EMPTY_LINK_DESCRIPTIONS);
        }

        @Test
        @DisplayName("Equals and hashCode")
        void testEqualsAndHashCode() {
            assertNotEquals(new Link(), link);
            Link expected = new Link(ZERO, article, NOW, true, "testLink", EMPTY_LINK_DESCRIPTIONS);
            assertEquals(expected.hashCode(), link.hashCode());
            assertEquals(expected, link);
        }

        @Test
        @DisplayName("The length of string from toString is great than zero")
        void testToString() {
            assertTrue(link.toString().length() > 0);
        }
    }
}
//EOF
