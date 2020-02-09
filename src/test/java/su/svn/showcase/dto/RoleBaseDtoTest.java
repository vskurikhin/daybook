/*
 * This file was last modified at  by Victor N. Skurikhin.
 */

package su.svn.showcase.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import su.svn.showcase.domain.Role;
import su.svn.utils.ValidateUtil;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static su.svn.showcase.domain.UUIDEntity.ZERO;

@DisplayName("Class RoleDto")
class RoleBaseDtoTest {

    private final static LocalDateTime NOW = LocalDateTime.now();

    private RoleBaseDto roleBaseDto;

    @Test
    @DisplayName("is instantiated")
    void isInstantiatedWithNew() {
        new RoleBaseDto();
    }

    @Nested
    @DisplayName("when new with empty constructor")
    class WhenNew {
        @BeforeEach
        void createNew() {
            roleBaseDto = new RoleBaseDto();
        }

        @Test
        @DisplayName("default values")
        void defaults() {
            assertNotNull(roleBaseDto.getId());
            assertThat(roleBaseDto).hasFieldOrPropertyWithValue("roleName", null);
        }

        @Test
        @DisplayName("Setters and getters")
        void testSettersAndGetters () {
            roleBaseDto.setRoleName("testRole");
            assertThat(roleBaseDto).hasFieldOrPropertyWithValue("roleName", "testRole");
            assertEquals("testRole", roleBaseDto.getRoleName());
        }

        @Test
        @DisplayName("violation on code is null")
        void codeIsNull() {
            assertFalse(ValidateUtil.isNull(1, roleBaseDto).hasNext());
        }
    }

    @Nested
    @DisplayName("when new with all args constructor")
    class WhenNewAllArgsConstructor {
        @BeforeEach
        void createNew() {
            roleBaseDto = new RoleBaseDto(ZERO, "testRole");
        }

        @Test
        @DisplayName("is instantiated partial constructor")
        void isInstantiatedWithNew() {
            roleBaseDto = new RoleBaseDto("testRole");
            assertThat(roleBaseDto).hasFieldOrPropertyWithValue("roleName", "testRole");
        }

        @Test
        @DisplayName("is instantiated with builder")
        void isInstantiatedWithBuilder() {
            roleBaseDto = RoleBaseDto.builder().id(ZERO).roleName("testRole").build();
            assertThat(roleBaseDto).hasFieldOrPropertyWithValue("id", ZERO);
            assertThat(roleBaseDto).hasFieldOrPropertyWithValue("roleName", "testRole");
        }

        @Test
        @DisplayName("Equals and hashCode")
        void testEqualsAndHashCode() {
            assertNotEquals(new RoleBaseDto(), roleBaseDto);
            RoleBaseDto expected = new RoleBaseDto(ZERO, "testRole");
            assertEquals(expected.hashCode(), roleBaseDto.hashCode());
            assertEquals(expected, roleBaseDto);
        }

        @Test
        @DisplayName("The length of string from toString is great than zero")
        void testToString() {
            assertTrue(roleBaseDto.toString().length() > 0);
        }

        @Test
        @DisplayName("Update entity by DTO")
        void update() {
            Role expected = new Role(ZERO, "testRole");
            assertEquals(expected, roleBaseDto.update(new Role()));
        }

        @Test
        @DisplayName("Instantiated DTO by entity")
        void instantiatedEntity() {
            Role entity = new Role(ZERO, "testRole");
            RoleBaseDto expected = new RoleBaseDto(entity);
            assertEquals(expected, roleBaseDto);
        }
    }
}
//EOF
