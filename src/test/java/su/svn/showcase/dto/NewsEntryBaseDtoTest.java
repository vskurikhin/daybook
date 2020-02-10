/*
 * This file was last modified at  by Victor N. Skurikhin.
 */

package su.svn.showcase.dto;

import org.junit.jupiter.api.*;
import su.svn.showcase.domain.*;
import su.svn.utils.TestData;
import su.svn.utils.ValidateUtil;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static su.svn.showcase.domain.TestData.getRecord0;
import static su.svn.showcase.domain.UUIDEntity.ZERO;
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
            assertNotNull(newsEntryBaseDto.getId());
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
            assertFalse(ValidateUtil.isNull(2, newsEntryBaseDto).hasNext());
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
            record = getRecord0();
            recordFullDto = getRecordFullDto0();
            newsGroupBaseDto = getNewsGroupBaseDto0();
            tags = TestData.EMPTY_BASEDTO_TAGS;
            newsEntryBaseDto = getNewsEntryBaseDto0();
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
            NewsEntry expected = new NewsEntry();
            expected.setId(ZERO);
            expected.setDateTime(NOW);
            expected.setRecord(record);
            expected.setTitle("titleTest0");
            expected.setContent("contentTest0");
            assertEquals(expected, newsEntryBaseDto.update(expected));
        }

        @Test
        @DisplayName("Instantiated DTO by entity")
        void instantiatedEntity() {
            NewsEntry entity = new NewsEntry();
            entity.setId(ZERO);
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
