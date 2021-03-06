/*
 * This file was last modified at 2020.04.14 22:15 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * RoleJdoTest.java
 * $Id$
 */

package su.svn.showcase.dto.jdo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import su.svn.showcase.domain.Role;
import su.svn.showcase.dto.jdo.RoleJdo;
import su.svn.utils.ValidateUtil;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static su.svn.shared.Constants.Types.UUID.ZERO;
import static su.svn.utils.TestData.ROLE_UUID0;

@DisplayName("Class RoleDto")
class RoleJdoTest {

    private final static LocalDateTime NOW = LocalDateTime.now();

    private RoleJdo roleBaseDto;

    @Test
    @DisplayName("is instantiated")
    void isInstantiatedWithNew() {
        new RoleJdo();
    }

    @Nested
    @DisplayName("when new with empty constructor")
    class WhenNew {
        @BeforeEach
        void createNew() {
            roleBaseDto = new RoleJdo();
        }

        @Test
        @DisplayName("default values")
        void defaults() {
            assertThat(roleBaseDto).hasFieldOrPropertyWithValue("id", null);
            assertThat(roleBaseDto).hasFieldOrPropertyWithValue("roleName", null);
        }

        @Test
        @DisplayName("Setters and getters")
        void testSettersAndGetters () {
            roleBaseDto.setRoleName("testRole");
            assertThat(roleBaseDto).hasFieldOrPropertyWithValue("roleName", "testRole");
            assertEquals("testRole", roleBaseDto.getRoleName());
        }

        @Test
        @DisplayName("violation on code is null")
        void codeIsNull() {
            assertFalse(ValidateUtil.isNull(1, roleBaseDto).hasNext());
        }
    }

    @Nested
    @DisplayName("when new with all args constructor")
    class WhenNewAllArgsConstructor {
        @BeforeEach
        void createNew() {
            roleBaseDto = new RoleJdo(ZERO, "testRole");
        }

        @Test
        @DisplayName("is instantiated partial constructor")
        void isInstantiatedWithNew() {
            roleBaseDto = new RoleJdo(ROLE_UUID0, "testRole");
            assertThat(roleBaseDto).hasFieldOrPropertyWithValue("roleName", "testRole");
        }

        @Test
        @DisplayName("is instantiated with builder")
        void isInstantiatedWithBuilder() {
            roleBaseDto = RoleJdo.builder().id(ZERO).roleName("testRole").build();
            assertThat(roleBaseDto).hasFieldOrPropertyWithValue("id", ZERO);
            assertThat(roleBaseDto).hasFieldOrPropertyWithValue("roleName", "testRole");
        }

        @Test
        @DisplayName("Equals and hashCode")
        void testEqualsAndHashCode() {
            assertNotEquals(new RoleJdo(), roleBaseDto);
            RoleJdo expected = new RoleJdo(ZERO, "testRole");
            assertEquals(expected.hashCode(), roleBaseDto.hashCode());
            assertEquals(expected, roleBaseDto);
        }

        @Test
        @DisplayName("The length of string from toString is great than zero")
        void testToString() {
            assertTrue(roleBaseDto.toString().length() > 0);
        }
    }
}
//EOF
