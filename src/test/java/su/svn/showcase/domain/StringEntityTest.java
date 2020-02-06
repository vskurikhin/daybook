/*
 * This file was last modified at 2020.02.06 22:17 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * StringEntityTest.java
 * $Id$
 */

package su.svn.showcase.domain;

import org.junit.jupiter.api.*;
import su.svn.showcase.utils.StringUtil;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static su.svn.showcase.domain.StringEntity.ZERO;

@DisplayName("Class StringEntity")
class StringEntityTest {

    private StringEntity entity;

    @Test
    @DisplayName("is instantiated")
    void isInstantiatedWithNew() {
        new StringEntity();
    }

    @Nested
    @DisplayName("when new with empty constructor")
    class WhenNew {
        @BeforeEach
        void createNew() {
            entity = new StringEntity();
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
            entity = new StringEntity(ZERO);
        }

        @Test
        @DisplayName("initialized values")
        void defaults() {
            assertThat(entity).hasFieldOrPropertyWithValue("id", ZERO);
        }

        @Test
        @DisplayName("Equals and hashCode")
        void testEqualsAndHashCode() {
            assertNotEquals(new StringEntity(), entity);
            StringEntity expected = new StringEntity(ZERO);
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

    @Test
    @DisplayName("The length of string from toString is great than zero")
    void generateTest() {
        String max = StringUtil.generateStringId();
        System.out.println("max = " + max);
    }
}
//EOF
