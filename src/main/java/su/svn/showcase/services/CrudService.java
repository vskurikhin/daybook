/*
 * This file was last modified at 2020.02.14 09:48 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * TagStorageService.java
 * $Id$
 */

package su.svn.showcase.services;

import su.svn.showcase.dto.Dto;

import java.util.List;

public interface CrudService<K, D extends Dto<K>> {

    void create(D dto);

    D readById(K id);

    List<D> readRange(int start, int size);

    void update(D dto);

    void delete(K id);

    int count();
}
