/*
 * This file was last modified at 2020.04.12 15:34 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * NewsEntryDtoEnum.java
 * $Id$
 */

package su.svn.showcase.dto.enums;

import su.svn.showcase.dto.NewsEntryBaseDto;
import su.svn.showcase.dto.NewsEntryFullDto;
import su.svn.showcase.utils.MapUtil;

import java.util.Map;

public enum NewsEntryDtoEnum {

    NewsEntryBaseDto(su.svn.showcase.dto.NewsEntryBaseDto.class.getSimpleName()),
    NewsEntryFullDto(su.svn.showcase.dto.NewsEntryFullDto.class.getSimpleName());

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
