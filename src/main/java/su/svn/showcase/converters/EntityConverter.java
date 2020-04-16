/*
 * This file was last modified at 2020.04.15 00:03 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * EntityConverter.java
 * $Id$
 */

package su.svn.showcase.converters;

import su.svn.showcase.domain.DBEntity;
import su.svn.showcase.dto.Dto;
import su.svn.showcase.utils.ReadyMap;

import javax.annotation.Nonnull;
import java.util.function.Consumer;

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

    /**
     * TODO
     * @param entity
     * @param ready
     * @return
     */
    D convert(@Nonnull E entity, @Nonnull ReadyMap ready);

    /**
     * TODO
     * @param dto
     * @param ready
     * @return
     */
    E convert(@Nonnull D dto, @Nonnull ReadyMap ready);

    /**
     * TODO
     * @param entity
     * @param dto
     * @return
     */
    E update(@Nonnull E entity, @Nonnull D dto);

    /**
     * TODO
     * @param entity
     * @param dto
     * @param ready
     * @return
     */
    E update(@Nonnull E entity, @Nonnull D dto, @Nonnull ReadyMap ready);

    default <T> void updateIfNotNull(Consumer<T> consumer, T o) {
        if (o != null) consumer.accept(o);
    }
}
