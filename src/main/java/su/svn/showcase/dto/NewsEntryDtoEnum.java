/*
 * This file was last modified at 2020.03.01 00:04 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * NewsEntryDtoEnum.java
 * $Id$
 */

package su.svn.showcase.dto;

import su.svn.showcase.utils.MapUtil;

import java.util.Map;

public enum NewsEntryDtoEnum {

    NewsEntryBaseDto(NewsEntryBaseDto.class.getSimpleName()),
    NewsEntryFullDto(NewsEntryFullDto.class.getSimpleName());

    private static final Map<Class<?>, NewsEntryDtoEnum> map = new MapUtil.Builder<Class<?>, NewsEntryDtoEnum>()
            .key(NewsEntryBaseDto.class).value(NewsEntryBaseDto)
            .key(NewsEntryFullDto.class).value(NewsEntryFullDto)
            .unmodifiableMap();

    private String value;

    NewsEntryDtoEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static NewsEntryDtoEnum getRecordType(Class<?> tClass) {
        return map.get(tClass);
    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public static boolean containsValue(String name) {
        try {
            return map.containsValue(valueOf(name));
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
