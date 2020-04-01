/*
 * This file was last modified at 2020.04.01 12:06 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * AbstractConverter.java
 * $Id$
 */

package su.svn.showcase.converters.impl;

import su.svn.showcase.converters.EntityConverter;
import su.svn.showcase.domain.DBEntity;
import su.svn.showcase.dto.Dto;
import su.svn.showcase.utils.FieldUtil;
import su.svn.showcase.utils.Getters;
import su.svn.showcase.utils.ReadyMap;
import su.svn.showcase.utils.Setters;

import javax.annotation.Nonnull;
import java.util.Objects;
import java.util.UUID;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

abstract class AbstractConverter<K, E extends DBEntity<K>, D extends Dto<K>> implements EntityConverter<K, E, D> {

    /**
     * The entityClass fields are protected so that subclasses.
     *
     * @return Entity Class field.
     */
    abstract Class<E> getEClass();

    abstract Class<D> getDClass();

    protected final Getters entityGetters;

    protected final Setters entitySetters;

    protected final Getters dtoGetters;

    protected final Setters dtoSetters;

    AbstractConverter() {
        entityGetters = new Getters(getEClass());
        entitySetters = new Setters(getEClass());
        dtoGetters = new Getters(getDClass());
        dtoSetters = new Setters(getDClass());
    }

    protected D convertByGetter(@Nonnull D dto, @Nonnull E entity) {
        entityGetters.forEach((fieldName, getter) -> invokeSetter(getDtoSetter(fieldName), dto, getter));

        return dto;
    }

    protected E convertBySetter(@Nonnull E entity, @Nonnull D dto) {
        Objects.requireNonNull(dto.getId());
        entitySetters.forEach((fieldName, setter) -> invokeSetter(setter, entity, getDtoGetter(fieldName)));

        return entity;
    }

    @SuppressWarnings("unchecked")
    <R extends DBEntity<Long>, X extends Dto<Long>>
    R convertLong(X entity, ReadyMap ready, BiFunction<X, ReadyMap, R> converter) {
        if (ready.containsKey(entity.getId())) {
            return (R) ready.getDto(entity.getId());
        }
        R result = converter.apply(entity, ready);

        return ready.putByLongKey(result);
    }

    @SuppressWarnings("unchecked")
    <R extends Dto<Long>, X extends DBEntity<Long>>
    R convertLong(X entity, ReadyMap ready, BiFunction<X, ReadyMap, R> converter) {
        if (ready.containsKey(entity.getId())) {
            return (R) ready.getDto(entity.getId());
        }
        R result = converter.apply(entity, ready);

        return ready.putByLongKey(result);
    }

    @SuppressWarnings("unchecked")
    <R extends DBEntity<String>, X extends Dto<String>>
    R convertString(X entity, ReadyMap ready, BiFunction<X, ReadyMap, R> converter) {
        if (ready.containsKey(entity.getId())) {
            return (R) ready.getDto(entity.getId());
        }
        R result = converter.apply(entity, ready);

        return ready.putByStringKey(result);
    }

    @SuppressWarnings("unchecked")
    <R extends Dto<String>, X extends DBEntity<String>>
    R convertString(X entity, ReadyMap ready, BiFunction<X, ReadyMap, R> converter) {
        if (ready.containsKey(entity.getId())) {
            return (R) ready.getDto(entity.getId());
        }
        R result = converter.apply(entity, ready);

        return ready.putByStringKey(result);
    }

    @SuppressWarnings("unchecked")
    <R extends DBEntity<UUID>, X extends Dto<UUID>>
    R convertUuid(X entity, ReadyMap ready, BiFunction<X, ReadyMap, R> converter) {
        if (ready.containsKey(entity.getId())) {
            return (R) ready.getDto(entity.getId());
        }
        R result = converter.apply(entity, ready);

        return ready.putByUuidKey(result);
    }

    @SuppressWarnings("unchecked")
    <R extends Dto<UUID>, X extends DBEntity<UUID>>
    R convertUuid(X entity, ReadyMap ready, BiFunction<X, ReadyMap, R> converter) {
        if (ready.containsKey(entity.getId())) {
            return (R) ready.getDto(entity.getId());
        }
        R result = converter.apply(entity, ready);

        return ready.putByUuidKey(result);
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

