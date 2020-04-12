/*
 * This file was last modified at 2020.04.12 15:34 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * ArticleDtoEnum.java
 * $Id$
 */

package su.svn.showcase.dto.enums;

import su.svn.showcase.dto.jdo.ArticleJdo;
import su.svn.showcase.utils.MapUtil;

import java.util.Map;

public enum ArticleDtoEnum {

    ArticleJdo(ArticleJdo.class.getSimpleName());

    private static final Map<Class<?>, ArticleDtoEnum> map = new MapUtil.Builder<Class<?>, ArticleDtoEnum>()
            .key(ArticleJdo.class).value(ArticleJdo)
            .unmodifiableMap();

    private String value;

    ArticleDtoEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static ArticleDtoEnum getRecordType(Class<?> tClass) {
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
