/*
 * This file was last modified at 2020.03.21 10:35 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * RecordTypesEnum.java
 * $Id$
 */

package su.svn.showcase.dto;

import su.svn.showcase.utils.MapUtil;

import java.util.Map;

public enum RecordTypesEnum {

    NewsEntryBaseDto(NewsEntryBaseDto.class.getSimpleName()),
    NewsEntryFullDto(NewsEntryFullDto.class.getSimpleName()),
    NewsLinksBaseDto(NewsLinksBaseDto.class.getSimpleName()),
    ArticleBaseDto(ArticleBaseDto.class.getSimpleName()),
    ArticleFullDto(ArticleFullDto.class.getSimpleName());

    private static final Map<Class<?>, RecordTypesEnum> map
        = new MapUtil.Builder<Class<?>, RecordTypesEnum>()
        .key(NewsEntryBaseDto.class).value(NewsEntryBaseDto)
        .key(NewsEntryFullDto.class).value(NewsEntryFullDto)
        .key(NewsLinksBaseDto.class).value(NewsLinksBaseDto)
        .key(NewsLinksBaseDto.class).value(NewsLinksBaseDto)
        .key(NewsLinksBaseDto.class).value(NewsLinksBaseDto)
        .unmodifiableMap();

    private String value;

    RecordTypesEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static RecordTypesEnum getRecordType(Class<?> tClass) {
        return map.get(tClass);
    }

    public static boolean containsValue(String name) {
        try {
            return map.containsValue(valueOf(name));
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
