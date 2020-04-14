/*
 * This file was last modified at 2020.04.14 19:52 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * RecordTypesEnum.java
 * $Id$
 */

package su.svn.showcase.dto.enums;

import su.svn.showcase.dto.jdo.NewsEntryJdo;
import su.svn.showcase.dto.jdo.ArticleJdo;
import su.svn.showcase.dto.jdo.NewsLinksJdo;
import su.svn.showcase.utils.MapUtil;

import java.util.Map;

public enum RecordTypesEnum {

    NewsEntryJdo(NewsEntryJdo.class.getSimpleName()),
    NewsLinksJdo(NewsLinksJdo.class.getSimpleName()),
    ArticleJdo(ArticleJdo.class.getSimpleName());

    private static final Map<Class<?>, RecordTypesEnum> map
        = new MapUtil.Builder<Class<?>, RecordTypesEnum>()
        .key(NewsEntryJdo.class).value(NewsEntryJdo)
        .key(NewsLinksJdo.class).value(NewsLinksJdo)
        .key(ArticleJdo.class).value(ArticleJdo)
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
