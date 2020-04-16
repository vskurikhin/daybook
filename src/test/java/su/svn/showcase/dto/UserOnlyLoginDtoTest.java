/*
 * This file was last modified at 2020.04.14 22:15 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * UserOnlyLoginDtoTest.java
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
import static su.svn.utils.TestData.NEWS_GROUP_UUID0;
import static su.svn.utils.TestData.USER_LOGIN_UUID0;

@DisplayName("Class UserOnlyLoginBaseDto")
class UserOnlyLoginDtoTest {
    private final static LocalDateTime NOW = LocalDateTime.now();

    private UserOnlyLoginDto userOnlyLoginDto;

    @Test
    @DisplayName("is instantiated")
    void isInstantiatedWithNew() {
        new UserOnlyLoginDto();
    }

    @Nested
    @DisplayName("when new with empty constructor")
    class WhenNew {
        @BeforeEach
        void createNew() {
            userOnlyLoginDto = new UserOnlyLoginDto();
        }

        @Test
        @DisplayName("default values")
        void defaults() {
            assertThat(userOnlyLoginDto).hasFieldOrPropertyWithValue("id", null);
            assertThat(userOnlyLoginDto).hasFieldOrPropertyWithValue("login", null);
        }

        @Test
        @DisplayName("Setters and getters")
        void testSettersAndGetters () {
            userOnlyLoginDto.setLogin("testLogin");
            assertThat(userOnlyLoginDto).hasFieldOrPropertyWithValue("login", "testLogin");
            assertEquals("testLogin", userOnlyLoginDto.getLogin());
        }

        @Test
        @DisplayName("violation on code is null")
        void codeIsNull() {
            assertFalse(ValidateUtil.isNull(2, userOnlyLoginDto).hasNext());
        }
    }

    @Nested
    @DisplayName("when new with all args constructor")
    class WhenNewAllArgsConstructor {
        @BeforeEach
        void createNew() {
            userOnlyLoginDto = new UserOnlyLoginDto(ZERO, "testLogin");
        }

        @Test
        @DisplayName("is instantiated partial constructor")
        void isInstantiatedWithNew() {
            userOnlyLoginDto = new UserOnlyLoginDto(USER_LOGIN_UUID0, "testLogin");
            assertThat(userOnlyLoginDto).hasFieldOrPropertyWithValue("login", "testLogin");
        }

        @Test
        @DisplayName("is instantiated with builder")
        void isInstantiatedWithBuilder() {
            userOnlyLoginDto = UserOnlyLoginDto.builder()
                    .id(ZERO)
                    .login("testLogin")
                    .build();
            assertThat(userOnlyLoginDto).hasFieldOrPropertyWithValue("id", ZERO);
            assertThat(userOnlyLoginDto).hasFieldOrPropertyWithValue("login", "testLogin");
        }

        @Test
        @DisplayName("initialized values")
        void defaults() {
            assertThat(userOnlyLoginDto).hasFieldOrPropertyWithValue("id", ZERO);
            assertThat(userOnlyLoginDto).hasFieldOrPropertyWithValue("login", "testLogin");
        }

        @Test
        @DisplayName("Equals and hashCode")
        void testEqualsAndHashCode() {
            assertNotEquals(new UserOnlyLoginDto(), userOnlyLoginDto);
            UserOnlyLoginDto expected = new UserOnlyLoginDto(NEWS_GROUP_UUID0, "testLogin");
            assertEquals(expected.hashCode(), userOnlyLoginDto.hashCode());
            assertEquals(expected, userOnlyLoginDto);
        }

        @Test
        @DisplayName("The length of string from toString is great than zero")
        void testToString() {
            assertTrue(userOnlyLoginDto.toString().length() > 0);
        }
    }
}
//EOF
