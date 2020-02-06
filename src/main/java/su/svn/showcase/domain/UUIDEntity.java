/*
 * This file was last modified at 2020.02.05 22:13 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * UUIDEntity.java
 * $Id$
 */

package su.svn.showcase.domain;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;
import java.util.UUID;

/**
 * Base Entity with UUID key
 *
 * @author Victor Skurikhin
 */

// Annotation to allow an entity to inherit properties from a base class.
@MappedSuperclass
public class UUIDEntity implements DBEntity<UUID> {

    public static final UUID ZERO = UUID.fromString("00000000-0000-0000-0000-000000000000");

    @Id
    @NotNull
    private UUID id;

    public UUIDEntity() {
        this.id = UUID.randomUUID();
    }

    public UUIDEntity(@NotNull UUID id) {
        this.id = id;
    }

    @Override
    public UUID getId() {
        return this.id;
    }

    @Override
    public void setId(@NotNull UUID id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if ( ! (obj instanceof UUIDEntity)) {
            return false;
        }
        UUIDEntity other = (UUIDEntity) obj;
        return getId().equals(other.getId());
    }

    @Override
    public String toString() {
        return "{id=" + id + '}';
    }
}
