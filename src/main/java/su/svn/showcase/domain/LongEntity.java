/*
 * This file was last modified at 2020.02.05 22:13 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * LongEntity.java
 * $Id$
 */

package su.svn.showcase.domain;

import su.svn.showcase.utils.LongUtil;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * Base Entity with long integer key
 *
 * @author Victor Skurikhin
 */

// Annotation to allow an entity to inherit properties from a base class.
@MappedSuperclass
public class LongEntity implements DBEntity<Long> {

    public static final long ZERO = 0;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "default_gen")
    private long id;

    public LongEntity() {
        this.id = LongUtil.generateLongId();
    }

    public LongEntity(@NotNull Long id) {
        this.id = id;
    }

    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public void setId(@NotNull Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if ( ! (obj instanceof LongEntity)) {
            return false;
        }
        LongEntity other = (LongEntity) obj;
        return  other.getId() == id;
    }

    @Override
    public String toString() {
        return "{id=" + id + '}';
    }
}
