/*
 * This file was last modified at 2020.02.15 14:31 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * TagBaseDtoTest.java$
 * $Id$
 */

package su.svn.showcase.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import su.svn.shared.Constants;
import su.svn.showcase.domain.Record;
import su.svn.showcase.domain.Tag;

import java.util.*;
import su.svn.utils.ValidateUtil;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static su.svn.shared.Constants.String.ZERO;
import static su.svn.utils.TestData.*;

@DisplayName("Class TagBaseDto")
class TagBaseDtoTest {

    private TagBaseDto tagBaseDto;

    @Test
    @DisplayName("is instantiated")
    void isInstantiatedWithNew() {
        new TagBaseDto();
    }

    @Nested
    @DisplayName("when new with empty constructor")
    class WhenNew {
        @BeforeEach
        void createNew() {
            tagBaseDto = new TagBaseDto();
        }

        @Test
        @DisplayName("default values")
        void defaults() {
            assertThat(tagBaseDto).hasFieldOrPropertyWithValue("id", null);
            assertThat(tagBaseDto).hasFieldOrPropertyWithValue("tag", null);
            assertThat(tagBaseDto).hasFieldOrPropertyWithValue("visible", null);
            assertThat(tagBaseDto).hasFieldOrPropertyWithValue("dateTime", null);
        }

        @Test
        @DisplayName("Setters and getters")
        void testSettersAndGetters () {
            tagBaseDto.setTag("testTag");
            assertThat(tagBaseDto).hasFieldOrPropertyWithValue("tag", "testTag");
            assertEquals("testTag", tagBaseDto.getTag());

            tagBaseDto.setVisible(true);
            assertThat(tagBaseDto).hasFieldOrPropertyWithValue("visible", true);
            assertEquals(true, tagBaseDto.getVisible());

            tagBaseDto.setDateTime(NOW);
            assertThat(tagBaseDto).hasFieldOrPropertyWithValue("dateTime", NOW);
            assertEquals(NOW, tagBaseDto.getDateTime());
        }

        @Test
        @DisplayName("violation on code is null")
        void codeIsNull() {
            assertFalse(ValidateUtil.isNull(3, tagBaseDto).hasNext());
        }
    }

    @Nested
    @DisplayName("when new with all args constructor")
    class WhenNewAllArgsConstructor {
        @BeforeEach
        void createNew() {
            tagBaseDto = new TagBaseDto(ZERO, "testTag", true, NOW);
        }

        @Test
        @DisplayName("is instantiated partial constructor")
        void isInstantiatedWithNew() {
            tagBaseDto = new TagBaseDto(TAG_ID0, "testTag", true, NOW);
            assertThat(tagBaseDto).hasFieldOrPropertyWithValue("id", ZERO);
            assertThat(tagBaseDto).hasFieldOrPropertyWithValue("tag", "testTag");
            assertThat(tagBaseDto).hasFieldOrPropertyWithValue("visible", true);
            assertThat(tagBaseDto).hasFieldOrPropertyWithValue("dateTime", NOW);
        }

        @Test
        @DisplayName("is instantiated with builder")
        void isInstantiatedWithBuilder() {
            tagBaseDto = TagBaseDto.builder()
                    .id(ZERO)
                    .tag("testTag")
                    .visible(true)
                    .dateTime(NOW)
                    .build();
            assertThat(tagBaseDto).hasFieldOrPropertyWithValue("id", ZERO);
            assertThat(tagBaseDto).hasFieldOrPropertyWithValue("tag", "testTag");
            assertThat(tagBaseDto).hasFieldOrPropertyWithValue("visible", true);
            assertThat(tagBaseDto).hasFieldOrPropertyWithValue("dateTime", NOW);
        }

        @Test
        @DisplayName("Equals and hashCode")
        void testEqualsAndHashCode() {
            assertNotEquals(new TagBaseDto(), tagBaseDto);
            TagBaseDto expected = new TagBaseDto(ZERO, "testTag", true, NOW);
            assertEquals(expected.hashCode(), tagBaseDto.hashCode());
            assertEquals(expected, tagBaseDto);
        }

        @Test
        @DisplayName("The length of string from toString is great than zero")
        void testToString() {
            assertTrue(tagBaseDto.toString().length() > 0);
        }

        @Test
        @DisplayName("Update entity by DTO")
        void update() {
            Tag expected1 = new Tag(ZERO, "testTag", true, NOW, EMPTY_RECORDS);
            assertEquals(expected1, tagBaseDto.update(new Tag(ZERO)));

            Record record = new Record(Constants.UUID.ZERO);
            record.setCreateDateTime(NOW);
            record.setEditDateTime(NOW);
            Set<Record> records = Collections.singleton(record);
            Map<String, Object> values = new HashMap<String, Object>() {{
                put("records", records);
            }};

            Tag expected2 = new Tag(ZERO, "testTag", true, NOW, records);
            Tag test = tagBaseDto.update(new Tag(ZERO), values);
            assertEquals(expected2, test);
            assertEquals(expected2.getRecords(), test.getRecords());
        }

        @Test
        @DisplayName("Instantiated DTO by entity")
        void instantiatedEntity() {
            Tag entity = new Tag(ZERO, "testTag", true, NOW, null);
            TagBaseDto expected = new TagBaseDto(entity);
            assertEquals(expected, tagBaseDto);
        }
    }
}
//EOF
