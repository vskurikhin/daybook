/*
 * This file was last modified at 2020.02.05 22:13 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * DBEntity.java
 * $Id$
 */

package su.svn.showcase.domain;

import javax.validation.constraints.NotNull;

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

    /**
     * Sets the key for the entity.
     *
     * @param id - the key.
     */
    // void setId(@NotNull T id);
}
