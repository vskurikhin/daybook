/*
 * This file was last modified at  by Victor N. Skurikhin.
 */

package su.svn.showcase.dto;

import org.junit.jupiter.api.*;
import su.svn.showcase.domain.UserLogin;
import su.svn.showcase.domain.UserRole;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static su.svn.showcase.domain.UUIDEntity.ZERO;

@DisplayName("Class UserRoleShortDto")
class UserRoleBaseDtoTest {
    private final static LocalDateTime NOW = LocalDateTime.now();

    private UserRoleBaseDto userRoleBaseDto;

    private static Validator validator;

    @BeforeAll
    static void setUpValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    @DisplayName("is instantiated")
    void isInstantiatedWithNew() {
        new UserRoleBaseDto();
    }

    @Nested
    @DisplayName("when new with empty constructor")
    class WhenNew {
        @BeforeEach
        void createNew() {
            userRoleBaseDto = new UserRoleBaseDto();
        }

        @Test
        @DisplayName("default values")
        void defaults() {
            assertNotNull(userRoleBaseDto.getId());
            assertThat(userRoleBaseDto).hasFieldOrPropertyWithValue("roleName", null);
            assertThat(userRoleBaseDto).hasFieldOrPropertyWithValue("dateTime", null);
        }

        @Test
        @DisplayName("Setters and getters")
        void testSettersAndGetters () {
            userRoleBaseDto.setRoleName("testRole");
            assertThat(userRoleBaseDto).hasFieldOrPropertyWithValue("roleName", "testRole");
            assertEquals("testRole", userRoleBaseDto.getRoleName());

            userRoleBaseDto.setDateTime(NOW);
            assertThat(userRoleBaseDto).hasFieldOrPropertyWithValue("dateTime", NOW);
            assertEquals(NOW , userRoleBaseDto.getDateTime());
        }

        @Test
        @DisplayName("violation on code is null")
        void codeIsNull() {
            final int constraintViolationsSize = 2;
            Set<ConstraintViolation<UserRoleBaseDto>> constraintViolations = validator.validate(userRoleBaseDto);
            assertEquals(constraintViolationsSize, constraintViolations.size());

            Iterator<ConstraintViolation<UserRoleBaseDto>> iterator = constraintViolations.iterator();

            for (int i = 0; i < constraintViolationsSize; ++i) {
                ConstraintViolation<UserRoleBaseDto> next = iterator.next();
                assertEquals("{javax.validation.constraints.NotNull.message}", next.getMessageTemplate());
            }
            assertFalse(iterator.hasNext());
        }
    }

    @Nested
    @DisplayName("when new with all args constructor")
    class WhenNewAllArgsConstructor {
        @BeforeEach
        void createNew() {
            userRoleBaseDto = new UserRoleBaseDto(ZERO, NOW, "testRole");
        }

        @Test
        @DisplayName("is instantiated partial constructor")
        void isInstantiatedWithNew() {
            userRoleBaseDto = new UserRoleBaseDto(NOW, "testRole");
            assertThat(userRoleBaseDto).hasFieldOrPropertyWithValue("roleName", "testRole");
            assertThat(userRoleBaseDto).hasFieldOrPropertyWithValue("dateTime", NOW);
        }

        @Test
        @DisplayName("is instantiated with builder")
        void isInstantiatedWithBuilder() {
            userRoleBaseDto = UserRoleBaseDto.builder().id(ZERO).dateTime(NOW).roleName("testRole").build();
            assertThat(userRoleBaseDto).hasFieldOrPropertyWithValue("id", ZERO);
            assertThat(userRoleBaseDto).hasFieldOrPropertyWithValue("roleName", "testRole");
            assertThat(userRoleBaseDto).hasFieldOrPropertyWithValue("dateTime", NOW);
        }

        @Test
        @DisplayName("initialized values")
        void defaults() {
            assertThat(userRoleBaseDto).hasFieldOrPropertyWithValue("id", ZERO);
            assertThat(userRoleBaseDto).hasFieldOrPropertyWithValue("roleName", "testRole");
            assertThat(userRoleBaseDto).hasFieldOrPropertyWithValue("dateTime", NOW);
        }

        @Test
        @DisplayName("Equals and hashCode")
        void testEqualsAndHashCode() {
            assertNotEquals(new UserRoleBaseDto(), userRoleBaseDto);
            UserRoleBaseDto expected = new UserRoleBaseDto(ZERO, NOW, "testRole");
            assertEquals(expected.hashCode(), userRoleBaseDto.hashCode());
            assertEquals(expected, userRoleBaseDto);
        }

        @Test
        @DisplayName("The length of string from toString is great than zero")
        void testToString() {
            assertTrue(userRoleBaseDto.toString().length() > 0);
        }

        @Test
        @DisplayName("Update entity by DTO")
        void update() {
            UserRole expected1 = new UserRole(ZERO, NOW, "testRole", null);
            assertEquals(expected1, userRoleBaseDto.update(new UserRole()));

            UserLogin userLogin = UserLogin.builder().id(ZERO).login("testLogin").dateTime(NOW).build();
            Map<String, Object> values = new HashMap<String, Object>() {{
                put("dateTime", NOW);
                put("userLogin", userLogin);
            }};
            UserRole expected2 = new UserRole(ZERO, NOW, "testRole", userLogin);
            assertEquals(expected2, userRoleBaseDto.update(new UserRole(), values));
        }

        @Test
        @DisplayName("Instantiated DTO by entity")
        void instantiatedEntity() {
            UserRole entity = new UserRole();
            entity.setId(ZERO);
            entity.setRoleName("testRole");
            entity.setDateTime(NOW);
            UserRoleBaseDto expected = new UserRoleBaseDto(ZERO, NOW, "testRole");
            assertEquals(expected, userRoleBaseDto);
        }
    }
}
//EOF
