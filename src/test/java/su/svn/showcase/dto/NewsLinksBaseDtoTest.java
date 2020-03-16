/*
 * This file was last modified at 2020.03.16 17:13 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * NewsLinksBaseDtoTest.java
 * $Id$
 */

package su.svn.showcase.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import su.svn.showcase.domain.NewsLinks;
import su.svn.showcase.domain.Record;
import su.svn.utils.TestData;
import su.svn.utils.ValidateUtil;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static su.svn.shared.Constants.Types.UUID.ZERO;
import static su.svn.showcase.domain.TestData.cloneRecord0;
import static su.svn.showcase.dto.TestData.*;
import static su.svn.utils.TestData.NOW;

@DisplayName("Class NewsLinksBaseDto")
class NewsLinksBaseDtoTest {

    private NewsLinksBaseDto newsEntryBaseDto;

    @Test
    @DisplayName("is instantiated")
    void isInstantiatedWithNew() {
        new NewsLinksBaseDto();
    }

    @Nested
    @DisplayName("when new with empty constructor")
    class WhenNew {
        @BeforeEach
        void createNew() {
            newsEntryBaseDto = new NewsLinksBaseDto();
        }

        @Test
        @DisplayName("default values")
        void defaults() {
            assertThat(newsEntryBaseDto).hasFieldOrPropertyWithValue("id", null);
            assertThat(newsEntryBaseDto).hasFieldOrPropertyWithValue("dateTime", null);
            assertThat(newsEntryBaseDto).hasFieldOrPropertyWithValue("title", null);
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
        }

        @Test
        @DisplayName("violation on code is null")
        void codeIsNull() {
            assertFalse(ValidateUtil.isNull(1, newsEntryBaseDto).hasNext());
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
            newsEntryBaseDto = cloneNewsLinksBaseDto0();
        }

        @Test
        @DisplayName("is instantiated partial constructor")
        void isInstantiatedWithNew() {
            newsEntryBaseDto = new NewsLinksBaseDto(ZERO, NOW, "titleTest0");
            assertThat(newsEntryBaseDto).hasFieldOrPropertyWithValue("dateTime", NOW);
            assertThat(newsEntryBaseDto).hasFieldOrPropertyWithValue("title", "titleTest0");
        }

        @Test
        @DisplayName("is instantiated with builder")
        void isInstantiatedWithBuilder() {
            newsEntryBaseDto = NewsLinksBaseDto.builder()
                    .id(ZERO)
                    .dateTime(NOW)
                    .title("titleTest0")
                    .build();
            assertThat(newsEntryBaseDto).hasFieldOrPropertyWithValue("id", ZERO);
            assertThat(newsEntryBaseDto).hasFieldOrPropertyWithValue("dateTime", NOW);
            assertThat(newsEntryBaseDto).hasFieldOrPropertyWithValue("title", "titleTest0");
        }

        @Test
        @DisplayName("Equals and hashCode")
        void testEqualsAndHashCode() {
            assertNotEquals(new NewsLinksBaseDto(), newsEntryBaseDto);
            NewsLinksBaseDto expected = new NewsLinksBaseDto(ZERO, NOW, "titleTest0");
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
            NewsLinks expected = new NewsLinks(ZERO);
            expected.setDateTime(NOW);
            expected.setRecord(record);
            expected.setTitle("titleTest0");
            assertEquals(expected, newsEntryBaseDto.update(expected));
        }

        @Test
        @DisplayName("Instantiated DTO by entity")
        void instantiatedEntity() {
            NewsLinks entity = new NewsLinks(ZERO);
            entity.setDateTime(NOW);
            entity.setTitle("testTitle");
            NewsLinksBaseDto expected = new NewsLinksBaseDto(ZERO, NOW, "testTitle");
            NewsLinksBaseDto test = new NewsLinksBaseDto(entity);
            assertEquals(expected, test);
        }
    }
}
//EOF
