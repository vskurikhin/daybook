/*
 * This file was last modified at 2020.03.31 20:05 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * ReadyMap.java
 * $Id$
 */

package su.svn.showcase.utils;

import su.svn.showcase.domain.DBEntity;
import su.svn.showcase.dto.Dto;

import java.util.*;

public class ReadyMap {

    private final Map<Long, DBEntity<Long>> longEntities = new HashMap<>();

    private final Map<Long, Dto<Long>> longDtos = new HashMap<>();

    private final Map<String, DBEntity<String>> stringEntities = new HashMap<>();

    private final Map<String, Dto<String>> stringDtos = new HashMap<>();

    private final Map<UUID, DBEntity<UUID>> uuidEntities = new HashMap<>();

    private final Map<UUID, Dto<UUID>> uuidDtos = new HashMap<>();

    public boolean containsKey(Object o) {
        if (o instanceof String) {
            return stringEntities.containsKey(o) || stringDtos.containsKey(o);
        } else if (o instanceof Long) {
            return longEntities.containsKey(o) || longDtos.containsKey(o);
        } else if (o instanceof UUID) {
            return uuidEntities.containsKey(o) || uuidEntities.containsKey(o);
        }
        return false;
    }

    @SuppressWarnings("unchecked")
    public <T extends DBEntity<Long>> T putByLongKey(T entity) {
        return (T) longEntities.put(entity.getId(), entity);
    }

    @SuppressWarnings("unchecked")
    public <T extends Dto<Long>> T putByLongKey(T dto) {
        return (T) longDtos.put(dto.getId(), dto);
    }

    @SuppressWarnings("unchecked")
    public <T extends DBEntity<String>> T putByStringKey(T entity) {
        return (T) stringEntities.put(entity.getId(), entity);
    }

    @SuppressWarnings("unchecked")
    public <T extends Dto<String>> T putByStringKey(T dto) {
        return (T) stringDtos.put(dto.getId(), dto);
    }

    @SuppressWarnings("unchecked")
    public <T extends DBEntity<UUID>> T putByUuidKey(T entity) {
        return (T) uuidEntities.put(entity.getId(), entity);
    }

    @SuppressWarnings("unchecked")
    public <T extends Dto<UUID>> T putByUuidKey(T dto) {
        return (T) uuidDtos.put(dto.getId(), dto);
    }

    public DBEntity<Long> getEntity(Long key) {
        return longEntities.get(key);
    }

    public Dto<Long> getDto(Long key) {
        return longDtos.get(key);
    }

    public DBEntity<String> getEntity(String key) {
        return stringEntities.get(key);
    }

    public Dto<String> getDto(String key) {
        return stringDtos.get(key);
    }

    public DBEntity<UUID> getEntity(UUID key) {
        return uuidEntities.get(key);
    }

    public Dto<UUID> getDto(UUID key) {
        return uuidDtos.get(key);
    }
}
