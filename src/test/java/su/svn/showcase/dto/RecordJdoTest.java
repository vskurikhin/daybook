/*
 * This file was last modified at 2020.04.14 21:45 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * RecordJdoTest.java
 * $Id$
 */

package su.svn.showcase.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import su.svn.showcase.dto.jdo.NewsEntryJdo;
import su.svn.showcase.dto.jdo.NewsLinksJdo;
import su.svn.showcase.dto.jdo.RecordJdo;
import su.svn.utils.ValidateUtil;

import java.util.Collections;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static su.svn.shared.Constants.Types.UUID.ZERO;
import static su.svn.showcase.dto.TestData.*;
import static su.svn.utils.TestData.NOW;
import static su.svn.utils.TestData.ROLE_UUID0;

@DisplayName("Class RecordFullDto")
class RecordJdoTest {

    private UserOnlyLoginDto userLoginDto;

    private NewsEntryJdo newsEntryDto;

    private NewsLinksJdo newsLinksJdo;

    private RecordJdo recordJdo;

    private ArticleDto articleDto;

    private Set<TagDto> tagDtos;

    @Test
    @DisplayName("is instantiated")
    void isInstantiatedWithNew() {
        new RecordJdo();
    }

    @Nested
    @DisplayName("when new with empty constructor")
    class WhenNew {
        @BeforeEach
        void createNew() {
            userLoginDto = cloneUserOnlyLoginBaseDto0();
            newsEntryDto = cloneNewsEntryJdo0();
            newsLinksJdo = cloneNewsLinksJdo0();
            articleDto = cloneArticleJdo0();
            recordJdo = new RecordJdo();
        }

        @Test
        @DisplayName("default values")
        void defaults() {
            assertThat(recordJdo).hasFieldOrPropertyWithValue("id", null);
            assertThat(recordJdo).hasFieldOrPropertyWithValue("createDateTime", null);
            assertThat(recordJdo).hasFieldOrPropertyWithValue("editDateTime", null);
            assertThat(recordJdo).hasFieldOrPropertyWithValue("index", null);
            assertThat(recordJdo).hasFieldOrPropertyWithValue("type", null);
            assertThat(recordJdo).hasFieldOrPropertyWithValue("userLogin", null);
            assertThat(recordJdo).hasFieldOrPropertyWithValue("newsEntry", null);
            assertThat(recordJdo).hasFieldOrPropertyWithValue("newsLinks", null);
            assertThat(recordJdo).hasFieldOrPropertyWithValue("tags", null);
        }

        @Test
        @DisplayName("Setters and getters")
        void testSettersAndGetters () {
            recordJdo.setCreateDateTime(NOW);
            assertThat(recordJdo).hasFieldOrPropertyWithValue("createDateTime", NOW);
            assertEquals(NOW, recordJdo.getCreateDateTime());

            recordJdo.setEditDateTime(NOW);
            assertThat(recordJdo).hasFieldOrPropertyWithValue("editDateTime", NOW);
            assertEquals(NOW, recordJdo.getEditDateTime());

            recordJdo.setIndex(13);
            assertThat(recordJdo).hasFieldOrPropertyWithValue("index", 13);
            assertEquals(13, recordJdo.getIndex());

            recordJdo.setType("testType");
            assertThat(recordJdo).hasFieldOrPropertyWithValue("type", "testType");
            assertEquals("testType", recordJdo.getType());

            recordJdo.setUserLogin(userLoginDto);
            assertThat(recordJdo).hasFieldOrPropertyWithValue("userLogin", userLoginDto);
            assertEquals(userLoginDto, recordJdo.getUserLogin());

            recordJdo.setNewsEntry(newsEntryDto);
            assertThat(recordJdo).hasFieldOrPropertyWithValue("newsEntry", newsEntryDto);
            assertEquals(newsEntryDto, recordJdo.getNewsEntry());

            recordJdo.setNewsLinks(newsLinksJdo);
            assertThat(recordJdo).hasFieldOrPropertyWithValue("newsLinks", newsLinksJdo);
            assertEquals(newsLinksJdo, recordJdo.getNewsLinks());
        }

