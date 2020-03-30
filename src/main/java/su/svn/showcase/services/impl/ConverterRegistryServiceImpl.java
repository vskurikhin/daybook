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
import su.svn.showcase.services.ConverterRegistryService;

import javax.ejb.Stateless;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

@Stateless
public class ConverterRegistryServiceImpl implements ConverterRegistryService {

    EntityConverterMap entityConverterMap = new EntityConverterMap();

    @Override
    public void put(Class<? extends DBEntity<?>> eClass, Class<? extends Dto<?>> dClass, EntityConverter<?, ?, ?> conv) {
        DtoConverterMap dtoConverterMap = entityConverterMap.get(eClass);
        if (dtoConverterMap == null) {
            dtoConverterMap = new DtoConverterMap();
        }
        dtoConverterMap.put(dClass, conv);
        entityConverterMap.put(eClass, dtoConverterMap);
    }

    @Override
    public EntityConverter<?, ?, ?> get(Class<? extends DBEntity<?>> eClass, Class<? extends Dto<?>> dClass) {
        DtoConverterMap dtoConverterMap = entityConverterMap.get(eClass);
        if (dtoConverterMap == null) {
            return null;
        }
        return dtoConverterMap.get(dClass);
    }

    private static class DtoConverterMap {
        private final Map<Class<? extends Dto<?>>, EntityConverter<?, ?, ?>> map = new ConcurrentHashMap<>();

        public EntityConverter<?, ?, ?> get(Class<? extends Dto<?>> key) {
            return map.get(key);
        }

        public EntityConverter<?, ?, ?> put(Class<? extends Dto<?>> key, EntityConverter<?, ?, ?> value) {
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
        private final Map<Class<?>, DtoConverterMap> map = new ConcurrentHashMap<>();

        public DtoConverterMap get(Class<? extends DBEntity<?>> key) {
            return map.get(key);
        }

        public DtoConverterMap put(Class<? extends DBEntity<?>> key, DtoConverterMap value) {
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
