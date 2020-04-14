/*
 * This file was last modified at 2020.04.14 21:03 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * TagJdoTest.java
 * $Id$
 */

package su.svn.showcase.dto.jdo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import su.svn.showcase.domain.Record;
import su.svn.showcase.domain.Tag;
import su.svn.showcase.dto.RecordDto;
import su.svn.showcase.dto.jdo.TagJdo;
import su.svn.utils.TestData;
import su.svn.utils.ValidateUtil;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static su.svn.shared.Constants.Types.String.ZERO;
import static su.svn.showcase.domain.TestData.cloneRecord0;
import static su.svn.showcase.dto.TestData.cloneRecordBaseDto0;
import static su.svn.utils.TestData.*;

@DisplayName("Class TagFullDto")
class TagJdoTest {

    private TagJdo tagJdo;

    @Test
    @DisplayName("is instantiated")
    void isInstantiatedWithNew() {
        new TagJdo();
    }

    @Nested
    @DisplayName("when new with empty constructor")
    class WhenNew {
        @BeforeEach
        void createNew() {
            tagJdo = new TagJdo();
        }

        @Test
        @DisplayName("default values")
        void defaults() {
            assertThat(tagJdo).hasFieldOrPropertyWithValue("records", null);
        }

        @Test
        @DisplayName("Setters and getters")
        void testSettersAndGetters () {
            tagJdo.setRecords(EMPTY_RECORD_DTOS);
            assertThat(tagJdo).hasFieldOrPropertyWithValue("records", TestData.EMPTY_RECORD_DTOS);
            assertEquals(EMPTY_RECORD_DTOS, tagJdo.getRecords());
        }

        @Test
        @DisplayName("violation on code is null")
        void codeIsNull() {
            assertFalse(ValidateUtil.isNull(1, tagJdo).hasNext());
        }
    }

    @Nested
    @DisplayName("when new with all args constructor")
    class WhenNewAllArgsConstructor {

        private Set<Record> records;
        private Set<RecordDto> recordDtos;

        @BeforeEach
        void createNew() {
            records = Collections.singleton(cloneRecord0());
            recordDtos = Collections.singleton(cloneRecordBaseDto0());
            tagJdo = new TagJdo(ZERO, "testTag", true, NOW, recordDtos);
        }

        @Test
        @DisplayName("initialized values")
        void defaults() {
            assertThat(tagJdo).hasFieldOrPropertyWithValue("records", recordDtos);
        }

        @Test
        @DisplayName("Equals and hashCode")
        void testEqualsAndHashCode() {
            assertNotEquals(new TagJdo(), tagJdo);
            TagJdo expected = new TagJdo(ZERO, "testTag", true, NOW, recordDtos);
            assertEquals(expected.hashCode(), tagJdo.hashCode());
            assertEquals(expected, tagJdo);
        }

        @Test
        @DisplayName("The length of string from toString is great than zero")
        void testToString() {
            assertTrue(tagJdo.toString().length() > 0);
        }

        @Test
        @DisplayName("Update entity by DTO")
        void update() {
            Tag expected1 = new Tag(ZERO, "testTag", true, NOW, records);
            Map<String, Object> values = new HashMap<String, Object>() {{
                put("records", records);
            }};
            assertEquals(expected1, tagJdo.update(new Tag(ZERO), values));

            Tag expected2 = new Tag(ZERO, "testTag", true, NOW, records);
            Tag updating2 = new Tag(ZERO, "testTag", true, NOW, records);
            Tag test2 = tagJdo.update(updating2);
            assertEquals(expected2, test2);
            // TODO assertEquals(expected2.getRecords(), test2.getRecords());
        }

        @Test
        @DisplayName("Instantiated DTO by entity")
        void instantiatedEntity() {
            Tag entity = new Tag(ZERO, "testTag", true, NOW, Collections.singleton(cloneRecord0()));
            TagJdo expected = new TagJdo(entity);
            assertEquals(expected, tagJdo);
        }
    }
}
//EOF
