/*
 * This file was last modified at 2020.02.06 22:18 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * UUIDEntityTest.java
 * $Id$
 */

package su.svn.showcase.domain;

import org.junit.jupiter.api.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static su.svn.showcase.domain.UUIDEntity.ZERO;

@DisplayName("Class UUIDEntity")
class UUIDEntityTest {

    private UUIDEntity entity;

    @Test
    @DisplayName("is instantiated")
    void isInstantiatedWithNew() {
        new UUIDEntity();
    }

    @Nested
    @DisplayName("when new with empty constructor")
    class WhenNew {
        @BeforeEach
        void createNew() {
            entity = new UUIDEntity();
        }

        @Test
        @DisplayName("default values and getter")
        void defaults() {
            assertNotNull(entity.getId());
        }

        @Test
        @DisplayName("Setter and getter")
        void testSettersAndGetters () {
            entity.setId(ZERO);
            assertThat(entity).hasFieldOrPropertyWithValue("id", ZERO);
            assertEquals(ZERO, entity.getId());
        }
    }

    @Nested
    @DisplayName("when new with all args constructor")
    class WhenNewAllArgsConstructor {
        @BeforeEach
        void createNew() {
            entity = new UUIDEntity(ZERO);
        }

        @Test
        @DisplayName("initialized values")
        void defaults() {
            assertThat(entity).hasFieldOrPropertyWithValue("id", ZERO);
        }

        @Test
        @DisplayName("Equals and hashCode")
        void testEqualsAndHashCode() {
            assertNotEquals(new UUIDEntity(), entity);
            UUIDEntity expected = new UUIDEntity(ZERO);
            assertEquals(expected.hashCode(), entity.hashCode());
            assertEquals(expected, entity);
            assertEquals(entity, entity);
            assertNotEquals(entity, null);
            assertNotEquals(entity, new Object());
        }

        @Test
        @DisplayName("The length of string from toString is great than zero")
        void testToString() {
            assertTrue(entity.toString().length() > 0);
        }
    }
}
//EOF
