/*
 * This file was last modified at 2020.03.22 22:22 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * MethodUtil.java
 * $Id$
 */

package su.svn.showcase.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class MethodUtil {
    public static final String GET = "get";
    public static final String SET = "set";
    public static final String IS = "is";

    public static boolean isValidGetter(Method method, Field field) {
        return method != null && method.getReturnType().equals(field.getType());
    }

    public static boolean isGetter(Method method) {
        return (method.getName().startsWith(GET) || method.getName().startsWith(IS))
            && method.getParameterCount() == 0
            && ! method.getReturnType().equals(void.class)
            && ! Modifier.isVolatile(method.getModifiers());
    }

    public static boolean isValidSetter(Method method, Field field) {
        return method != null && method.getParameterTypes()[0].equals(field.getType());
    }

    public static boolean isSetter(Method method) {
        return method.getName().startsWith(SET)
            && method.getParameterCount() == 1
            && method.getReturnType().equals(void.class)
            && ! Modifier.isVolatile(method.getModifiers());
    }

    public static String methodName(String fieldName, int index, String prefix) {
        StringBuilder sb = new StringBuilder(prefix);
        sb.append(fieldName);
        sb.setCharAt(index, Character.toUpperCase(sb.charAt(index)));

        return sb.toString();
    }
}
