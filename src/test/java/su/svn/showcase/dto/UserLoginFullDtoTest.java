/*
 * This file was last modified at 2020.03.14 13:49 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * UserLoginFullDtoTest.java
 * $Id$
 */

package su.svn.showcase.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import su.svn.showcase.domain.UserLogin;
import su.svn.utils.ValidateUtil;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static su.svn.shared.Constants.Types.UUID.ZERO;
import static su.svn.utils.TestData.*;

@DisplayName("Class UserLoginFullDto")
class UserLoginFullDtoTest {
    private final static LocalDateTime NOW = LocalDateTime.now();

    private UserLoginFullDto userLoginFullDto;

    @Test
    @DisplayName("is instantiated")
    void isInstantiatedWithNew() {
        new UserLoginFullDto();
    }

    @Nested
    @DisplayName("when new with empty constructor")
    class WhenNew {
        @BeforeEach
        void createNew() {
            userLoginFullDto = new UserLoginFullDto();
        }

        @Test
        @DisplayName("default values")
        void defaults() {
            assertThat(userLoginFullDto).hasFieldOrPropertyWithValue("id", null);
            assertThat(userLoginFullDto).hasFieldOrPropertyWithValue("dateTime", null);
            assertThat(userLoginFullDto).hasFieldOrPropertyWithValue("login", null);
            assertThat(userLoginFullDto).hasFieldOrPropertyWithValue("password", null);
            assertThat(userLoginFullDto).hasFieldOrPropertyWithValue("roles", null);
        }

        @Test
        @DisplayName("Setters and getters")
        void testSettersAndGetters () {
            userLoginFullDto.setDateTime(NOW);
            assertThat(userLoginFullDto).hasFieldOrPropertyWithValue("dateTime", NOW);
            assertEquals(NOW, userLoginFullDto.getDateTime());

            userLoginFullDto.setLogin("testLogin");
            assertThat(userLoginFullDto).hasFieldOrPropertyWithValue("login", "testLogin");
            assertEquals("testLogin", userLoginFullDto.getLogin());

            userLoginFullDto.setPassword("testPassword");
            assertThat(userLoginFullDto).hasFieldOrPropertyWithValue("password", "testPassword");
            assertEquals("testPassword", userLoginFullDto.getPassword());

            userLoginFullDto.setRoles(EMPTY_USER_ROLE_DTOS);
            assertThat(userLoginFullDto).hasFieldOrPropertyWithValue("roles", EMPTY_USER_ROLE_DTOS);
            assertEquals(EMPTY_USER_ROLE_DTOS, userLoginFullDto.getRoles());
        }

        @Test
        @DisplayName("violation on code is null")
        void codeIsNull() {
            assertFalse(ValidateUtil.isNull(4, userLoginFullDto).hasNext());
        }
    }

    @Nested
    @DisplayName("when new with all args constructor")
    class WhenNewAllArgsConstructor {
        @BeforeEach
        void createNew() {
            userLoginFullDto = new UserLoginFullDto(ZERO, NOW, "testLogin", "testPassword", EMPTY_USER_ROLE_DTOS);
        }

        @Test
        @DisplayName("is instantiated partial constructor")
        void isInstantiatedWithNew() {
            userLoginFullDto = new UserLoginFullDto(USER_LOGIN_UUID0, NOW, "testLogin", "testPassword", EMPTY_USER_ROLE_DTOS);
            assertThat(userLoginFullDto).hasFieldOrPropertyWithValue("dateTime", NOW);
            assertThat(userLoginFullDto).hasFieldOrPropertyWithValue("login", "testLogin");
            assertThat(userLoginFullDto).hasFieldOrPropertyWithValue("password", "testPassword");
            assertThat(userLoginFullDto).hasFieldOrPropertyWithValue("roles", EMPTY_USER_ROLE_DTOS);
        }

        @Test
        @DisplayName("is instantiated with builder")
        void isInstantiatedWithBuilder() {
            userLoginFullDto = UserLoginFullDto.builder()
                    .id(ZERO)
                    .dateTime(NOW)
                    .login("testLogin")
                    .password("testPassword")
                    .roles(EMPTY_USER_ROLE_DTOS)
                    .build();
            assertThat(userLoginFullDto).hasFieldOrPropertyWithValue("id", ZERO);
            assertThat(userLoginFullDto).hasFieldOrPropertyWithValue("dateTime", NOW);
            assertThat(userLoginFullDto).hasFieldOrPropertyWithValue("login", "testLogin");
            assertThat(userLoginFullDto).hasFieldOrPropertyWithValue("password", "testPassword");
            assertThat(userLoginFullDto).hasFieldOrPropertyWithValue("roles", EMPTY_USER_ROLE_DTOS);
        }

        @Test
        @DisplayName("initialized values")
        void defaults() {
            assertThat(userLoginFullDto).hasFieldOrPropertyWithValue("id", ZERO);
            assertThat(userLoginFullDto).hasFieldOrPropertyWithValue("dateTime", NOW);
            assertThat(userLoginFullDto).hasFieldOrPropertyWithValue("login", "testLogin");
            assertThat(userLoginFullDto).hasFieldOrPropertyWithValue("password", "testPassword");
            assertThat(userLoginFullDto).hasFieldOrPropertyWithValue("roles", EMPTY_USER_ROLE_DTOS);
        }

        @Test
        @DisplayName("Equals and hashCode")
        void testEqualsAndHashCode() {
            assertNotEquals(new UserLoginFullDto(), userLoginFullDto);
            UserLoginFullDto expected = new UserLoginFullDto(NEWS_GROUP_UUID0, NOW, "testLogin", "testPassword", EMPTY_USER_ROLE_DTOS);
            assertEquals(expected.hashCode(), userLoginFullDto.hashCode());
            assertEquals(expected, userLoginFullDto);
        }

        @Test
        @DisplayName("The length of string from toString is great than zero")
        void testToString() {
            assertTrue(userLoginFullDto.toString().length() > 0);
        }

        @Test
        @DisplayName("Update entity by DTO")
        void update() {
            UserLogin expected = new UserLogin(ZERO);
            expected.setLogin("testLogin");
            expected.setPassword("testPassword");
            expected.setDateTime(NOW);
            assertEquals(expected, userLoginFullDto.update(new UserLogin(ZERO)));
        }

        @Test
        @DisplayName("Instantiated DTO by entity")
        void instantiatedEntity() {
            UserLogin entity = new UserLogin(ZERO);
            entity.setLogin("testLogin");
            entity.setPassword("testPassword");
            entity.setDateTime(NOW);
            entity.setRoles(EMPTY_USER_ROLES);
            UserLoginFullDto expected = new UserLoginFullDto(entity);
            assertEquals(expected, userLoginFullDto);
        }
    }
}
//EOF
