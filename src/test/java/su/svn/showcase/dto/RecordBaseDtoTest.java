/*
 * This file was last modified at  by Victor N. Skurikhin.
 */

package su.svn.showcase.dto;

import org.junit.jupiter.api.*;
import su.svn.showcase.domain.Record;
import su.svn.utils.TestData;
import su.svn.utils.ValidateUtil;

import java.util.Collections;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static su.svn.showcase.domain.UUIDEntity.ZERO;
import static su.svn.showcase.dto.TestData.*;
import static su.svn.utils.TestData.EMPTY_TAGS;
import static su.svn.utils.TestData.NOW;

@DisplayName("Class RecordBaseDto")
class RecordBaseDtoTest {

    private RecordBaseDto recordBaseDto;

    @Test
    @DisplayName("is instantiated")
    void isInstantiatedWithNew() {
        new RecordBaseDto();
    }

    @Nested
    @DisplayName("when new with empty constructor")
    class WhenNew {
        @BeforeEach
        void createNew() {
            recordBaseDto = new RecordBaseDto();
        }

        @Test
        @DisplayName("default values")
        void defaults() {
            assertNotNull(recordBaseDto.getId());
            assertThat(recordBaseDto).hasFieldOrPropertyWithValue("createDateTime", null);
            assertThat(recordBaseDto).hasFieldOrPropertyWithValue("editDateTime", null);
            assertThat(recordBaseDto).hasFieldOrPropertyWithValue("index", null);
            assertThat(recordBaseDto).hasFieldOrPropertyWithValue("type", null);
        }

        @Test
        @DisplayName("Setters and getters")
        void testSettersAndGetters () {
            recordBaseDto.setCreateDateTime(NOW);
            assertThat(recordBaseDto).hasFieldOrPropertyWithValue("createDateTime", NOW);
            assertEquals(NOW, recordBaseDto.getCreateDateTime());

            recordBaseDto.setEditDateTime(NOW);
            assertThat(recordBaseDto).hasFieldOrPropertyWithValue("editDateTime", NOW);
            assertEquals(NOW, recordBaseDto.getEditDateTime());

            recordBaseDto.setIndex(13);
            assertThat(recordBaseDto).hasFieldOrPropertyWithValue("index", 13);
            assertEquals(13, recordBaseDto.getIndex());

            recordBaseDto.setType("testType");
            assertThat(recordBaseDto).hasFieldOrPropertyWithValue("type", "testType");
            assertEquals("testType", recordBaseDto.getType());
        }

        @Test
        @DisplayName("violation on code is null")
        void codeIsNull() {
            assertFalse(ValidateUtil.isNull(3, recordBaseDto).hasNext());
        }
    }

    @Nested
    @DisplayName("when new with all args constructor")
    class WhenNewAllArgsConstructor {

        NewsGroupBaseDto newsGroupBaseDto;

        Set<TagBaseDto> tagBaseDtos;

        @BeforeEach
        void createNew() {
            newsGroupBaseDto = getNewsGroupBaseDto0();
            recordBaseDto = getRecordBaseDto0();
            tagBaseDtos = Collections.singleton(getTagBaseDto0());
        }

        @Test
        @DisplayName("is instantiated partial constructor")
        void isInstantiatedWithNew() {
            recordBaseDto = new RecordBaseDto(NOW, NOW, 13, "testType");
            assertThat(recordBaseDto).hasFieldOrPropertyWithValue("createDateTime", NOW);
            assertThat(recordBaseDto).hasFieldOrPropertyWithValue("editDateTime", NOW);
            assertThat(recordBaseDto).hasFieldOrPropertyWithValue("index", 13);
            assertThat(recordBaseDto).hasFieldOrPropertyWithValue("type", "testType");
        }

        @Test
        @DisplayName("is instantiated with builder")
        void isInstantiatedWithBuilder() {
            recordBaseDto = RecordBaseDto.builder()
                    .id(ZERO)
                    .createDateTime(NOW)
                    .editDateTime(NOW)
                    .index(13)
                    .type("testType")
                    .build();
            assertThat(recordBaseDto).hasFieldOrPropertyWithValue("id", ZERO);
            assertThat(recordBaseDto).hasFieldOrPropertyWithValue("createDateTime", NOW);
            assertThat(recordBaseDto).hasFieldOrPropertyWithValue("editDateTime", NOW);
            assertThat(recordBaseDto).hasFieldOrPropertyWithValue("index", 13);
            assertThat(recordBaseDto).hasFieldOrPropertyWithValue("type", "testType");
        }

        @Test
        @DisplayName("initialized values")
        void defaults() {
            assertThat(recordBaseDto).hasFieldOrPropertyWithValue("id", ZERO);
            assertThat(recordBaseDto).hasFieldOrPropertyWithValue("createDateTime", NOW);
            assertThat(recordBaseDto).hasFieldOrPropertyWithValue("editDateTime", NOW);
            assertThat(recordBaseDto).hasFieldOrPropertyWithValue("index", 13);
            assertThat(recordBaseDto).hasFieldOrPropertyWithValue("type", "testType");
        }

        @Test
        @DisplayName("Equals and hashCode")
        void testEqualsAndHashCode() {
            assertNotEquals(new RecordBaseDto(), recordBaseDto);
            RecordBaseDto expected = new RecordBaseDto(ZERO, NOW, NOW, 13, "testType");
            assertEquals(expected.hashCode(), recordBaseDto.hashCode());
            assertEquals(expected, recordBaseDto);
        }

        @Test
        @DisplayName("The length of string from toString is great than zero")
        void testToString() {
            assertTrue(recordBaseDto.toString().length() > 0);
        }

        @Test
        @DisplayName("Update entity by DTO")
        void update() {
            Record expected = new Record();
            expected.setId(ZERO);
            expected.setCreateDateTime(NOW);
            expected.setEditDateTime(NOW);
            expected.setIndex(13);
            expected.setType("testType");
            expected.setTags(EMPTY_TAGS);
            assertEquals(expected, recordBaseDto.update(new Record()));
        }

        @Test
        @DisplayName("Instantiated DTO by entity")
        void instantiatedEntity() {
            Record entity = new Record();
            entity.setId(ZERO);
            entity.setCreateDateTime(NOW);
            entity.setEditDateTime(NOW);
            entity.setIndex(13);
            entity.setType("testType");
            entity.setTags(EMPTY_TAGS);
            RecordBaseDto expected = new RecordBaseDto(ZERO, NOW, NOW, 13, "testType");
            RecordBaseDto test = new RecordBaseDto(entity);
            assertEquals(expected, test);
        }
    }
}
//EOF
