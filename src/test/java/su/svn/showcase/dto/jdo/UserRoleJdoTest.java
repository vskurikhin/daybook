/*
 * This file was last modified at 2020.04.14 21:03 by Victor N. Skurikhin.
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
import su.svn.showcase.dto.UserRoleFullDto;
import su.svn.showcase.dto.jdo.RoleJdo;
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

    private UserRoleFullDto userRoleFullDto;

    private UserOnlyLoginDto userLoginDto;

    @Test
    @DisplayName("is instantiated")
    void isInstantiatedWithNew() {
        new UserRoleFullDto();
    }

    @Nested
    @DisplayName("when new with empty constructor")
    class WhenNew {
        @BeforeEach
        void createNew() {
            roleJdo = cloneRoleJdo0();
            userLoginDto = new UserOnlyLoginDto();
            userRoleFullDto = new UserRoleFullDto();
        }

        @Test
        @DisplayName("default values")
        void defaults() {
            assertThat(userRoleFullDto).hasFieldOrPropertyWithValue("id", null);
            assertThat(userRoleFullDto).hasFieldOrPropertyWithValue("role", null);
            assertThat(userRoleFullDto).hasFieldOrPropertyWithValue("roleName", null);
            assertThat(userRoleFullDto).hasFieldOrPropertyWithValue("dateTime", null);
            assertThat(userRoleFullDto).hasFieldOrPropertyWithValue("userLogin", null);
        }

        @Test
        @DisplayName("Setters and getters")
        void testSettersAndGetters () {
            userRoleFullDto.setRole(roleJdo);
            assertThat(userRoleFullDto).hasFieldOrPropertyWithValue("role", roleJdo);
            assertEquals(roleJdo, userRoleFullDto.getRole());

            userRoleFullDto.setRoleName("testRole");
            assertThat(userRoleFullDto).hasFieldOrPropertyWithValue("roleName", "testRole");
            assertEquals("testRole", userRoleFullDto.getRoleName());

            userRoleFullDto.setDateTime(NOW);
            assertThat(userRoleFullDto).hasFieldOrPropertyWithValue("dateTime", NOW);
            assertEquals(NOW , userRoleFullDto.getDateTime());

            userRoleFullDto.setUserLogin(userLoginDto);
            assertThat(userRoleFullDto).hasFieldOrPropertyWithValue("userLogin", userLoginDto);
            assertEquals(userLoginDto, userRoleFullDto.getUserLogin());
        }

        @Test
        @DisplayName("violation on code is null")
        void codeIsNull() {
            assertFalse(ValidateUtil.isNull(1, userRoleFullDto).hasNext());
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
            userRoleFullDto = cloneUserRoleFullDto0();
        }

        @Test
        @DisplayName("is instantiated partial constructor")
        void isInstantiatedWithNew() {
            userRoleFullDto = new UserRoleFullDto(ROLE_UUID0, roleJdo, NOW, "testRole", userLoginDto);
            assertThat(userRoleFullDto).hasFieldOrPropertyWithValue("role", roleJdo);
            assertThat(userRoleFullDto).hasFieldOrPropertyWithValue("roleName", "testRole");
            assertThat(userRoleFullDto).hasFieldOrPropertyWithValue("dateTime", NOW);
            assertThat(userRoleFullDto).hasFieldOrPropertyWithValue("userLogin", userLoginDto);
        }

        @Test
        @DisplayName("is instantiated with builder")
        void isInstantiatedWithBuilder() {
            userRoleFullDto = UserRoleFullDto.builder()
                    .id(ZERO)
                    .role(roleJdo)
                    .dateTime(NOW)
                    .roleName("testRole")
                    .userLogin(userLoginDto)
                    .build();
            assertThat(userRoleFullDto).hasFieldOrPropertyWithValue("id", ZERO);
            assertThat(userRoleFullDto).hasFieldOrPropertyWithValue("role", roleJdo);
            assertThat(userRoleFullDto).hasFieldOrPropertyWithValue("roleName", "testRole");
            assertThat(userRoleFullDto).hasFieldOrPropertyWithValue("dateTime", NOW);
            assertThat(userRoleFullDto).hasFieldOrPropertyWithValue("userLogin", userLoginDto);
        }

        @Test
        @DisplayName("Equals and hashCode")
        void testEqualsAndHashCode() {
            assertNotEquals(new UserRoleFullDto(), userRoleFullDto);
            UserRoleFullDto expected = cloneUserRoleFullDto0();
            assertEquals(expected.hashCode(), userRoleFullDto.hashCode());
            assertEquals(expected, userRoleFullDto);
        }

        @Test
        @DisplayName("The length of string from toString is great than zero")
        void testToString() {
            assertTrue(userRoleFullDto.toString().length() > 0);
        }

        @Test
        @DisplayName("Update entity by DTO")
        void update() {
            UserRole expected1 = cloneUserRole0();
            UserRole userRoleTest0 = cloneUserRole0();
            assertEquals(expected1, userRoleFullDto.update(userRoleTest0));

            UserLogin userLogin = cloneUserLogin0();
            Map<String, Object> values = new HashMap<String, Object>() {{
                put("role", role);
                put("dateTime", NOW);
                put("userLogin", userLogin);
            }};
            UserRole expected2 = cloneUserRole0();
            assertEquals(expected2, userRoleFullDto.update(cloneUserRole0(), values));
        }

        @Test
        @DisplayName("Instantiated DTO by entity")
        void instantiatedEntity() {
            UserRole entity = new UserRole();
            entity.setId(ZERO);
            entity.setRoleName("testRole");
            entity.setDateTime(NOW);
            UserRoleFullDto expected = new UserRoleFullDto(ZERO, roleJdo, NOW, "testRole", userLoginDto);
            // TODO assertEquals(expected, userRoleFullDto);
        }
    }
}
//EOF