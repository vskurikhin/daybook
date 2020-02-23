/*
 * This file was last modified at 2020.02.22 19:31 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * RecordTypesEnum.java
 * $Id$
 */

package su.svn.showcase.dto;

import su.svn.showcase.utils.MapUtil;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public enum RecordTypesEnum {

    NEWS_ENTRY_BASE(NewsEntryBaseDto.class.getSimpleName()),
    NEWS_ENTRY_FULL(NewsEntryFullDto.class.getSimpleName());

    private static final Map<Class<?>, RecordTypesEnum> map = new MapUtil.Builder<Class<?>, RecordTypesEnum>()
            .add(NEWS_ENTRY_BASE, NewsEntryBaseDto.class)
            .add(NEWS_ENTRY_FULL, NewsEntryFullDto.class)
            .build();

    private static final Set<String> stringValues;

    static {
        stringValues = Arrays.stream(RecordTypesEnum.values())
                .map(recordTypesEnum -> recordTypesEnum.value)
                .collect(Collectors.toSet());
    }

    private String value;

    RecordTypesEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static boolean isValid(String name) {
        return stringValues.contains(name);
    }

    public static RecordTypesEnum getRecordType(Class<?> tClass) {
        return map.get(tClass);
    }
}
