/*
 * This file was last modified at 2020.04.12 13:16 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * LinkJdoTest.java
 * $Id$
 */

package su.svn.showcase.dto.jdo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import su.svn.showcase.domain.Article;
import su.svn.utils.ValidateUtil;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static su.svn.shared.Constants.Types.UUID.ZERO;
import static su.svn.showcase.domain.TestData.cloneArticle0;
import static su.svn.utils.TestData.*;

@DisplayName("Class LinkJdo")
class LinkJdoTest {

    private LinkJdo linkJdo;

    @Test
    @DisplayName("is instantiated")
    void isInstantiatedWithNew() {
        new LinkJdo();
    }

    @Nested
    @DisplayName("when new with empty constructor")
    class WhenNew {
        @BeforeEach
        void createNew() {
            linkJdo = new LinkJdo();
        }

        @Test
        @DisplayName("default values")
        void defaults() {
            assertThat(linkJdo).hasFieldOrPropertyWithValue("id", null);
            assertThat(linkJdo).hasFieldOrPropertyWithValue("link", null);
            assertThat(linkJdo).hasFieldOrPropertyWithValue("visible", null);
            assertThat(linkJdo).hasFieldOrPropertyWithValue("dateTime", null);
        }

        @Test
        @DisplayName("Setters and getters")
        void testSettersAndGetters () {
            linkJdo.setLink("testLink");
            assertThat(linkJdo).hasFieldOrPropertyWithValue("link", "testLink");
            assertEquals("testLink", linkJdo.getLink());

            linkJdo.setVisible(true);
            assertThat(linkJdo).hasFieldOrPropertyWithValue("visible", true);
            assertEquals(true, linkJdo.getVisible());

            linkJdo.setDateTime(NOW);
            assertThat(linkJdo).hasFieldOrPropertyWithValue("dateTime", NOW);
            assertEquals(NOW, linkJdo.getDateTime());
        }

        @Test
        @DisplayName("violation on code is null")
        void codeIsNull() {
            assertFalse(ValidateUtil.isNull(1, linkJdo).hasNext());
        }
    }

    @Nested
    @DisplayName("when new with all args constructor")
    class WhenNewAllArgsConstructor {
        @BeforeEach
        void createNew() {
            linkJdo = new LinkJdo(ZERO, null, NOW, true, "testLink", null);
        }

        @Test
        @DisplayName("is instantiated partial constructor")
        void isInstantiatedWithNew() {
            linkJdo = new LinkJdo(ZERO,null,  NOW, true, "testLink", null);
            assertThat(linkJdo).hasFieldOrPropertyWithValue("id", ZERO);
            assertThat(linkJdo).hasFieldOrPropertyWithValue("dateTime", NOW);
            assertThat(linkJdo).hasFieldOrPropertyWithValue("visible", true);
            assertThat(linkJdo).hasFieldOrPropertyWithValue("link", "testLink");
        }

        @Test
        @DisplayName("is instantiated with builder")
        void isInstantiatedWithBuilder() {
            linkJdo = LinkJdo.builder()
                    .id(ZERO)
                    .dateTime(NOW)
                    .visible(true)
                    .link("testLink")
                    .build();
            assertThat(linkJdo).hasFieldOrPropertyWithValue("id", ZERO);
            assertThat(linkJdo).hasFieldOrPropertyWithValue("dateTime", NOW);
            assertThat(linkJdo).hasFieldOrPropertyWithValue("visible", true);
            assertThat(linkJdo).hasFieldOrPropertyWithValue("link", "testLink");
        }

        @Test
        @DisplayName("Equals and hashCode")
        void testEqualsAndHashCode() {
            assertNotEquals(new LinkJdo(), linkJdo);
            LinkJdo expected = new LinkJdo(ZERO, null, NOW, true, "testLink", null);
            assertEquals(expected.hashCode(), linkJdo.hashCode());
            assertEquals(expected, linkJdo);
        }

        @Test
        @DisplayName("The length of string from toString is great than zero")
        void testToString() {
            assertTrue(linkJdo.toString().length() > 0);
        }
    }
}
//EOF
