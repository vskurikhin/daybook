/*
 * This file was last modified at 2020.03.01 00:04 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * Dto.java
 * $Id$
 */

package su.svn.showcase.dto;

/**
 * Base interface for Data Transfer Object.
 *
 * @author Victor N. Skurikhin
 */
public interface Dto<K> {

    /**
     * Returns the key as identifier of the DTO.
     *
     * @return - the key.
     */
    K getId();

    /**
     * Sets the key for the entity.
     *
     * @param id - the key.
     */
    void setId(K id);

    /**
     * Returns the DTO type.
     *
     * @return the dto type.
     */
    Class<? extends Dto> getDtoClass();
}
//EOF
