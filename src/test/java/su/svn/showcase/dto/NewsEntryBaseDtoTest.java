/*
 * This file was last modified at 2020.02.21 22:20 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * NewsEntryBaseDtoTest.java
 * $Id$
 */

package su.svn.showcase.dto;

import org.junit.jupiter.api.*;
import su.svn.showcase.domain.*;
import su.svn.utils.TestData;
import su.svn.utils.ValidateUtil;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static su.svn.shared.Constants.UUID.ZERO;
import static su.svn.showcase.domain.TestData.cloneRecord0;
import static su.svn.showcase.dto.TestData.*;
import static su.svn.utils.TestData.NOW;

@DisplayName("Class NewsEntryBaseDto")
class NewsEntryBaseDtoTest {

    private NewsEntryBaseDto newsEntryBaseDto;

    @Test
    @DisplayName("is instantiated")
    void isInstantiatedWithNew() {
        new NewsEntryBaseDto();
    }

    @Nested
    @DisplayName("when new with empty constructor")
    class WhenNew {
        @BeforeEach
        void createNew() {
            newsEntryBaseDto = new NewsEntryBaseDto();
        }

        @Test
        @DisplayName("default values")
        void defaults() {
            assertThat(newsEntryBaseDto).hasFieldOrPropertyWithValue("id", null);
            assertThat(newsEntryBaseDto).hasFieldOrPropertyWithValue("dateTime", null);
            assertThat(newsEntryBaseDto).hasFieldOrPropertyWithValue("title", null);
            assertThat(newsEntryBaseDto).hasFieldOrPropertyWithValue("content", null);
        }

        @Test
        @DisplayName("Setters and getters")
        void testSettersAndGetters () {
            newsEntryBaseDto.setTitle("testTitle");
            assertThat(newsEntryBaseDto).hasFieldOrPropertyWithValue("title", "testTitle");
            assertEquals("testTitle", newsEntryBaseDto.getTitle());

            newsEntryBaseDto.setDateTime(NOW);
            assertThat(newsEntryBaseDto).hasFieldOrPropertyWithValue("dateTime", NOW);
            assertEquals(NOW, newsEntryBaseDto.getDateTime());

            newsEntryBaseDto.setContent("testContent");
            assertThat(newsEntryBaseDto).hasFieldOrPropertyWithValue("content", "testContent");
            assertEquals("testContent", newsEntryBaseDto.getContent());
        }

        @Test
        @DisplayName("violation on code is null")
        void codeIsNull() {
            assertFalse(ValidateUtil.isNull(3, newsEntryBaseDto).hasNext());
        }
    }

    @Nested
    @DisplayName("when new with all args constructor")
    class WhenNewAllArgsConstructor {

        Record record;
        RecordFullDto recordFullDto;

        NewsGroupBaseDto newsGroupBaseDto;

        Set<TagBaseDto> tags;

        @BeforeEach
        void createNew() {
            record = cloneRecord0();
            recordFullDto = cloneRecordFullDto0();
            newsGroupBaseDto = cloneNewsGroupBaseDto0();
            tags = TestData.EMPTY_BASEDTO_TAGS;
            newsEntryBaseDto = cloneNewsEntryBaseDto0();
        }

        @Test
        @DisplayName("is instantiated partial constructor")
        void isInstantiatedWithNew() {
            newsEntryBaseDto = new NewsEntryBaseDto(ZERO, NOW, "titleTest0", "contentTest0");
            assertThat(newsEntryBaseDto).hasFieldOrPropertyWithValue("dateTime", NOW);
            assertThat(newsEntryBaseDto).hasFieldOrPropertyWithValue("title", "titleTest0");
            assertThat(newsEntryBaseDto).hasFieldOrPropertyWithValue("content", "contentTest0");
        }

        @Test
        @DisplayName("is instantiated with builder")
        void isInstantiatedWithBuilder() {
            newsEntryBaseDto = NewsEntryBaseDto.builder()
                    .id(ZERO)
                    .dateTime(NOW)
                    .title("titleTest0")
                    .content("contentTest0")
                    .build();
            assertThat(newsEntryBaseDto).hasFieldOrPropertyWithValue("id", ZERO);
            assertThat(newsEntryBaseDto).hasFieldOrPropertyWithValue("dateTime", NOW);
            assertThat(newsEntryBaseDto).hasFieldOrPropertyWithValue("title", "titleTest0");
            assertThat(newsEntryBaseDto).hasFieldOrPropertyWithValue("content", "contentTest0");
        }

        @Test
        @DisplayName("Equals and hashCode")
        void testEqualsAndHashCode() {
            assertNotEquals(new NewsEntryBaseDto(), newsEntryBaseDto);
            NewsEntryBaseDto expected = new NewsEntryBaseDto(ZERO, NOW, "titleTest0", "contentTest0");
            assertEquals(expected.hashCode(), newsEntryBaseDto.hashCode());
            assertEquals(expected, newsEntryBaseDto);
        }

        @Test
        @DisplayName("The length of string from toString is great than zero")
        void testToString() {
            assertTrue(newsEntryBaseDto.toString().length() > 0);
        }

        @Test
        @DisplayName("Update entity by DTO")
        void update() {
            NewsEntry expected = new NewsEntry(ZERO);
            expected.setDateTime(NOW);
            expected.setRecord(record);
            expected.setTitle("titleTest0");
            expected.setContent("contentTest0");
            assertEquals(expected, newsEntryBaseDto.update(expected));
        }

        @Test
        @DisplayName("Instantiated DTO by entity")
        void instantiatedEntity() {
            NewsEntry entity = new NewsEntry(ZERO);
            entity.setDateTime(NOW);
            entity.setTitle("testTitle");
            entity.setContent("testContent");
            NewsEntryBaseDto expected = new NewsEntryBaseDto(ZERO, NOW, "testTitle", "testContent");
            NewsEntryBaseDto test = new NewsEntryBaseDto(entity);
            assertEquals(expected, test);
        }
    }
}
//EOF
