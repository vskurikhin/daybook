/*
 * This file was last modified at 2020.02.14 09:48 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * TagStorageService.java
 * $Id$
 */

package su.svn.showcase.services;

import su.svn.showcase.dto.TagDto;

import java.util.List;

public interface TagCrudService {

    void create(TagDto dto);

    TagDto readById(String id);

    List<? extends TagDto> readRange(int start, int size);

    void update(TagDto dto);

    void delete(String id);

    int count();
}
