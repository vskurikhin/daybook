/*
 * This file was last modified at 2020.02.22 19:31 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * RecordTypesEnum.java
 * $Id$
 */

package su.svn.showcase.dto;

import com.google.common.collect.ImmutableMap;
import su.svn.showcase.domain.NewsEntry;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public enum RecordTypesEnum {

    NEWS_ENTRY(NewsEntry.class.getSimpleName());

    private static final ImmutableMap<Class<?>, RecordTypesEnum> map = ImmutableMap.of(
            NewsEntry.class, NEWS_ENTRY,
            NewsEntryBaseDto.class, NEWS_ENTRY,
            NewsEntryFullDto.class, NEWS_ENTRY);

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

    public boolean isValid(String name) {
        return stringValues.contains(name);
    }

    public static RecordTypesEnum getRecordType(Class<?> tClass) {
        return map.get(tClass);
    }
}
