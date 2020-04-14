/*
 * This file was last modified at 2020.04.14 21:03 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * UserLoginJdoTest.java
 * $Id$
 */

package su.svn.showcase.dto.jdo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import su.svn.showcase.domain.UserLogin;
import su.svn.showcase.dto.jdo.UserLoginJdo;
import su.svn.utils.ValidateUtil;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static su.svn.shared.Constants.Types.UUID.ZERO;
import static su.svn.utils.TestData.*;

@DisplayName("Class UserLoginFullDto")
class UserLoginJdoTest {
    private final static LocalDateTime NOW = LocalDateTime.now();

    private UserLoginJdo userLoginJdo;

    @Test
    @DisplayName("is instantiated")
    void isInstantiatedWithNew() {
        new UserLoginJdo();
    }

    @Nested
    @DisplayName("when new with empty constructor")
    class WhenNew {
        @BeforeEach
        void createNew() {
            userLoginJdo = new UserLoginJdo();
        }

        @Test
        @DisplayName("default values")
        void defaults() {
            assertThat(userLoginJdo).hasFieldOrPropertyWithValue("id", null);
            assertThat(userLoginJdo).hasFieldOrPropertyWithValue("dateTime", null);
            assertThat(userLoginJdo).hasFieldOrPropertyWithValue("login", null);
            assertThat(userLoginJdo).hasFieldOrPropertyWithValue("password", null);
            assertThat(userLoginJdo).hasFieldOrPropertyWithValue("roles", null);
        }

        @Test
        @DisplayName("Setters and getters")
        void testSettersAndGetters () {
            userLoginJdo.setDateTime(NOW);
            assertThat(userLoginJdo).hasFieldOrPropertyWithValue("dateTime", NOW);
            assertEquals(NOW, userLoginJdo.getDateTime());

            userLoginJdo.setLogin("testLogin");
            assertThat(userLoginJdo).hasFieldOrPropertyWithValue("login", "testLogin");
            assertEquals("testLogin", userLoginJdo.getLogin());

            userLoginJdo.setPassword("testPassword");
            assertThat(userLoginJdo).hasFieldOrPropertyWithValue("password", "testPassword");
            assertEquals("testPassword", userLoginJdo.getPassword());

            userLoginJdo.setRoles(EMPTY_USER_ROLE_DTOS);
            assertThat(userLoginJdo).hasFieldOrPropertyWithValue("roles", EMPTY_USER_ROLE_DTOS);
            assertEquals(EMPTY_USER_ROLE_DTOS, userLoginJdo.getRoles());
        }

        @Test
        @DisplayName("violation on code is null")
        void codeIsNull() {
            assertFalse(ValidateUtil.isNull(1, userLoginJdo).hasNext());
        }
    }

    @Nested
    @DisplayName("when new with all args constructor")
    class WhenNewAllArgsConstructor {
        @BeforeEach
        void createNew() {
            userLoginJdo = new UserLoginJdo(ZERO, NOW, "testLogin", "testPassword", EMPTY_USER_ROLE_DTOS);
        }

        @Test
        @DisplayName("is instantiated partial constructor")
        void isInstantiatedWithNew() {
            userLoginJdo = new UserLoginJdo(USER_LOGIN_UUID0, NOW, "testLogin", "testPassword", EMPTY_USER_ROLE_DTOS);
            assertThat(userLoginJdo).hasFieldOrPropertyWithValue("dateTime", NOW);
            assertThat(userLoginJdo).hasFieldOrPropertyWithValue("login", "testLogin");
            assertThat(userLoginJdo).hasFieldOrPropertyWithValue("password", "testPassword");
            assertThat(userLoginJdo).hasFieldOrPropertyWithValue("roles", EMPTY_USER_ROLE_DTOS);
        }

        @Test
        @DisplayName("is instantiated with builder")
        void isInstantiatedWithBuilder() {
            userLoginJdo = UserLoginJdo.builder()
                    .id(ZERO)
                    .dateTime(NOW)
                    .login("testLogin")
                    .password("testPassword")
                    .roles(EMPTY_USER_ROLE_DTOS)
                    .build();
            assertThat(userLoginJdo).hasFieldOrPropertyWithValue("id", ZERO);
            assertThat(userLoginJdo).hasFieldOrPropertyWithValue("dateTime", NOW);
            assertThat(userLoginJdo).hasFieldOrPropertyWithValue("login", "testLogin");
            assertThat(userLoginJdo).hasFieldOrPropertyWithValue("password", "testPassword");
            assertThat(userLoginJdo).hasFieldOrPropertyWithValue("roles", EMPTY_USER_ROLE_DTOS);
        }

        @Test
        @DisplayName("initialized values")
        void defaults() {
            assertThat(userLoginJdo).hasFieldOrPropertyWithValue("id", ZERO);
            assertThat(userLoginJdo).hasFieldOrPropertyWithValue("dateTime", NOW);
            assertThat(userLoginJdo).hasFieldOrPropertyWithValue("login", "testLogin");
            assertThat(userLoginJdo).hasFieldOrPropertyWithValue("password", "testPassword");
            assertThat(userLoginJdo).hasFieldOrPropertyWithValue("roles", EMPTY_USER_ROLE_DTOS);
        }

        @Test
        @DisplayName("Equals and hashCode")
        void testEqualsAndHashCode() {
            assertNotEquals(new UserLoginJdo(), userLoginJdo);
            UserLoginJdo expected = new UserLoginJdo(NEWS_GROUP_UUID0, NOW, "testLogin", "testPassword", EMPTY_USER_ROLE_DTOS);
            assertEquals(expected.hashCode(), userLoginJdo.hashCode());
            assertEquals(expected, userLoginJdo);
        }

        @Test
        @DisplayName("The length of string from toString is great than zero")
        void testToString() {
            assertTrue(userLoginJdo.toString().length() > 0);
        }

        @Test
        @DisplayName("Update entity by DTO")
        void update() {
            UserLogin expected = new UserLogin(ZERO);
            expected.setLogin("testLogin");
            expected.setPassword("testPassword");
            expected.setDateTime(NOW);
            assertEquals(expected, userLoginJdo.update(new UserLogin(ZERO)));
        }

        @Test
        @DisplayName("Instantiated DTO by entity")
        void instantiatedEntity() {
            UserLogin entity = new UserLogin(ZERO);
            entity.setLogin("testLogin");
            entity.setPassword("testPassword");
            entity.setDateTime(NOW);
            entity.setRoles(EMPTY_USER_ROLES);
            UserLoginJdo expected = new UserLoginJdo(entity);
            assertEquals(expected, userLoginJdo);
        }
    }
}
//EOF
