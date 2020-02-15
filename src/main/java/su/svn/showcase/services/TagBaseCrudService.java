/*
 * This file was last modified at 2020.02.14 15:57 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * TagBaseCrudService.java
 * $Id$
 */

package su.svn.showcase.services;

import su.svn.showcase.dto.TagBaseDto;

import java.util.List;

public interface TagBaseCrudService extends TagCrudService {

    @Override
    List<TagBaseDto> readRange(int start, int size);
}
