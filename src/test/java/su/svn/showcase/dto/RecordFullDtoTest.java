/*
 * This file was last modified at 2020.03.09 16:35 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * RecordFullDtoTest.java
 * $Id$
 */

package su.svn.showcase.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import su.svn.showcase.domain.Record;
import su.svn.showcase.domain.Tag;
import su.svn.utils.ValidateUtil;

import java.util.Collections;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static su.svn.shared.Constants.UUID.ZERO;
import static su.svn.showcase.domain.TestData.clean;
import static su.svn.showcase.domain.TestData.cloneRecord0;
import static su.svn.showcase.dto.TestData.*;
import static su.svn.utils.TestData.NOW;
import static su.svn.utils.TestData.ROLE_UUID0;

@DisplayName("Class RecordFullDto")
class RecordFullDtoTest {

    private UserOnlyLoginBaseDto userLoginDto;

    private NewsEntryBaseDto newsEntryDto;

    private RecordFullDto recordFullDto;

    @Test
    @DisplayName("is instantiated")
    void isInstantiatedWithNew() {
        new RecordFullDto();
    }

    @Nested
    @DisplayName("when new with empty constructor")
    class WhenNew {
        @BeforeEach
        void createNew() {
            userLoginDto = cloneUserOnlyLoginBaseDto0();
            newsEntryDto = cloneNewsEntryBaseDto0();
            recordFullDto = new RecordFullDto();
        }

        @Test
        @DisplayName("default values")
        void defaults() {
            assertThat(recordFullDto).hasFieldOrPropertyWithValue("id", null);
            assertThat(recordFullDto).hasFieldOrPropertyWithValue("createDateTime", null);
            assertThat(recordFullDto).hasFieldOrPropertyWithValue("editDateTime", null);
            assertThat(recordFullDto).hasFieldOrPropertyWithValue("index", null);
            assertThat(recordFullDto).hasFieldOrPropertyWithValue("type", null);
            assertThat(recordFullDto).hasFieldOrPropertyWithValue("userLogin", null);
            assertThat(recordFullDto).hasFieldOrPropertyWithValue("newsEntry", null);
            assertThat(recordFullDto).hasFieldOrPropertyWithValue("tags", null);
        }

        @Test
        @DisplayName("Setters and getters")
        void testSettersAndGetters () {
            recordFullDto.setCreateDateTime(NOW);
            assertThat(recordFullDto).hasFieldOrPropertyWithValue("createDateTime", NOW);
            assertEquals(NOW, recordFullDto.getCreateDateTime());

            recordFullDto.setEditDateTime(NOW);
            assertThat(recordFullDto).hasFieldOrPropertyWithValue("editDateTime", NOW);
            assertEquals(NOW, recordFullDto.getEditDateTime());

            recordFullDto.setIndex(13);
            assertThat(recordFullDto).hasFieldOrPropertyWithValue("index", 13);
            assertEquals(13, recordFullDto.getIndex());

            recordFullDto.setType("testType");
            assertThat(recordFullDto).hasFieldOrPropertyWithValue("type", "testType");
            assertEquals("testType", recordFullDto.getType());

            recordFullDto.setUserLogin(userLoginDto);
            assertThat(recordFullDto).hasFieldOrPropertyWithValue("userLogin", userLoginDto);
            assertEquals(userLoginDto, recordFullDto.getUserLogin());

            recordFullDto.setNewsEntry(newsEntryDto);
            assertThat(recordFullDto).hasFieldOrPropertyWithValue("newsEntry", newsEntryDto);
            assertEquals(newsEntryDto, recordFullDto.getNewsEntry());
        }

        @Test
        @DisplayName("violation on code is null")
        void codeIsNull() {
            assertFalse(ValidateUtil.isNull(7, recordFullDto).hasNext());
        }
    }

    @Nested
    @DisplayName("when new with all args constructor")
    class WhenNewAllArgsConstructor {

        UserLoginDto userLoginDto;

