/*
 * This file was last modified at  by Victor N. Skurikhin.
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
import static su.svn.showcase.domain.UUIDEntity.ZERO;

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
            assertNotNull(newsGroupBaseDto.getId());
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
            assertFalse(ValidateUtil.isNull(2, newsGroupBaseDto).hasNext());
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
            newsGroupBaseDto = new NewsGroupBaseDto(NOW, "testGroup");
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
            assertEquals(expected, newsGroupBaseDto.update(new NewsGroup(), values));
        }

        @Test
        @DisplayName("Instantiated DTO by entity")
        void instantiatedEntity() {
            NewsGroup entity = new NewsGroup();
            entity.setId(ZERO);
            entity.setDateTime(NOW);
            entity.setGroup("testGroup");
            NewsGroupBaseDto expected = new NewsGroupBaseDto(entity);
            assertEquals(expected, newsGroupBaseDto);
        }
    }
}
//EOF