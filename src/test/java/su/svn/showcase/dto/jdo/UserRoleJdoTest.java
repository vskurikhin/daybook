/*
 * This file was last modified at 2020.04.14 22:15 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * UserRoleJdoTest.java
 * $Id$
 */

package su.svn.showcase.dto.jdo;

import org.junit.jupiter.api.*;
import su.svn.showcase.domain.Role;
import su.svn.showcase.domain.UserLogin;
import su.svn.showcase.domain.UserRole;
import su.svn.showcase.dto.UserOnlyLoginDto;
import su.svn.utils.ValidateUtil;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static su.svn.shared.Constants.Types.UUID.ZERO;
import static su.svn.showcase.domain.TestData.*;
import static su.svn.showcase.dto.TestData.*;
import static su.svn.utils.TestData.NOW;
import static su.svn.utils.TestData.ROLE_UUID0;

@DisplayName("Class UserRoleFullDtoTest")
class UserRoleJdoTest {

    private Role role;

    private RoleJdo roleJdo;

    private UserRoleJdo userRoleJdo;

    private UserOnlyLoginDto userLoginDto;

    @Test
    @DisplayName("is instantiated")
    void isInstantiatedWithNew() {
        new UserRoleJdo();
    }

    @Nested
    @DisplayName("when new with empty constructor")
    class WhenNew {
        @BeforeEach
        void createNew() {
            roleJdo = cloneRoleJdo0();
            userLoginDto = new UserOnlyLoginDto();
            userRoleJdo = new UserRoleJdo();
        }

        @Test
        @DisplayName("default values")
        void defaults() {
            assertThat(userRoleJdo).hasFieldOrPropertyWithValue("id", null);
            assertThat(userRoleJdo).hasFieldOrPropertyWithValue("role", null);
            assertThat(userRoleJdo).hasFieldOrPropertyWithValue("roleName", null);
            assertThat(userRoleJdo).hasFieldOrPropertyWithValue("dateTime", null);
            assertThat(userRoleJdo).hasFieldOrPropertyWithValue("userLogin", null);
        }

        @Test
        @DisplayName("Setters and getters")
        void testSettersAndGetters () {
            userRoleJdo.setRole(roleJdo);
            assertThat(userRoleJdo).hasFieldOrPropertyWithValue("role", roleJdo);
            assertEquals(roleJdo, userRoleJdo.getRole());

            userRoleJdo.setRoleName("testRole");
            assertThat(userRoleJdo).hasFieldOrPropertyWithValue("roleName", "testRole");
            assertEquals("testRole", userRoleJdo.getRoleName());

            userRoleJdo.setDateTime(NOW);
            assertThat(userRoleJdo).hasFieldOrPropertyWithValue("dateTime", NOW);
            assertEquals(NOW , userRoleJdo.getDateTime());

            userRoleJdo.setUserLogin(userLoginDto);
            assertThat(userRoleJdo).hasFieldOrPropertyWithValue("userLogin", userLoginDto);
            assertEquals(userLoginDto, userRoleJdo.getUserLogin());
        }

        @Test
        @DisplayName("violation on code is null")
        void codeIsNull() {
            assertFalse(ValidateUtil.isNull(1, userRoleJdo).hasNext());
        }
    }

    @Nested
    @DisplayName("when new with all args constructor")
    class WhenNewAllArgsConstructor {
        @BeforeEach
        void createNew() {
            role = cloneRole0();
            roleJdo = cloneRoleJdo0();
            userLoginDto = cloneUserOnlyLoginBaseDto0();
            userRoleJdo = cloneUserRoleFullDto0();
        }

        @Test
        @DisplayName("is instantiated partial constructor")
        void isInstantiatedWithNew() {
            userRoleJdo = new UserRoleJdo(ROLE_UUID0, roleJdo, NOW, "testRole", userLoginDto);
            assertThat(userRoleJdo).hasFieldOrPropertyWithValue("role", roleJdo);
            assertThat(userRoleJdo).hasFieldOrPropertyWithValue("roleName", "testRole");
            assertThat(userRoleJdo).hasFieldOrPropertyWithValue("dateTime", NOW);
            assertThat(userRoleJdo).hasFieldOrPropertyWithValue("userLogin", userLoginDto);
        }

        @Test
        @DisplayName("is instantiated with builder")
        void isInstantiatedWithBuilder() {
            userRoleJdo = UserRoleJdo.builder()
                    .id(ZERO)
                    .role(roleJdo)
                    .dateTime(NOW)
                    .roleName("testRole")
                    .userLogin(userLoginDto)
                    .build();
            assertThat(userRoleJdo).hasFieldOrPropertyWithValue("id", ZERO);
            assertThat(userRoleJdo).hasFieldOrPropertyWithValue("role", roleJdo);
            assertThat(userRoleJdo).hasFieldOrPropertyWithValue("roleName", "testRole");
            assertThat(userRoleJdo).hasFieldOrPropertyWithValue("dateTime", NOW);
            assertThat(userRoleJdo).hasFieldOrPropertyWithValue("userLogin", userLoginDto);
        }

        @Test
        @DisplayName("Equals and hashCode")
        void testEqualsAndHashCode() {
            assertNotEquals(new UserRoleJdo(), userRoleJdo);
            UserRoleJdo expected = cloneUserRoleFullDto0();
            assertEquals(expected.hashCode(), userRoleJdo.hashCode());
            assertEquals(expected, userRoleJdo);
        }

        @Test
        @DisplayName("The length of string from toString is great than zero")
        void testToString() {
            assertTrue(userRoleJdo.toString().length() > 0);
        }
    }
}
//EOF
