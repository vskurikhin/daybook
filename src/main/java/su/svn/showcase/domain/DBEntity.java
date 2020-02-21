/*
 * This file was last modified at 2020.02.16 00:19 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * DBEntity.java$
 * $Id$
 */

package su.svn.showcase.domain;

/**
 * Base interface for entities
 *
 * @author Victor N. Skurikhin
 */

public interface DBEntity<T> {

    /**
     * Returns the key as identifier of the entity.
     *
     * @return - the key.
     */
    T getId();
}
