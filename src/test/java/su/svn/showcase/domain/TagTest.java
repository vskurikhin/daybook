/*
 * This file was last modified at 2020.02.06 22:29 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * TagTest.java
 * $Id$
 */

package su.svn.showcase.domain;

import org.junit.jupiter.api.*;
import su.svn.utils.ValidateUtil;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static su.svn.utils.TestData.EMPTY_RECORDS;
import static su.svn.utils.TestData.NOW;

@DisplayName("Class Tag")
class TagTest {

    private Tag tag;

    @Test
    @DisplayName("is instantiated")
    void isInstantiatedWithNew() {
        new Tag();
    }

    @Nested
    @DisplayName("when new with empty constructor")
    class WhenNew {
        @BeforeEach
        void createNew() {
            tag = new Tag();
        }

        @Test
        @DisplayName("default values")
        void defaults() {
            assertNotNull(tag.getId());
            assertNotEquals(StringEntity.ZERO, tag.getId());
            assertThat(tag).hasFieldOrPropertyWithValue("tag", null);
            assertThat(tag).hasFieldOrPropertyWithValue("visible", null);
            assertThat(tag).hasFieldOrPropertyWithValue("dateTime", null);
        }

        @Test
        @DisplayName("Setters and getters")
        void testSettersAndGetters () {
            tag.setTag("testTag");
            assertThat(tag).hasFieldOrPropertyWithValue("tag", "testTag");
            assertEquals("testTag", tag.getTag());

            tag.setVisible(true);
            assertThat(tag).hasFieldOrPropertyWithValue("visible", true);
            assertEquals(true, tag.getVisible());


            tag.setDateTime(NOW);
            assertThat(tag).hasFieldOrPropertyWithValue("dateTime", NOW);
            assertEquals(NOW, tag.getDateTime());
        }

        @Test
        @DisplayName("violation on code is null")
        void codeIsNull() {
            assertFalse(ValidateUtil.isNull(2, tag).hasNext());
        }
    }

    @Nested
    @DisplayName("when new with all args constructor")
    class WhenNewAllArgsConstructor {
        @BeforeEach
        void createNew() {
            tag = new Tag(StringEntity.ZERO, "testTag", true, NOW, EMPTY_RECORDS);
        }

        @Test
        @DisplayName("is instantiated partial constructor")
        void isInstantiatedWithNew() {
            tag = new Tag("testTag", true, NOW, EMPTY_RECORDS);
            assertThat(tag).hasFieldOrPropertyWithValue("tag", "testTag");
            assertThat(tag).hasFieldOrPropertyWithValue("visible", true);
            assertThat(tag).hasFieldOrPropertyWithValue("dateTime", NOW);
            assertThat(tag).hasFieldOrPropertyWithValue("records", EMPTY_RECORDS);
        }

        @Test
        @DisplayName("is instantiated with builder")
        void isInstantiatedWithBuilder() {
            tag = Tag.builder()
                    .id(StringEntity.ZERO)
                    .tag("testTag")
                    .visible(true)
                    .dateTime(NOW)
                    .records(EMPTY_RECORDS)
                    .build();
            assertThat(tag).hasFieldOrPropertyWithValue("id", StringEntity.ZERO);
            assertThat(tag).hasFieldOrPropertyWithValue("tag", "testTag");
            assertThat(tag).hasFieldOrPropertyWithValue("visible", true);
            assertThat(tag).hasFieldOrPropertyWithValue("dateTime", NOW);
            assertThat(tag).hasFieldOrPropertyWithValue("records", EMPTY_RECORDS);
        }

        @Test
        @DisplayName("Equals and hashCode")
        void testEqualsAndHashCode() {
            assertNotEquals(new Tag(), tag);
            Tag expected = new Tag(StringEntity.ZERO, "testTag", true, NOW, EMPTY_RECORDS);
            assertEquals(expected.hashCode(), tag.hashCode());
            assertEquals(expected, tag);
        }

        @Test
        @DisplayName("The length of string from toString is great than zero")
        void testToString() {
            assertTrue(tag.toString().length() > 0);
        }
    }
}
//EOF
