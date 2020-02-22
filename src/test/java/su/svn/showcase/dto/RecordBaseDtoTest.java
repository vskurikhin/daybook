/*
 * This file was last modified at 2020.02.17 22:15 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * RecordBaseDtoTest.java
 * $Id$
 */

package su.svn.showcase.dto;

import org.junit.jupiter.api.*;
import su.svn.showcase.domain.Record;
import su.svn.utils.ValidateUtil;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static su.svn.shared.Constants.UUID.ZERO;
import static su.svn.showcase.domain.TestData.*;
import static su.svn.showcase.dto.TestData.*;
import static su.svn.utils.TestData.*;

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
;
        @Test
        @DisplayName("default values")
        void defaults() {
            assertThat(recordBaseDto).hasFieldOrPropertyWithValue("id", null);
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
            assertFalse(ValidateUtil.isNull(4, recordBaseDto).hasNext());
        }
    }

    @Nested
    @DisplayName("when new with all args constructor")
    class WhenNewAllArgsConstructor {

        NewsGroupBaseDto newsGroupBaseDto;

        Set<TagBaseDto> tagBaseDtos;

        @BeforeEach
        void createNew() {
            newsGroupBaseDto = cloneNewsGroupBaseDto0();
            recordBaseDto = cloneRecordBaseDto0();
            tagBaseDtos = Collections.singleton(cloneTagBaseDto0());
        }

        @Test
        @DisplayName("is instantiated partial constructor")
        void isInstantiatedWithNew() {
            recordBaseDto = new RecordBaseDto(ROLE_UUID0, NOW, NOW, 13, "testType");
            assertThat(recordBaseDto).hasFieldOrPropertyWithValue("id", ZERO);
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
        @DisplayName("Equals and hashCode")
        void testEqualsAndHashCode() {
            assertNotEquals(new RecordBaseDto(), recordBaseDto);
            RecordBaseDto expected = cloneRecordBaseDto0();
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
            Record expected = cloneRecord0();
            Map<String, Object> values = new HashMap<String, Object>() {{
                put("userLogin", cloneUserLogin0());
                put("newsEntry", clean(cloneNewsEntry0()));
            }};
            assertEquals(expected, recordBaseDto.update(new Record(ZERO), values));
        }

        @Test
        @DisplayName("Instantiated DTO by entity")
        void instantiatedEntity() {
            Record entity = clean(cloneRecord0());
            RecordBaseDto expected = cloneRecordBaseDto0();
            RecordBaseDto test = new RecordBaseDto(entity);
            assertEquals(expected, test);
        }
    }
}
//EOF
/*
expected: <
Record(id=00000000-0000-0000-0000-000000000000, createDateTime=2020-02-22T23:19:55.711634, editDateTime=2020-02-22T23:19:55.711634, index=13, type=NewsEntry, userLogin=UserLogin(id=00000000-0000-0000-0000-000000000000, dateTime=2020-02-22T23:19:55.711634, login=loginTest0, password=passwordTest0), newsEntry=NewsEntry(id=00000000-0000-0000-0000-000000000000, dateTime=2020-02-22T23:19:55.711634, title=titleTest0, content=contentTest0, newsGroup=null))> but was: <
Record(id=00000000-0000-0000-0000-000000000000, createDateTime=2020-02-22T23:19:55.711634, editDateTime=2020-02-22T23:19:55.711634, index=13, type=NewsEntry, userLogin=null, newsEntry=null)>

expected: <
Record(id=00000000-0000-0000-0000-000000000000, createDateTime=2020-02-22T23:27:23.595365, editDateTime=2020-02-22T23:27:23.595365, index=13, type=NewsEntry, userLogin=UserLogin(id=00000000-0000-0000-0000-000000000000, dateTime=2020-02-22T23:27:23.595365, login=loginTest0, password=passwordTest0), newsEntry=NewsEntry(id=00000000-0000-0000-0000-000000000000, dateTime=2020-02-22T23:27:23.595365, title=titleTest0, content=contentTest0, newsGroup=null))> but was: <
Record(id=00000000-0000-0000-0000-000000000000, createDateTime=2020-02-22T23:27:23.595365, editDateTime=2020-02-22T23:27:23.595365, index=13, type=NewsEntry, userLogin=UserLogin(id=00000000-0000-0000-0000-000000000000, dateTime=2020-02-22T23:27:23.595365, login=loginTest0, password=passwordTest0), newsEntry=NewsEntry(id=00000000-0000-0000-0000-000000000000, dateTime=2020-02-22T23:27:23.595365, title=titleTest0, content=contentTest0, newsGroup=NewsGroup(id=00000000-0000-0000-0000-000000000000, dateTime=2020-02-22T23:27:23.595365, group=groupTest0)))>

expected: <
Record(id=00000000-0000-0000-0000-000000000000, createDateTime=2020-02-22T23:29:17.369114, editDateTime=2020-02-22T23:29:17.369114, index=13, type=NewsEntry, userLogin=UserLogin(id=00000000-0000-0000-0000-000000000000, dateTime=2020-02-22T23:29:17.369114, login=loginTest0, password=passwordTest0), newsEntry=NewsEntry(id=00000000-0000-0000-0000-000000000000, dateTime=2020-02-22T23:29:17.369114, title=titleTest0, content=contentTest0, newsGroup=null))> but was: <
Record(id=00000000-0000-0000-0000-000000000000, createDateTime=2020-02-22T23:29:17.369114, editDateTime=2020-02-22T23:29:17.369114, index=13, type=NewsEntry, userLogin=UserLogin(id=00000000-0000-0000-0000-000000000000, dateTime=2020-02-22T23:29:17.369114, login=loginTest0, password=passwordTest0), newsEntry=NewsEntry(id=00000000-0000-0000-0000-000000000000, dateTime=2020-02-22T23:29:17.369114, title=titleTest0, content=contentTest0, newsGroup=NewsGroup(id=00000000-0000-0000-0000-000000000000, dateTime=2020-02-22T23:29:17.369114, group=groupTest0)))>

 */