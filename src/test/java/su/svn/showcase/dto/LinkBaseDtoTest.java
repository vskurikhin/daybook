/*
 * This file was last modified at 2020.03.16 17:13 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * LinkBaseDtoTest.java
 * $Id$
 */

package su.svn.showcase.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import su.svn.shared.Constants;
import su.svn.showcase.domain.LinkDescription;
import su.svn.showcase.domain.Link;
import su.svn.utils.ValidateUtil;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static su.svn.shared.Constants.Types.UUID.ZERO;
import static su.svn.utils.TestData.*;

@DisplayName("Class LinkBaseDto")
class LinkBaseDtoTest {

    private LinkBaseDto linkBaseDto;

    @Test
    @DisplayName("is instantiated")
    void isInstantiatedWithNew() {
        new LinkBaseDto();
    }

    @Nested
    @DisplayName("when new with empty constructor")
    class WhenNew {
        @BeforeEach
        void createNew() {
            linkBaseDto = new LinkBaseDto();
        }

        @Test
        @DisplayName("default values")
        void defaults() {
            assertThat(linkBaseDto).hasFieldOrPropertyWithValue("id", null);
            assertThat(linkBaseDto).hasFieldOrPropertyWithValue("link", null);
            assertThat(linkBaseDto).hasFieldOrPropertyWithValue("visible", null);
            assertThat(linkBaseDto).hasFieldOrPropertyWithValue("dateTime", null);
        }

        @Test
        @DisplayName("Setters and getters")
        void testSettersAndGetters () {
            linkBaseDto.setLink("testLink");
            assertThat(linkBaseDto).hasFieldOrPropertyWithValue("link", "testLink");
            assertEquals("testLink", linkBaseDto.getLink());

            linkBaseDto.setVisible(true);
            assertThat(linkBaseDto).hasFieldOrPropertyWithValue("visible", true);
            assertEquals(true, linkBaseDto.getVisible());

            linkBaseDto.setDateTime(NOW);
            assertThat(linkBaseDto).hasFieldOrPropertyWithValue("dateTime", NOW);
            assertEquals(NOW, linkBaseDto.getDateTime());
        }

        @Test
        @DisplayName("violation on code is null")
        void codeIsNull() {
            assertFalse(ValidateUtil.isNull(1, linkBaseDto).hasNext());
        }
    }

    @Nested
    @DisplayName("when new with all args constructor")
    class WhenNewAllArgsConstructor {
        @BeforeEach
        void createNew() {
            linkBaseDto = new LinkBaseDto(ZERO, NOW, true, "testLink");
        }

        @Test
        @DisplayName("is instantiated partial constructor")
        void isInstantiatedWithNew() {
            linkBaseDto = new LinkBaseDto(ZERO, NOW, true, "testLink");
            assertThat(linkBaseDto).hasFieldOrPropertyWithValue("id", ZERO);
            assertThat(linkBaseDto).hasFieldOrPropertyWithValue("dateTime", NOW);
            assertThat(linkBaseDto).hasFieldOrPropertyWithValue("visible", true);
            assertThat(linkBaseDto).hasFieldOrPropertyWithValue("link", "testLink");
        }

        @Test
        @DisplayName("is instantiated with builder")
        void isInstantiatedWithBuilder() {
            linkBaseDto = LinkBaseDto.builder()
                    .id(ZERO)
                    .dateTime(NOW)
                    .visible(true)
                    .link("testLink")
                    .build();
            assertThat(linkBaseDto).hasFieldOrPropertyWithValue("id", ZERO);
            assertThat(linkBaseDto).hasFieldOrPropertyWithValue("dateTime", NOW);
            assertThat(linkBaseDto).hasFieldOrPropertyWithValue("visible", true);
            assertThat(linkBaseDto).hasFieldOrPropertyWithValue("link", "testLink");
        }

        @Test
        @DisplayName("Equals and hashCode")
        void testEqualsAndHashCode() {
            assertNotEquals(new LinkBaseDto(), linkBaseDto);
            LinkBaseDto expected = new LinkBaseDto(ZERO, NOW, true, "testLink");
            assertEquals(expected.hashCode(), linkBaseDto.hashCode());
            assertEquals(expected, linkBaseDto);
        }

        @Test
        @DisplayName("The length of string from toString is great than zero")
        void testToString() {
            assertTrue(linkBaseDto.toString().length() > 0);
        }

        @Test
        @DisplayName("Update entity by DTO")
        void update() {
            Link expected1 = new Link(ZERO, NOW, true, "testLink", EMPTY_LINK_DESCRIPTIONS);
            assertEquals(expected1, linkBaseDto.update(new Link(ZERO)));

            LinkDescription linkDescription = new LinkDescription(Constants.Types.UUID.ZERO);
            linkDescription.setDateTime(NOW);
            Set<LinkDescription> linkDescriptions = Collections.singleton(linkDescription);
            Map<String, Object> values = new HashMap<String, Object>() {{
                put("descriptions", linkDescriptions);
            }};

            Link expected2 = new Link(ZERO, NOW, true, "testLink", linkDescriptions);
            Link test = linkBaseDto.update(new Link(ZERO), values);
            assertEquals(expected2, test);
            // TODO assertEquals(expected2.getRecords(), test.getRecords());
        }

        @Test
        @DisplayName("Instantiated DTO by entity")
        void instantiatedEntity() {
            Link entity = new Link(ZERO, NOW, true, "testLink", EMPTY_LINK_DESCRIPTIONS);
            LinkBaseDto expected = new LinkBaseDto(entity);
            assertEquals(expected, linkBaseDto);
        }
    }
}
//EOF
