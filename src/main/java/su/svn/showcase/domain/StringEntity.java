/*
 * This file was last modified at 2020.02.05 22:13 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * StringEntity.java
 * $Id$
 */

package su.svn.showcase.domain;

import su.svn.showcase.utils.StringUtil;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Base Entity with String key
 *
 * @author Victor Skurikhin
 */

// Annotation to allow an entity to inherit properties from a base class.
@MappedSuperclass
public class StringEntity implements Serializable, DBEntity<String> {
    private static final long serialVersionUID = 102L;

    public static final String ZERO = "00000000" + "00000000";

    @Id
    @NotNull
    private String id;

    public StringEntity() {
        this.id = StringUtil.generateStringId();
    }

    public StringEntity(@NotNull String id) {
        this.id = id;
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public void setId(@NotNull String id) {
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
        if ( ! (obj instanceof StringEntity)) {
            return false;
        }
        StringEntity other = (StringEntity) obj;
        return String.valueOf(getId()).equals(other.getId());
    }

    @Override
    public String toString() {
        return "{id=" + id + '}';
    }
}
