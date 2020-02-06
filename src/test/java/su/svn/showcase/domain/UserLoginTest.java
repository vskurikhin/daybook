/*
 * This file was last modified at 2020.02.06 22:29 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * UserLoginTest.java
 * $Id$
 */

package su.svn.showcase.domain;

import org.junit.jupiter.api.*;
import su.svn.utils.ValidateUtil;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static su.svn.showcase.domain.UUIDEntity.ZERO;
import static su.svn.utils.TestData.EMPTY_USER_ROLES;
import static su.svn.utils.TestData.NOW;

@DisplayName("Class UserLogin")
class UserLoginTest {

    private UserLogin userLogin;

    @Test
    @DisplayName("is instantiated")
    void isInstantiatedWithNew() {
        new UserLogin();
    }

    @Nested
    @DisplayName("when new with empty constructor")
    class WhenNew {
        @BeforeEach
        void createNew() {
            userLogin = new UserLogin();
        }

        @Test
        @DisplayName("default values")
        void defaults() {
            assertNotNull(userLogin.getId());
            assertNotEquals(ZERO, userLogin.getId());
            assertThat(userLogin).hasFieldOrPropertyWithValue("dateTime", null);
            assertThat(userLogin).hasFieldOrPropertyWithValue("login", null);
            assertThat(userLogin).hasFieldOrPropertyWithValue("password", null);
            assertThat(userLogin).hasFieldOrPropertyWithValue("roles", null);
        }

        @Test
        @DisplayName("Setters and getters")
        void testSettersAndGetters () {
            userLogin.setDateTime(NOW);
            assertThat(userLogin).hasFieldOrPropertyWithValue("dateTime", NOW);
            assertEquals(NOW, userLogin.getDateTime());

            userLogin.setLogin("testLogin");
            assertThat(userLogin).hasFieldOrPropertyWithValue("login", "testLogin");
            assertEquals("testLogin", userLogin.getLogin());

            userLogin.setPassword("testPassword");
            assertThat(userLogin).hasFieldOrPropertyWithValue("password", "testPassword");
            assertEquals("testPassword", userLogin.getPassword());

            userLogin.setRoles(EMPTY_USER_ROLES);
            assertThat(userLogin).hasFieldOrPropertyWithValue("roles", EMPTY_USER_ROLES);
            assertEquals(EMPTY_USER_ROLES, userLogin.getRoles());
        }

        @Test
        @DisplayName("violation on code is null")
        void codeIsNull() {
            assertFalse(ValidateUtil.isNull(3, userLogin).hasNext());
        }
    }

    @Nested
    @DisplayName("when new with all args constructor")
    class WhenNewAllArgsConstructor {
        @BeforeEach
        void createNew() {
            userLogin = new UserLogin(ZERO, NOW, "testLogin", "testPassword", EMPTY_USER_ROLES);
        }

        @Test
        @DisplayName("is instantiated partial constructor")
        void isInstantiatedWithNew() {
            userLogin = new UserLogin(NOW, "testLogin", "testPassword", EMPTY_USER_ROLES);
            assertThat(userLogin).hasFieldOrPropertyWithValue("dateTime", NOW);
            assertThat(userLogin).hasFieldOrPropertyWithValue("login", "testLogin");
            assertThat(userLogin).hasFieldOrPropertyWithValue("password", "testPassword");
            assertThat(userLogin).hasFieldOrPropertyWithValue("roles", EMPTY_USER_ROLES);
        }

        @Test
        @DisplayName("is instantiated with builder")
        void isInstantiatedWithBuilder() {
            userLogin = UserLogin.builder()
                    .id(ZERO)
                    .dateTime(NOW)
                    .login("testLogin")
                    .password("testPassword")
                    .roles(EMPTY_USER_ROLES)
                    .build();
            assertThat(userLogin).hasFieldOrPropertyWithValue("id", ZERO);
            assertThat(userLogin).hasFieldOrPropertyWithValue("dateTime", NOW);
            assertThat(userLogin).hasFieldOrPropertyWithValue("login", "testLogin");
            assertThat(userLogin).hasFieldOrPropertyWithValue("password", "testPassword");
            assertThat(userLogin).hasFieldOrPropertyWithValue("roles", EMPTY_USER_ROLES);
        }

        @Test
        @DisplayName("Equals and hashCode")
        void testEqualsAndHashCode() {
            assertNotEquals(new UserLogin(), userLogin);
            UserLogin expected = new UserLogin(ZERO, NOW, "testLogin", "testPassword", EMPTY_USER_ROLES);
            assertEquals(expected.hashCode(), userLogin.hashCode());
            assertEquals(expected, userLogin);
        }

        @Test
        @DisplayName("The length of string from toString is great than zero")
        void testToString() {
            assertTrue(userLogin.toString().length() > 0);
        }
    }
}
//EOF
