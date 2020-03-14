/*
 * This file was last modified at 2020.03.14 13:49 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * UserOnlyLoginBaseDtoTest.java
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
import static su.svn.shared.Constants.UUID.ZERO;
import static su.svn.utils.TestData.NEWS_GROUP_UUID0;
import static su.svn.utils.TestData.USER_LOGIN_UUID0;

@DisplayName("Class UserOnlyLoginBaseDto")
class UserOnlyLoginBaseDtoTest {
    private final static LocalDateTime NOW = LocalDateTime.now();

    private UserOnlyLoginBaseDto userOnlyLoginBaseDto;

    @Test
    @DisplayName("is instantiated")
    void isInstantiatedWithNew() {
        new UserOnlyLoginBaseDto();
    }

    @Nested
    @DisplayName("when new with empty constructor")
    class WhenNew {
        @BeforeEach
        void createNew() {
            userOnlyLoginBaseDto = new UserOnlyLoginBaseDto();
        }

        @Test
        @DisplayName("default values")
        void defaults() {
            assertThat(userOnlyLoginBaseDto).hasFieldOrPropertyWithValue("id", null);
            assertThat(userOnlyLoginBaseDto).hasFieldOrPropertyWithValue("login", null);
        }

        @Test
        @DisplayName("Setters and getters")
        void testSettersAndGetters () {
            userOnlyLoginBaseDto.setLogin("testLogin");
            assertThat(userOnlyLoginBaseDto).hasFieldOrPropertyWithValue("login", "testLogin");
            assertEquals("testLogin", userOnlyLoginBaseDto.getLogin());
        }

        @Test
        @DisplayName("violation on code is null")
        void codeIsNull() {
            assertFalse(ValidateUtil.isNull(2, userOnlyLoginBaseDto).hasNext());
        }
    }

    @Nested
    @DisplayName("when new with all args constructor")
    class WhenNewAllArgsConstructor {
        @BeforeEach
        void createNew() {
            userOnlyLoginBaseDto = new UserOnlyLoginBaseDto(ZERO, "testLogin");
        }

        @Test
        @DisplayName("is instantiated partial constructor")
        void isInstantiatedWithNew() {
            userOnlyLoginBaseDto = new UserOnlyLoginBaseDto(USER_LOGIN_UUID0, "testLogin");
            assertThat(userOnlyLoginBaseDto).hasFieldOrPropertyWithValue("login", "testLogin");
        }

        @Test
        @DisplayName("is instantiated with builder")
        void isInstantiatedWithBuilder() {
            userOnlyLoginBaseDto = UserOnlyLoginBaseDto.builder()
                    .id(ZERO)
                    .login("testLogin")
                    .build();
            assertThat(userOnlyLoginBaseDto).hasFieldOrPropertyWithValue("id", ZERO);
            assertThat(userOnlyLoginBaseDto).hasFieldOrPropertyWithValue("login", "testLogin");
        }

        @Test
        @DisplayName("initialized values")
        void defaults() {
            assertThat(userOnlyLoginBaseDto).hasFieldOrPropertyWithValue("id", ZERO);
            assertThat(userOnlyLoginBaseDto).hasFieldOrPropertyWithValue("login", "testLogin");
        }

        @Test
        @DisplayName("Equals and hashCode")
        void testEqualsAndHashCode() {
            assertNotEquals(new UserOnlyLoginBaseDto(), userOnlyLoginBaseDto);
            UserOnlyLoginBaseDto expected = new UserOnlyLoginBaseDto(NEWS_GROUP_UUID0, "testLogin");
            assertEquals(expected.hashCode(), userOnlyLoginBaseDto.hashCode());
            assertEquals(expected, userOnlyLoginBaseDto);
        }

        @Test
        @DisplayName("The length of string from toString is great than zero")
        void testToString() {
            assertTrue(userOnlyLoginBaseDto.toString().length() > 0);
        }

        @Test
        @DisplayName("Update entity by DTO")
        void update() {
            UserLogin expected = new UserLogin(ZERO);
            expected.setLogin("testLogin");
            UserLogin userLogin = new UserLogin(ZERO);
            userLogin.setLogin("testLogin");
            assertEquals(expected, userOnlyLoginBaseDto.update(userLogin));
        }

        @Test
        @DisplayName("Instantiated DTO by entity")
        void instantiatedEntity() {
            UserLogin entity = new UserLogin(ZERO);
            entity.setLogin("testLogin");
            UserOnlyLoginBaseDto expected = new UserOnlyLoginBaseDto(entity);
            assertEquals(expected, userOnlyLoginBaseDto);
        }
    }
}
//EOF
/*

                    .roles(EMPTY_USER_ROLE_DTOS)
                    expected: <
UserLogin(id=00000000-0000-0000-0000-000000000000, dateTime=2020-03-14T13:41:39.885372, login=testLogin, password=testPassword)> but was: <
UserLogin(id=00000000-0000-0000-0000-000000000000, dateTime=null, login=testLogin, password=null)>

 */