        NewsEntryDto newsEntryDto;

        NewsGroupDto newsGroupDto;

        Set<Tag> tags;

        Set<TagDto> tagDtos;

        @BeforeEach
        void createNew() {
            userLoginDto = cloneUserOnlyLoginBaseDto0();
            newsEntryDto = cloneNewsEntryBaseDto0();
            newsGroupDto = cloneNewsGroupBaseDto0();
            recordFullDto = cloneRecordFullDto0();
            tagDtos = Collections.singleton(cloneTagBaseDto0());
        }

        @Test
        @DisplayName("is instantiated partial constructor")
        void isInstantiatedWithNew() {
            recordFullDto = new RecordFullDto(ROLE_UUID0, NOW, NOW, 13, "testType", userLoginDto, newsEntryDto, tagDtos);
            assertThat(recordFullDto).hasFieldOrPropertyWithValue("id", ZERO);
            assertThat(recordFullDto).hasFieldOrPropertyWithValue("createDateTime", NOW);
            assertThat(recordFullDto).hasFieldOrPropertyWithValue("editDateTime", NOW);
            assertThat(recordFullDto).hasFieldOrPropertyWithValue("index", 13);
            assertThat(recordFullDto).hasFieldOrPropertyWithValue("type", "testType");
            assertThat(recordFullDto).hasFieldOrPropertyWithValue("userLogin", userLoginDto);
            assertThat(recordFullDto).hasFieldOrPropertyWithValue("newsEntry", newsEntryDto);
            assertThat(recordFullDto).hasFieldOrPropertyWithValue("tags", tagDtos);
        }

        @Test
        @DisplayName("is instantiated with builder")
        void isInstantiatedWithBuilder() {
            recordFullDto = RecordFullDto.builder()
                    .id(ZERO)
                    .createDateTime(NOW)
                    .editDateTime(NOW)
                    .index(13)
                    .type("testType")
                    .userLogin(userLoginDto)
                    .newsEntry(newsEntryDto)
                    .tags(tagDtos)
                    .build();
            assertThat(recordFullDto).hasFieldOrPropertyWithValue("id", ZERO);
            assertThat(recordFullDto).hasFieldOrPropertyWithValue("createDateTime", NOW);
            assertThat(recordFullDto).hasFieldOrPropertyWithValue("editDateTime", NOW);
            assertThat(recordFullDto).hasFieldOrPropertyWithValue("index", 13);
            assertThat(recordFullDto).hasFieldOrPropertyWithValue("type", "testType");
            assertThat(recordFullDto).hasFieldOrPropertyWithValue("userLogin", userLoginDto);
            assertThat(recordFullDto).hasFieldOrPropertyWithValue("newsEntry", newsEntryDto);
            assertThat(recordFullDto).hasFieldOrPropertyWithValue("tags", tagDtos);
        }

        @Test
        @DisplayName("Equals and hashCode")
        void testEqualsAndHashCode() {
            assertNotEquals(new RecordFullDto(), recordFullDto);
            RecordFullDto expected = cloneRecordFullDto0();
            assertEquals(expected.hashCode(), recordFullDto.hashCode());
            assertEquals(expected, recordFullDto);
        }

        @Test
        @DisplayName("The length of string from toString is great than zero")
        void testToString() {
            assertTrue(recordFullDto.toString().length() > 0);
        }

        @Test
        @DisplayName("Update entity by DTO")
        void update() {
            Record expected = clean(cloneRecord0());
            RecordFullDto dto = su.svn.showcase.dto.TestData.clean(cloneRecordFullDto0());
            assertEquals(expected, dto.update(new Record(ZERO, expected.getUserLogin())));
        }

        @Test
        @DisplayName("Instantiated DTO by entity")
        void instantiatedEntity() {
            Record entity = cloneRecord0();
            RecordFullDto expected = cloneRecordFullDto0();
            RecordFullDto test = new RecordFullDto(entity);
            assertEquals(expected, test);
        }
    }
}
//EOF
