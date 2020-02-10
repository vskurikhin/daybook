/*
 * This file was last modified at 2020.02.09 19:11 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * LongDto.java
 * $Id$
 */

package su.svn.showcase.dto;

import su.svn.showcase.domain.LongEntity;

import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * Base DTO with long integer key.
 *
 * @author Victor N. Skurikhin
 */
abstract class LongDto implements Dto<Long> {

    @NotNull
    private Long id;

    public LongDto() {
        this.id = LongEntity.ZERO;

    }

    public LongDto(Long id) {
        this.id = id;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LongDto longDto = (LongDto) o;
        return Objects.equals(id, longDto.id);
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
