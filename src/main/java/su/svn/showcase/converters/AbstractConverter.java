/*
 * This file was last modified at 2020.04.15 00:03 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * AbstractConverter.java
 * $Id$
 */

package su.svn.showcase.converters;

import su.svn.showcase.domain.DBEntity;
import su.svn.showcase.dto.Dto;
import su.svn.showcase.utils.FieldUtil;
import su.svn.showcase.utils.Getters;
import su.svn.showcase.utils.Setters;

import javax.annotation.Nonnull;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Function;

public abstract class AbstractConverter<K, E extends DBEntity<K>, D extends Dto<K>> implements EntityConverter<K, E, D> {

    /**
     * The entityClass fields are protected so that subclasses.
     *
     * @return Entity Class field.
     */
    protected abstract Class<E> getEClass();

    protected abstract Class<D> getDClass();

    protected final Getters entityGetters;

    protected final Setters entitySetters;

    protected final Getters dtoGetters;

    protected final Setters dtoSetters;

    protected AbstractConverter() {
        entityGetters = new Getters(getEClass());
        entitySetters = new Setters(getEClass());
        dtoGetters = new Getters(getDClass());
        dtoSetters = new Setters(getDClass());
    }

    protected D convertByGetter(@Nonnull D dto, @Nonnull E entity) {
        entityGetters.forEach(transitBiConsumer(dto, entity));

        return dto;
    }

    private BiConsumer<String, Function<Object, Object>> transitBiConsumer(D dto, E entity) {
        return (fieldName, getter) -> invokeSetter(getDtoSetter(fieldName), dto, getter.apply(entity));
    }

    protected E convertBySetter(@Nonnull E entity, @Nonnull D dto) {
        Objects.requireNonNull(dto.getId());
        entitySetters.forEach(transitBiConsumer(entity, dto));

        return entity;
    }

    private BiConsumer<String, BiConsumer<Object, Object>> transitBiConsumer(E entity, D dto) {
        return (fieldName, setter) -> {
            Function<Object, Object> getter = getDtoGetter(fieldName);
            if (getter != null) {
                invokeSetter(setter, entity, getter.apply(dto));
            }
        };
    }

    private void invokeSetter(BiConsumer<Object, Object> bi, Object o, Object value) {
        if (bi != null && FieldUtil.isSimpleObject(value)) {
            bi.accept(o, value);
        }
    }

    private Function<Object, Object> getDtoGetter(String fieldName) {
        return dtoGetters.getFunction(fieldName);
    }

    private BiConsumer<Object, Object> getDtoSetter(String fieldName) {
        return dtoSetters.getBiConsumer(fieldName);
    }
}
//EOF

