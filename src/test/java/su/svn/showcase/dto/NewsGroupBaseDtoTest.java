/*
 * This file was last modified at 2020.03.15 12:34 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * NewsGroupBaseDtoTest.java
 * $Id$
 */

package su.svn.showcase.dto;

import org.junit.jupiter.api.*;
import su.svn.showcase.domain.NewsEntry;
import su.svn.showcase.domain.NewsGroup;
import su.svn.utils.ValidateUtil;

import java.time.LocalDateTime;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static su.svn.shared.Constants.Types.UUID.ZERO;
import static su.svn.utils.TestData.NEWS_GROUP_UUID0;

@DisplayName("Class NewsGroupShortDto")
class NewsGroupBaseDtoTest {
    private final static LocalDateTime NOW = LocalDateTime.now();

    private final static List<NewsEntry> EMPTY_NEWS_ENTRIES = Collections.emptyList();

    private NewsGroupBaseDto newsGroupBaseDto;

    @Test
    @DisplayName("is instantiated")
    void isInstantiatedWithNew() {
        new NewsGroupBaseDto();
    }

    @Nested
    @DisplayName("when new with empty constructor")
    class WhenNew {
        @BeforeEach
        void createNew() {
            newsGroupBaseDto = new NewsGroupBaseDto();
        }

        @Test
        @DisplayName("default values")
        void defaults() {
            assertThat(newsGroupBaseDto).hasFieldOrPropertyWithValue("id", null);
            assertThat(newsGroupBaseDto).hasFieldOrPropertyWithValue("dateTime", null);
            assertThat(newsGroupBaseDto).hasFieldOrPropertyWithValue("group", null);
        }

        @Test
        @DisplayName("Setters and getters")
        void testSettersAndGetters () {
            newsGroupBaseDto.setDateTime(NOW);
            assertThat(newsGroupBaseDto).hasFieldOrPropertyWithValue("dateTime", NOW);
            assertEquals(NOW, newsGroupBaseDto.getDateTime());

            newsGroupBaseDto.setGroup("testGroup");
            assertThat(newsGroupBaseDto).hasFieldOrPropertyWithValue("group", "testGroup");
            assertEquals("testGroup", newsGroupBaseDto.getGroup());
        }

        @Test
        @DisplayName("violation on code is null")
        void codeIsNull() {
            assertFalse(ValidateUtil.isNull(3, newsGroupBaseDto).hasNext());
        }
    }

    @Nested
    @DisplayName("when new with all args constructor")
    class WhenNewAllArgsConstructor {
        @BeforeEach
        void createNew() {
            newsGroupBaseDto = new NewsGroupBaseDto(ZERO, NOW, "testGroup");
        }

        @Test
        @DisplayName("is instantiated partial constructor")
        void isInstantiatedWithNew() {
            newsGroupBaseDto = new NewsGroupBaseDto(NEWS_GROUP_UUID0, NOW, "testGroup");
            assertThat(newsGroupBaseDto).hasFieldOrPropertyWithValue("id", ZERO);
            assertThat(newsGroupBaseDto).hasFieldOrPropertyWithValue("dateTime", NOW);
            assertThat(newsGroupBaseDto).hasFieldOrPropertyWithValue("group", "testGroup");
        }

        @Test
        @DisplayName("is instantiated with builder")
        void isInstantiatedWithBuilder() {
            newsGroupBaseDto = NewsGroupBaseDto.builder().id(ZERO).dateTime(NOW).group("testGroup").build();
            assertThat(newsGroupBaseDto).hasFieldOrPropertyWithValue("id", ZERO);
            assertThat(newsGroupBaseDto).hasFieldOrPropertyWithValue("dateTime", NOW);
            assertThat(newsGroupBaseDto).hasFieldOrPropertyWithValue("group", "testGroup");
        }

        @Test
        @DisplayName("initialized values")
        void defaults() {
            assertThat(newsGroupBaseDto).hasFieldOrPropertyWithValue("id", ZERO);
            assertThat(newsGroupBaseDto).hasFieldOrPropertyWithValue("dateTime", NOW);
            assertThat(newsGroupBaseDto).hasFieldOrPropertyWithValue("group", "testGroup");
        }

        @Test
        @DisplayName("Equals and hashCode")
        void testEqualsAndHashCode() {
            assertNotEquals(new NewsGroupBaseDto(), newsGroupBaseDto);
            NewsGroupBaseDto expected = new NewsGroupBaseDto(ZERO, NOW, "testGroup");
            assertEquals(expected.hashCode(), newsGroupBaseDto.hashCode());
            assertEquals(expected, newsGroupBaseDto);
        }

        @Test
        @DisplayName("The length of string from toString is great than zero")
        void testToString() {
            assertTrue(newsGroupBaseDto.toString().length() > 0);
        }

        @Test
        @DisplayName("Update entity by DTO")
        void update() {
            NewsGroup expected = new NewsGroup(ZERO, NOW, "testGroup", EMPTY_NEWS_ENTRIES);
            Map<String, Object> values = new HashMap<String, Object>() {{
                put("newsEntries", EMPTY_NEWS_ENTRIES);
            }};
            assertEquals(expected, newsGroupBaseDto.update(new NewsGroup(ZERO), values));
        }

        @Test
        @DisplayName("Instantiated DTO by entity")
        void instantiatedEntity() {
            NewsGroup entity = new NewsGroup(ZERO);
            entity.setDateTime(NOW);
            entity.setGroup("testGroup");
            NewsGroupBaseDto expected = new NewsGroupBaseDto(entity);
            assertEquals(expected, newsGroupBaseDto);
        }
    }
}
//EOF
