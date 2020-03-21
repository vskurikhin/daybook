/*
 * This file was last modified at 2020.03.21 19:24 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * LinkBaseCrudService.java
 * $Id$
 */

package su.svn.showcase.services;

import su.svn.showcase.dto.LinkBaseDto;

import java.util.UUID;

public interface LinkBaseCrudService extends CrudService<UUID, LinkBaseDto> {
}
