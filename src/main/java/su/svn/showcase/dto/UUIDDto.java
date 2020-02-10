/*
 * This file was last modified at 2020.02.09 19:15 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * UUIDDto.java
 * $Id$
 */

package su.svn.showcase.dto;

import su.svn.showcase.domain.UUIDEntity;

import javax.validation.constraints.NotNull;
import java.util.Objects;
import java.util.UUID;

/**
 * Base DTO with UUID key.
 *
 * @author Victor N. Skurikhin
 */
abstract class UUIDDto implements Dto<UUID> {

    @NotNull
    private UUID id;

    public UUIDDto() {
        this.id = UUIDEntity.ZERO;

    }

    public UUIDDto(UUID id) {
        this.id = id;
    }

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public void setId(UUID id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UUIDDto uuidDto = (UUIDDto) o;
        return Objects.equals(id, uuidDto.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "{id=" + id + '}';
    }
}
//EOF
