/*
 * This file was last modified at 2020.03.22 22:22 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * FieldUtil.java
 * $Id$
 */

package su.svn.showcase.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class FieldUtil {
    public static boolean isTransientOrStatic(Field field) {
        return Modifier.isTransient(field.getModifiers())
            || Modifier.isStatic(field.getModifiers());
    }
}
