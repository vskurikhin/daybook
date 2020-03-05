/*
 * This file was last modified at 2020.03.03 20:33 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * AbstractCrudService.java
 * $Id$
 */

package su.svn.showcase.services.impl;

import org.slf4j.Logger;
import su.svn.showcase.domain.DBEntity;
import su.svn.showcase.dto.Dto;
import su.svn.showcase.utils.StringUtil;

import java.util.Objects;
import java.util.UUID;

abstract class AbstractCrudService {

    abstract Logger getLogger();

    <D extends Dto> D validateId(D dto) {
        Objects.requireNonNull(dto.getId());
        return dto;
    }
    <D extends Dto<UUID>> D checkAndSetId(D dto) {
        if (dto.getId() == null) {
            UUID id = UUID.randomUUID();
            dto.setId(id);
        }
        return dto;
    }

    <T extends Dto<String>> String getOrGenerateStringKey(T entity) {
        return entity.getId() != null ? entity.getId() : StringUtil.generateStringId();
    }

    <T extends Dto<UUID>> UUID getOrGenerateUuidKey(T entity) {
        return entity.getId() != null ? entity.getId() : UUID.randomUUID();
    }

    private <K> void errorLoggin(K e) {
        getLogger().error("Error with : {}", e);
    }

    private <K, T extends DBEntity<K>> void errorLoggin(T entity) {
        K id = null;
        Class<?> c = null;
        if (entity != null) {
            id = entity.getId();
            c = entity.getClass();
        }
        getLogger().error("Error with DBEntity : {} and id : {}", c, id);
    }
}
//EOF
