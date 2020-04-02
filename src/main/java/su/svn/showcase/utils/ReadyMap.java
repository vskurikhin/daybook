/*
 * This file was last modified at 2020.04.02 18:19 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * ReadyMap.java
 * $Id$
 */

package su.svn.showcase.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import su.svn.showcase.domain.DBEntity;
import su.svn.showcase.dto.Dto;

import java.util.*;

public class ReadyMap {

    public interface Key {
        Object getKey();
        Class<?> getVClass();
    }

    @Data
    @EqualsAndHashCode
    @AllArgsConstructor
    public static class LongKey implements Key {
        Long key;
        Class<?> vClass;
    }

    @Data
    @EqualsAndHashCode
    @AllArgsConstructor
    public static class StringKey implements Key {
        String key;
        Class<?> vClass;
    }

    @Data
    @EqualsAndHashCode
    @AllArgsConstructor
    public static class UuidKey implements Key {
        UUID key;
        Class<?> vClass;
    }

    private final Map<Key, Object> map = new HashMap<>();

    public boolean containsKey(Key key) {
        System.out.println("map.size() = " + map.size());
        System.out.println("map = " + map);
        return map.containsKey(key);
    }

    public <I, T extends DBEntity<I>> DBEntity<?> put(T entity) {
        I id = entity.getId();
        if (id instanceof String) {
            StringKey key = new StringKey((String) entity.getId(), entity.getClass());
            return (DBEntity<?>) map.put(key, entity);
        } else if (id instanceof Long) {
            LongKey key = new LongKey((Long) entity.getId(), entity.getClass());
            return (DBEntity<?>) map.put(key, entity);
        } else if (id instanceof UUID) {
            UuidKey key = new UuidKey((UUID) entity.getId(), entity.getClass());
            return (DBEntity<?>) map.put(key, entity);
        }
        return null;
    }

    public <I, T extends Dto<I>> Dto<?> put(T dto) {
        I id = dto.getId();
        if (id instanceof String) {
            StringKey key = new StringKey((String) dto.getId(), dto.getClass());
            return (Dto<?>) map.put(key, dto);
        } else if (id instanceof Long) {
            LongKey key = new LongKey((Long) dto.getId(), dto.getClass());
            return (Dto<?>) map.put(key, dto);
        } else if (id instanceof UUID) {
            UuidKey key = new UuidKey((UUID) dto.getId(), dto.getClass());
            return (Dto<?>) map.put(key, dto);
        }
        return null;
    }

    public Object get(Key key) {
        return map.get(key);
    }
}
