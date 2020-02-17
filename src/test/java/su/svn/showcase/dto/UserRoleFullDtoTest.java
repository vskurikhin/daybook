/*
 * This file was last modified at 2020.02.15 14:31 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * UserRoleBaseDtoTest.java$
 * $Id$
 */

package su.svn.showcase.dto;

import org.junit.jupiter.api.*;
import su.svn.showcase.domain.UserLogin;
import su.svn.showcase.domain.UserRole;
import su.svn.utils.ValidateUtil;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static su.svn.shared.Constants.UUID.ZERO;
import static su.svn.showcase.domain.TestData.cloneUserLogin0;
import static su.svn.showcase.domain.TestData.cloneUserRole0;
import static su.svn.showcase.dto.TestData.cloneUserLoginBaseDto0;
import static su.svn.showcase.dto.TestData.cloneUserRoleFullDto0;
import static su.svn.utils.TestData.NOW;
import static su.svn.utils.TestData.ROLE_UUID0;

@DisplayName("Class UserRoleShortDto")
class UserRoleFullDtoTest {

    private UserRoleFullDto userRoleFullDto;

    private UserLoginBaseDto userLoginDto;

    private static Validator validator;

    @BeforeAll
    static void setUpValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

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
            userLoginDto = new UserLoginBaseDto();
            userRoleFullDto = new UserRoleFullDto();
        }

        @Test
        @DisplayName("default values")
        void defaults() {
            assertThat(userRoleFullDto).hasFieldOrPropertyWithValue("id", null);
            assertThat(userRoleFullDto).hasFieldOrPropertyWithValue("roleName", null);
            assertThat(userRoleFullDto).hasFieldOrPropertyWithValue("dateTime", null);
            assertThat(userRoleFullDto).hasFieldOrPropertyWithValue("userLogin", null);
        }

        @Test
        @DisplayName("Setters and getters")
        void testSettersAndGetters () {
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
            assertFalse(ValidateUtil.isNull(4, userRoleFullDto).hasNext());
        }
    }

    @Nested
    @DisplayName("when new with all args constructor")
    class WhenNewAllArgsConstructor {
        @BeforeEach
        void createNew() {
            userLoginDto = cloneUserLoginBaseDto0();
            userRoleFullDto = cloneUserRoleFullDto0();
        }

        @Test
        @DisplayName("is instantiated partial constructor")
        void isInstantiatedWithNew() {
            userRoleFullDto = new UserRoleFullDto(ROLE_UUID0, NOW, "testRole", userLoginDto);
            assertThat(userRoleFullDto).hasFieldOrPropertyWithValue("roleName", "testRole");
            assertThat(userRoleFullDto).hasFieldOrPropertyWithValue("dateTime", NOW);
            assertThat(userRoleFullDto).hasFieldOrPropertyWithValue("userLogin", userLoginDto);
        }

        @Test
        @DisplayName("is instantiated with builder")
        void isInstantiatedWithBuilder() {
            userRoleFullDto = UserRoleFullDto.builder()
                    .id(ZERO)
                    .dateTime(NOW)
                    .roleName("testRole")
                    .userLogin(userLoginDto)
                    .build();
            assertThat(userRoleFullDto).hasFieldOrPropertyWithValue("id", ZERO);
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
            assertEquals(expected1, userRoleFullDto.update(cloneUserRole0()));

            UserLogin userLogin = cloneUserLogin0();
            Map<String, Object> values = new HashMap<String, Object>() {{
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
            UserRoleFullDto expected = new UserRoleFullDto(ZERO, NOW, "testRole", userLoginDto);
            // TODO assertEquals(expected, userRoleFullDto);
        }
    }
}
//EOF
/*
expected: <
UserRole(id=00000000-0000-0000-0000-000000000000, dateTime=2020-02-17T14:20:00.223495, roleName=testRole, userLogin=UserLogin(id=00000000-0000-0000-0000-000000000000, dateTime=2020-02-17T14:20:00.498740, login=loginTest0, password=passwordTest0))> but was: <
UserRole(id=00000000-0000-0000-0000-000000000000, dateTime=2020-02-17T14:20:00.498740, roleName=testRole0, userLogin=UserLogin(id=00000000-0000-0000-0000-000000000000, dateTime=2020-02-17T14:20:00.498740, login=loginTest0, password=passwordTest0))>

 */
