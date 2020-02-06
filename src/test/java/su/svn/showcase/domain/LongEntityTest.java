/*
 * This file was last modified at 2020.02.06 22:14 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * LongEntityTest.java
 * $Id$
 */

package su.svn.showcase.domain;

import org.junit.jupiter.api.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static su.svn.showcase.domain.LongEntity.ZERO;

@DisplayName("Class LongEntity")
class LongEntityTest {

    private LongEntity longEntity;

    @Test
    @DisplayName("is instantiated")
    void isInstantiatedWithNew() {
        new LongEntity();
    }

    @Nested
    @DisplayName("when new with empty constructor")
    class WhenNew {
        @BeforeEach
        void createNew() {
            longEntity = new LongEntity();
        }

        @Test
        @DisplayName("default values and getter")
        void defaults() {
            assertNotNull(longEntity.getId());
        }

        @Test
        @DisplayName("Setter and getter")
        void testSettersAndGetters () {
            longEntity.setId(ZERO);
            assertThat(longEntity).hasFieldOrPropertyWithValue("id", ZERO);
            assertEquals(ZERO, longEntity.getId());
        }
    }

    @Nested
    @DisplayName("when new with all args constructor")
    class WhenNewAllArgsConstructor {
        @BeforeEach
        void createNew() {
            longEntity = new LongEntity(ZERO);
        }

        @Test
        @DisplayName("initialized values")
        void defaults() {
            assertThat(longEntity).hasFieldOrPropertyWithValue("id", ZERO);
        }

        @Test
        @DisplayName("Equals and hashCode")
        void testEqualsAndHashCode() {
            assertNotEquals(new LongEntity(), longEntity);
            LongEntity expected = new LongEntity(ZERO);
            assertEquals(expected.hashCode(), longEntity.hashCode());
            assertEquals(expected, longEntity);
            assertEquals(longEntity, longEntity);
            assertNotEquals(longEntity, null);
            assertNotEquals(longEntity, new Object());
        }

        @Test
        @DisplayName("The length of string from toString is great than zero")
        void testToString() {
            assertTrue(longEntity.toString().length() > 0);
        }
    }
}
///EOF
