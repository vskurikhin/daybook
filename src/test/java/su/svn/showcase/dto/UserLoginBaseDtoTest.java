/*
 * This file was last modified at 2020.03.15 12:34 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * UserLoginBaseDtoTest.java
 * $Id$
 */

package su.svn.showcase.dto;

import org.junit.jupiter.api.*;
import su.svn.showcase.domain.UserLogin;
import su.svn.utils.ValidateUtil;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static su.svn.shared.Constants.Types.UUID.ZERO;
import static su.svn.utils.TestData.NEWS_GROUP_UUID0;
import static su.svn.utils.TestData.USER_LOGIN_UUID0;

@DisplayName("Class UserLoginBaseDto")
class UserLoginBaseDtoTest {
    private final static LocalDateTime NOW = LocalDateTime.now();

    private UserLoginBaseDto userLoginBaseDto;

    @Test
    @DisplayName("is instantiated")
    void isInstantiatedWithNew() {
        new UserLoginBaseDto();
    }

    @Nested
    @DisplayName("when new with empty constructor")
    class WhenNew {
        @BeforeEach
        void createNew() {
            userLoginBaseDto = new UserLoginBaseDto();
        }

        @Test
        @DisplayName("default values")
        void defaults() {
            assertThat(userLoginBaseDto).hasFieldOrPropertyWithValue("id", null);
            assertThat(userLoginBaseDto).hasFieldOrPropertyWithValue("dateTime", null);
            assertThat(userLoginBaseDto).hasFieldOrPropertyWithValue("login", null);
            assertThat(userLoginBaseDto).hasFieldOrPropertyWithValue("password", null);
        }

        @Test
        @DisplayName("Setters and getters")
        void testSettersAndGetters () {
            userLoginBaseDto.setDateTime(NOW);
            assertThat(userLoginBaseDto).hasFieldOrPropertyWithValue("dateTime", NOW);
            assertEquals(NOW, userLoginBaseDto.getDateTime());

            userLoginBaseDto.setLogin("testLogin");
            assertThat(userLoginBaseDto).hasFieldOrPropertyWithValue("login", "testLogin");
            assertEquals("testLogin", userLoginBaseDto.getLogin());

            userLoginBaseDto.setPassword("testPassword");
            assertThat(userLoginBaseDto).hasFieldOrPropertyWithValue("password", "testPassword");
            assertEquals("testPassword", userLoginBaseDto.getPassword());
        }

        @Test
        @DisplayName("violation on code is null")
        void codeIsNull() {
            assertFalse(ValidateUtil.isNull(3, userLoginBaseDto).hasNext());
        }
    }

    @Nested
    @DisplayName("when new with all args constructor")
    class WhenNewAllArgsConstructor {
        @BeforeEach
        void createNew() {
            userLoginBaseDto = new UserLoginBaseDto(ZERO, NOW, "testLogin", "testPassword");
        }

        @Test
        @DisplayName("is instantiated partial constructor")
        void isInstantiatedWithNew() {
            userLoginBaseDto = new UserLoginBaseDto(USER_LOGIN_UUID0, NOW, "testLogin", "testPassword");
            assertThat(userLoginBaseDto).hasFieldOrPropertyWithValue("dateTime", NOW);
            assertThat(userLoginBaseDto).hasFieldOrPropertyWithValue("login", "testLogin");
            assertThat(userLoginBaseDto).hasFieldOrPropertyWithValue("password", "testPassword");
        }

        @Test
        @DisplayName("is instantiated with builder")
        void isInstantiatedWithBuilder() {
            userLoginBaseDto = UserLoginBaseDto.builder()
                    .id(ZERO)
                    .dateTime(NOW)
                    .login("testLogin")
                    .password("testPassword")
                    .build();
            assertThat(userLoginBaseDto).hasFieldOrPropertyWithValue("id", ZERO);
            assertThat(userLoginBaseDto).hasFieldOrPropertyWithValue("dateTime", NOW);
            assertThat(userLoginBaseDto).hasFieldOrPropertyWithValue("login", "testLogin");
            assertThat(userLoginBaseDto).hasFieldOrPropertyWithValue("password", "testPassword");
        }

        @Test
        @DisplayName("initialized values")
        void defaults() {
            assertThat(userLoginBaseDto).hasFieldOrPropertyWithValue("id", ZERO);
            assertThat(userLoginBaseDto).hasFieldOrPropertyWithValue("dateTime", NOW);
            assertThat(userLoginBaseDto).hasFieldOrPropertyWithValue("login", "testLogin");
            assertThat(userLoginBaseDto).hasFieldOrPropertyWithValue("password", "testPassword");
        }

        @Test
        @DisplayName("Equals and hashCode")
        void testEqualsAndHashCode() {
            assertNotEquals(new UserLoginBaseDto(), userLoginBaseDto);
            UserLoginBaseDto expected = new UserLoginBaseDto(NEWS_GROUP_UUID0, NOW, "testLogin", "testPassword");
            assertEquals(expected.hashCode(), userLoginBaseDto.hashCode());
            assertEquals(expected, userLoginBaseDto);
        }

        @Test
        @DisplayName("The length of string from toString is great than zero")
        void testToString() {
            assertTrue(userLoginBaseDto.toString().length() > 0);
        }

        @Test
        @DisplayName("Update entity by DTO")
        void update() {
            UserLogin expected = new UserLogin(ZERO);
            expected.setLogin("testLogin");
            expected.setPassword("testPassword");
            expected.setDateTime(NOW);
            assertEquals(expected, userLoginBaseDto.update(new UserLogin(ZERO)));
        }

        @Test
        @DisplayName("Instantiated DTO by entity")
        void instantiatedEntity() {
            UserLogin entity = new UserLogin(ZERO);
            entity.setLogin("testLogin");
            entity.setPassword("testPassword");
            entity.setDateTime(NOW);
            UserLoginBaseDto expected = new UserLoginBaseDto(entity);
            assertEquals(expected, userLoginBaseDto);
        }
    }
}
//EOF
