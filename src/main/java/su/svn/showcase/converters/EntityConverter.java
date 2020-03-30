/*
 * This file was last modified at 2020.03.22 22:56 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * EntityConverter.java
 * $Id$
 */

package su.svn.showcase.converters;

import su.svn.showcase.domain.DBEntity;
import su.svn.showcase.dto.Dto;

import javax.annotation.Nonnull;

public interface EntityConverter<K, E extends DBEntity<K>, D extends Dto<K>> {

    /**
     * TODO
     *
     * @param entity
     * @return
     */
    D convert(@Nonnull E entity);

    /**
     * TODO
     *
     * @param dto
     * @return
     */
    E convert(@Nonnull D dto);
}
