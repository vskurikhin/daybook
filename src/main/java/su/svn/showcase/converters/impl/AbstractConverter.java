/*
 * This file was last modified at 2020.03.22 22:59 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * TagConverter.java
 * $Id$
 */

package su.svn.showcase.converters.impl;

import su.svn.showcase.converters.EntityConverter;
import su.svn.showcase.domain.DBEntity;
import su.svn.showcase.dto.Dto;
import su.svn.showcase.utils.FieldUtil;
import su.svn.showcase.utils.Getters;
import su.svn.showcase.utils.Setters;

import javax.annotation.Nonnull;
import javax.ejb.Stateless;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Function;

@Stateless
abstract class AbstractConverter<K, E extends DBEntity<K>, D extends Dto<K>>
    implements EntityConverter<K, E, D> {

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

    protected D convert(@Nonnull D dto, @Nonnull E entity) {
        entityGetters.forEach((fieldName, getter) ->
            invokeSetter(getDtoSetter(fieldName), dto, getter));

        return dto;
    }

    protected E convert(@Nonnull E entity, @Nonnull D dto) {
        Objects.requireNonNull(dto.getId());
        entitySetters.forEach((fieldName, setter) ->
            invokeSetter(setter, entity, getDtoGetter(fieldName)));

        return entity;
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
