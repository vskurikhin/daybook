/*
 * This file was last modified at 2020.03.30 09:52 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * ConverterFabricServiceImpl.java
 * $Id$
 */

package su.svn.showcase.services.impl;

import su.svn.showcase.converters.EntityConverter;
import su.svn.showcase.domain.DBEntity;
import su.svn.showcase.dto.Dto;

import javax.ejb.Stateless;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

@Stateless
public class ConverterRegistryServiceImpl {

    EntityConverterMap entityConverterMap = new EntityConverterMap();

    public void put(EntityConverter<?, DBEntity<?>, Dto<?>> converter, Class<DBEntity<?>> eClass, Class<Dto<?>> dClass) {
        DtoConverterMap dtoConverterMap = entityConverterMap.get(eClass);
        if (dtoConverterMap == null) {
            dtoConverterMap = new DtoConverterMap();
        }
        dtoConverterMap.put(dClass, converter);
        entityConverterMap.put(eClass, dtoConverterMap);
    }

    public EntityConverter<?, DBEntity<?>, Dto<?>> get(Class<DBEntity<?>> eClass, Class<Dto<?>> dClass) {
        DtoConverterMap dtoConverterMap = entityConverterMap.get(eClass);
        if (dtoConverterMap == null) {
            return null;
        }
        //noinspection unchecked
        return (EntityConverter<?, DBEntity<?>, Dto<?>>) dtoConverterMap.get(dClass);
    }

    private static class DtoConverterMap {
        private final Map<Class<Dto<?>>, EntityConverter<?, ?, Dto<?>>> map = new ConcurrentHashMap<>();

        public EntityConverter<?, ?, Dto<?>> get(Class<Dto<?>> key) {
            return map.get(key);
        }

        public EntityConverter<?, ?, Dto<?>> put(Class<Dto<?>> key, EntityConverter<?, ?, Dto<?>> value) {
            return map.put(key, value);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            DtoConverterMap that = (DtoConverterMap) o;
            return Objects.equals(map, that.map);
        }

        @Override
        public int hashCode() {
            return Objects.hash(map);
        }

        @Override
        public String toString() {
            return "DtoConverterMap{" +
                    "map=" + map +
                    '}';
        }
    }

    private static class EntityConverterMap {
        private final Map<Class<DBEntity<?>>, DtoConverterMap> map = new ConcurrentHashMap<>();

        public DtoConverterMap get(Class<DBEntity<?>> key) {
            return map.get(key);
        }

        public DtoConverterMap put(Class<DBEntity<?>> key, DtoConverterMap value) {
            return map.put(key, value);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            EntityConverterMap that = (EntityConverterMap) o;
            return Objects.equals(map, that.map);
        }

        @Override
        public int hashCode() {
            return Objects.hash(map);
        }

        @Override
        public String toString() {
            return "EntityConverterMap{" +
                    "map=" + map +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "ConverterFabricServiceImpl{" +
                "entityConverterMap=" + entityConverterMap +
                '}';
    }
}
