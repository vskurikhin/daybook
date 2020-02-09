/*
 * This file was last modified at 2020.02.09 19:14 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * StringDto.java
 * $Id$
 */

package su.svn.showcase.dto;

import su.svn.showcase.domain.StringEntity;

import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * Base DTO with String key.
 *
 * @author Victor N. Skurikhin
 */
abstract class StringDto implements Dto<String> {

    @NotNull
    private String id;

    public StringDto() {
        this.id = StringEntity.ZERO;

    }

    public StringDto(String id) {
        this.id = id;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StringDto stringDto = (StringDto) o;
        return Objects.equals(id, stringDto.id);
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
