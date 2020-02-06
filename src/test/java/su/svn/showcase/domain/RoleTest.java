/*
 * This file was last modified at 2020.02.06 22:29 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * RoleTest.java
 * $Id$
 */

package su.svn.showcase.domain;

import org.junit.jupiter.api.*;
import su.svn.utils.ValidateUtil;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static su.svn.showcase.domain.UUIDEntity.ZERO;

@DisplayName("Class Role")
class RoleTest {

    private Role role;

    @Test
    @DisplayName("is instantiated")
    void isInstantiatedWithNew() {
        new Role();
    }

    @Nested
    @DisplayName("when new with empty constructor")
    class WhenNew {
        @BeforeEach
        void createNew() {
            role = new Role();
        }

        @Test
        @DisplayName("default values")
        void defaults() {
            assertNotNull(role.getId());
            assertNotEquals(ZERO, role.getId());
            assertThat(role).hasFieldOrPropertyWithValue("roleName", null);
        }

        @Test
        @DisplayName("Setters and getters")
        void testSettersAndGetters () {
            role.setRoleName("testRole");
            assertThat(role).hasFieldOrPropertyWithValue("roleName", "testRole");
            assertEquals("testRole", role.getRoleName());
        }

        @Test
        @DisplayName("violation on code is null")
        void codeIsNull() {
            assertFalse(ValidateUtil.isNull(1, role).hasNext());
        }
    }

    @Nested
    @DisplayName("when new with all args constructor")
    class WhenNewAllArgsConstructor {
        @BeforeEach
        void createNew() {
            role = new Role(ZERO, "testRole");
        }

        @Test
        @DisplayName("is instantiated partial constructor")
        void isInstantiatedWithNew() {
            role = new Role("testRole");
            assertThat(role).hasFieldOrPropertyWithValue("roleName", "testRole");
        }

        @Test
        @DisplayName("is instantiated with builder")
        void isInstantiatedWithBuilder() {
            role = Role.builder().id(ZERO).roleName("testRole").build();
            assertThat(role).hasFieldOrPropertyWithValue("id", ZERO);
            assertThat(role).hasFieldOrPropertyWithValue("roleName", "testRole");
        }

        @Test
        @DisplayName("Equals and hashCode")
        void testEqualsAndHashCode() {
            assertNotEquals(new Role(), role);
            Role expected = new Role(ZERO, "testRole");
            assertEquals(expected.hashCode(), role.hashCode());
            assertEquals(expected, role);
        }

        @Test
        @DisplayName("The length of string from toString is great than zero")
        void testToString() {
            assertTrue(role.toString().length() > 0);
        }
    }
}
//EOF
