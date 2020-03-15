/*
 * This file was last modified at 2020.03.15 20:31 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * NewsLinksDtoEnum.java
 * $Id$
 */

package su.svn.showcase.dto;

import su.svn.showcase.utils.MapUtil;

import java.util.Map;

public enum NewsLinksDtoEnum {

    NewsLinksBaseDto(NewsLinksBaseDto.class.getSimpleName());

    private static final Map<Class<?>, NewsLinksDtoEnum> map = new MapUtil.Builder<Class<?>, NewsLinksDtoEnum>()
            .key(NewsLinksBaseDto.class).value(NewsLinksBaseDto)
            .unmodifiableMap();

    private String value;

    NewsLinksDtoEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static NewsLinksDtoEnum getRecordType(Class<?> tClass) {
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
