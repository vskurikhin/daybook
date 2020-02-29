/*
 * This file was last modified at 2020.03.01 00:04 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * CrudService.java
 * $Id$
 */

package su.svn.showcase.services;

import su.svn.showcase.dto.Dto;

import javax.annotation.Nonnull;
import java.util.List;

public interface CrudService<K, D extends Dto<K>> {

    void create(@Nonnull D dto);

    D readById(@Nonnull K id);

    List<D> readRange(int start, int size);

    void update(@Nonnull D dto);

    void delete(@Nonnull K id);

    int count();
}
//EOF
