/*
 * This file was last modified at 2020.02.12 21:19 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * RecordTest.java
 * $Id$
 */

package su.svn.showcase.domain;

import org.junit.jupiter.api.*;
import su.svn.utils.ValidateUtil;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static su.svn.shared.Constants.UUID.ZERO;
import static su.svn.showcase.domain.TestData.getNewsEntry0;
import static su.svn.showcase.domain.TestData.cloneUserLogin0;
import static su.svn.utils.TestData.EMPTY_TAGS;
import static su.svn.utils.TestData.NOW;

@DisplayName("Class Record")
class RecordTest {

    private Record record;

    @Test
    @DisplayName("is instantiated")
    void isInstantiatedWithNew() {
        new Record();
    }

    @Nested
    @DisplayName("when new with empty constructor")
    class WhenNew {

        UserLogin userLogin;

        NewsEntry newsEntry;

        @BeforeEach
        void createNew() {
            userLogin = cloneUserLogin0();
            newsEntry = getNewsEntry0();
            record = new Record();
        }

        @Test
        @DisplayName("default values")
        void defaults() {
            assertThat(record).hasFieldOrPropertyWithValue("id", null);
            assertThat(record).hasFieldOrPropertyWithValue("createDateTime", null);
            assertThat(record).hasFieldOrPropertyWithValue("editDateTime", null);
            assertThat(record).hasFieldOrPropertyWithValue("index", 0);
            assertThat(record).hasFieldOrPropertyWithValue("userLogin", null);
            assertThat(record).hasFieldOrPropertyWithValue("type", null);
            assertThat(record).hasFieldOrPropertyWithValue("newsEntry", null);
            assertThat(record).hasFieldOrPropertyWithValue("tags", null);
        }

        @Test
        @DisplayName("Setters and getters")
        void testSettersAndGetters () {
            record.setCreateDateTime(NOW);
            assertThat(record).hasFieldOrPropertyWithValue("createDateTime", NOW);
            assertEquals(NOW, record.getCreateDateTime());

            record.setEditDateTime(NOW);
            assertThat(record).hasFieldOrPropertyWithValue("editDateTime", NOW);
            assertEquals(NOW, record.getEditDateTime());

            record.setIndex(13);
            assertThat(record).hasFieldOrPropertyWithValue("index", 13);
            assertEquals(13, record.getIndex());

            record.setType("testType");
            assertThat(record).hasFieldOrPropertyWithValue("type", "testType");
            assertEquals("testType", record.getType());

            UserLogin userLogin = UserLogin.builder().id(ZERO).login("testLogin").dateTime(NOW).build();

            record.setUserLogin(userLogin);
            assertThat(record).hasFieldOrPropertyWithValue("userLogin", userLogin);
            assertEquals(userLogin, record.getUserLogin());

            record.setTags(EMPTY_TAGS);
            assertThat(record).hasFieldOrPropertyWithValue("tags", EMPTY_TAGS);
            assertEquals(EMPTY_TAGS, record.getTags());
        }

        @Test
        @DisplayName("violation on code is null")
        void codeIsNull() {
            assertFalse(ValidateUtil.isNull(5, record).hasNext());
        }

        @Test
        @DisplayName("The length of string from toString is great than zero")
        void testToString() {
            assertTrue(record.toString().length() > 0);
        }
    }

    @Nested
    @DisplayName("when new with all args constructor")
    class WhenNewAllArgsConstructor {

        UserLogin userLogin;

        NewsEntry newsEntry;

        @BeforeEach
        void setUp() {
            userLogin = cloneUserLogin0();
            newsEntry = getNewsEntry0();
        }

        @Test
        @DisplayName("is instantiated with builder")
        void isInstantiatedWithBuilder() {
            record = Record.builder()
                    .id(ZERO)
                    .createDateTime(NOW)
                    .editDateTime(NOW)
                    .index(13)
                    .type("testType")
                    .userLogin(userLogin)
                    .tags(EMPTY_TAGS)
                    .build();
            assertThat(record).hasFieldOrPropertyWithValue("id", ZERO);
            assertThat(record).hasFieldOrPropertyWithValue("createDateTime", NOW);
            assertThat(record).hasFieldOrPropertyWithValue("editDateTime", NOW);
            assertThat(record).hasFieldOrPropertyWithValue("index", 13);
            assertThat(record).hasFieldOrPropertyWithValue("type", "testType");
            assertThat(record).hasFieldOrPropertyWithValue("userLogin", userLogin);
            assertThat(record).hasFieldOrPropertyWithValue("tags", EMPTY_TAGS);
        }
    }
}
//EOF
