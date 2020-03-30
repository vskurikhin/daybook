/*
 * This file was last modified at 2020.03.30 09:46 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * ConverterFabricService.java
 * $Id$
 */

package su.svn.showcase.services;

import su.svn.showcase.converters.EntityConverter;
import su.svn.showcase.domain.DBEntity;
import su.svn.showcase.dto.Dto;

public interface ConverterRegistryService {

    /**
     * TODO
     * @param converter
     * @param eClass
     * @param dClass
     */
    void put(EntityConverter<?, DBEntity<?>, Dto<?>> converter, Class<DBEntity<?>> eClass, Class<Dto<?>> dClass);

    /**
     * TODO
     * @param eClass
     * @param dClass
     * @return
     */
    EntityConverter<?, DBEntity<?>, Dto<?>> get(Class<DBEntity<?>> eClass, Class<Dto<?>> dClass);
}