        @Test
        @DisplayName("violation on code is null")
        void codeIsNull() {
            assertFalse(ValidateUtil.isNull(1, recordJdo).hasNext());
        }
    }

    @Nested
    @DisplayName("when new with all args constructor")
    class WhenNewAllArgsConstructor {

        @BeforeEach
        void createNew() {
            userLoginDto = cloneUserOnlyLoginBaseDto0();
            newsEntryDto = cloneNewsEntryJdo0();
            recordJdo = cloneRecordFullDto0();
            articleDto = cloneArticleJdo0();
            tagDtos = Collections.singleton(cloneTagBaseDto0());
        }

        @Test
        @DisplayName("is instantiated partial constructor")
        void isInstantiatedWithNew() {
            recordJdo = new RecordJdo(ROLE_UUID0,
                    NOW, NOW, 13, "testType", userLoginDto, newsEntryDto, newsLinksJdo, articleDto, tagDtos);
            assertThat(recordJdo).hasFieldOrPropertyWithValue("id", ZERO);
            assertThat(recordJdo).hasFieldOrPropertyWithValue("createDateTime", NOW);
            assertThat(recordJdo).hasFieldOrPropertyWithValue("editDateTime", NOW);
            assertThat(recordJdo).hasFieldOrPropertyWithValue("index", 13);
            assertThat(recordJdo).hasFieldOrPropertyWithValue("type", "testType");
            assertThat(recordJdo).hasFieldOrPropertyWithValue("userLogin", userLoginDto);
            assertThat(recordJdo).hasFieldOrPropertyWithValue("newsEntry", newsEntryDto);
            assertThat(recordJdo).hasFieldOrPropertyWithValue("newsLinks", newsLinksJdo);
            assertThat(recordJdo).hasFieldOrPropertyWithValue("tags", tagDtos);
        }

        @Test
        @DisplayName("is instantiated with builder")
        void isInstantiatedWithBuilder() {
            recordJdo = RecordJdo.builder()
                    .id(ZERO)
                    .createDateTime(NOW)
                    .editDateTime(NOW)
                    .index(13)
                    .type("testType")
                    .userLogin(userLoginDto)
                    .newsEntry(newsEntryDto)
                    .newsLinks(newsLinksJdo)
                    .tags(tagDtos)
                    .build();
            assertThat(recordJdo).hasFieldOrPropertyWithValue("id", ZERO);
            assertThat(recordJdo).hasFieldOrPropertyWithValue("createDateTime", NOW);
            assertThat(recordJdo).hasFieldOrPropertyWithValue("editDateTime", NOW);
            assertThat(recordJdo).hasFieldOrPropertyWithValue("index", 13);
            assertThat(recordJdo).hasFieldOrPropertyWithValue("type", "testType");
            assertThat(recordJdo).hasFieldOrPropertyWithValue("userLogin", userLoginDto);
            assertThat(recordJdo).hasFieldOrPropertyWithValue("newsEntry", newsEntryDto);
            assertThat(recordJdo).hasFieldOrPropertyWithValue("newsLinks", newsLinksJdo);
            assertThat(recordJdo).hasFieldOrPropertyWithValue("tags", tagDtos);
        }

        @Test
        @DisplayName("Equals and hashCode")
        void testEqualsAndHashCode() {
            assertNotEquals(new RecordJdo(), recordJdo);
            RecordJdo expected = cloneRecordFullDto0();
            assertEquals(expected.hashCode(), recordJdo.hashCode());
            assertEquals(expected, recordJdo);
        }

        @Test
        @DisplayName("The length of string from toString is great than zero")
        void testToString() {
            assertTrue(recordJdo.toString().length() > 0);
        }
    }
}
//EOF
