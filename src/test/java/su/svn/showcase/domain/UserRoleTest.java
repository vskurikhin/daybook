/*
 * This file was last modified at 2020.03.15 12:34 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * UserRoleTest.java
 * $Id$
 */

package su.svn.showcase.domain;

import org.junit.jupiter.api.*;
import su.svn.utils.ValidateUtil;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static su.svn.shared.Constants.Types.UUID.ZERO;
import static su.svn.showcase.domain.TestData.cloneRole0;
import static su.svn.showcase.domain.TestData.cloneRole1;
import static su.svn.utils.TestData.NOW;

@DisplayName("Class UserRole")
class UserRoleTest {

    private Role role;

    private UserRole userRole;

    @Test
    @DisplayName("is instantiated")
    void isInstantiatedWithNew() {
        new UserRole();
    }

    @Nested
    @DisplayName("when new with empty constructor")
    class WhenNew {
        @BeforeEach
        void createNew() {
            role = cloneRole0();
            userRole = new UserRole();
        }

        @Test
        @DisplayName("default values")
        void defaults() {
            assertThat(userRole).hasFieldOrPropertyWithValue("id", null);
            assertThat(userRole).hasFieldOrPropertyWithValue("role", null);
            assertThat(userRole).hasFieldOrPropertyWithValue("dateTime", null);
            assertThat(userRole).hasFieldOrPropertyWithValue("roleName", null);
            assertThat(userRole).hasFieldOrPropertyWithValue("userLogin", null);
        }

        @Test
        @DisplayName("Setters and getters")
        void testSettersAndGetters () {
            userRole.setRole(role);
            assertThat(userRole).hasFieldOrPropertyWithValue("role", role);
            assertEquals(role, userRole.getRole());

            userRole.setDateTime(NOW);
            assertThat(userRole).hasFieldOrPropertyWithValue("dateTime", NOW);
            assertEquals(NOW, userRole.getDateTime());

            userRole.setRoleName("testRole");
            assertThat(userRole).hasFieldOrPropertyWithValue("roleName", "testRole");
            assertEquals("testRole", userRole.getRoleName());

            UserLogin userLogin = UserLogin.builder().id(ZERO).login("testLogin").dateTime(NOW).build();

            userRole.setUserLogin(userLogin);
            assertThat(userRole).hasFieldOrPropertyWithValue("userLogin", userLogin);
            assertEquals(userLogin, userRole.getUserLogin());
        }

        @Test
        @DisplayName("violation on code is null")
        void codeIsNull() {
            assertFalse(ValidateUtil.isNull(4, userRole).hasNext());
        }
    }

    @Nested
    @DisplayName("when new with all args constructor")
    class WhenNewAllArgsConstructor {

        UserLogin userLogin;

        @BeforeEach
        void createNew() {
            role = cloneRole1();
            userLogin = UserLogin.builder().id(ZERO).login("testLogin").dateTime(NOW).build();
            userRole = new UserRole(ZERO, role, NOW, "testRole", userLogin);
        }

        @Test
        @DisplayName("is instantiated partial constructor")
        void isInstantiatedWithNew() {
            userRole = new UserRole(ZERO, role, NOW, "testRole", userLogin);
            assertThat(userRole).hasFieldOrPropertyWithValue("roleName", "testRole");
        }

        @Test
        @DisplayName("is instantiated with builder")
        void isInstantiatedWithBuilder() {
            userRole = UserRole.builder()
                    .id(ZERO)
                    .role(role)
                    .dateTime(NOW)
                    .roleName("testRole")
                    .userLogin(userLogin).build();
            assertThat(userRole).hasFieldOrPropertyWithValue("id", ZERO);
            assertThat(userRole).hasFieldOrPropertyWithValue("role", role);
            assertThat(userRole).hasFieldOrPropertyWithValue("dateTime", NOW);
            assertThat(userRole).hasFieldOrPropertyWithValue("roleName", "testRole");
            assertThat(userRole).hasFieldOrPropertyWithValue("userLogin", userLogin);
        }

        @Test
        @DisplayName("Equals and hashCode")
        void testEqualsAndHashCode() {
            assertNotEquals(new UserRole(), userRole);
            UserRole expected = new UserRole(ZERO, role, NOW, "testRole", userLogin);
            assertEquals(expected.hashCode(), userRole.hashCode());
            assertEquals(expected, userRole);
        }

        @Test
        @DisplayName("The length of string from toString is great than zero")
        void testToString() {
            assertTrue(userRole.toString().length() > 0);
        }
    }
}
//EOF
