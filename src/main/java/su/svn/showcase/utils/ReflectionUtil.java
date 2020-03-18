/*
 * This file was last modified at 2020.03.17 22:27 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * ReflectionUtil.java
 * $Id$
 */

package su.svn.showcase.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ReflectionUtil {

    private static String GET = "get";
    private static String SET = "set";
    private static String IS = "is";

    public static Map<String, BiConsumer<Object, Object>> setterBiConsumers(Class sClass) {
        return setterBiConsumers(setters(sClass));
    }

    public static Map<String, Method> setters(Class aClass) {
        Map<String, Method> methods = Arrays.stream(aClass.getMethods())
                .peek(System.out::println) // TODO remove
                .filter(ReflectionUtil::isSetter)
                .peek(System.out::println) // TODO remove
                .collect(Collectors.toMap(Method::getName, Function.identity()));
        Map<String, Method> result = new HashMap<>();

        for (Field field : aClass.getDeclaredFields()) {
            if ( ! isTransientOrStatic(field)) {
                String methodName = methodName(field.getName(), 3, SET);
                Method setter = methods.get(methodName);
                if (isValidSetter(setter, field)) {
                    System.out.println("methodName = " + methodName); // TODO remove
                    result.put(field.getName(), setter);
                }
            }
        }
        return result;
    }

    public static Map<String, BiConsumer<Object, Object>> setterBiConsumers(Map<String, Method> map) {
        Map<String, BiConsumer<Object, Object>> result = new HashMap<>();
        for(Map.Entry<String, Method> entry : map.entrySet()) {
            result.put(entry.getKey(), setterBiConsumer(entry.getValue()));
        }
        return result;
    }

    public static Map<String, Function<Object, Object>> getterFunctions(Class aClass) {
        return getterFunctions(getters(aClass));
    }

    public static Map<String, Method> getters(Class aClass) {
        Map<String, Method> methods = Arrays.stream(aClass.getDeclaredMethods())
                .peek(System.out::println) // TODO remove
                .filter(ReflectionUtil::isGetter)
                .peek(System.out::println) // TODO remove
                .collect(Collectors.toMap(Method::getName, Function.identity()));
        Map<String, Method> result = new HashMap<>();

        for (Field field : aClass.getDeclaredFields()) {
            if ( ! isTransientOrStatic(field)) {
                System.out.println("field = " + field); // TODO remove
                String methodName = methodName(field.getName(), 3, GET);
                System.out.println("methodName = " + methodName); // TODO remove
                Method getter = methods.get(methodName);
                if (isValidGetter(getter, field)) {
                    result.put(field.getName(), getter);
                } else {
                    methodName = methodName(field.getName(), 2, IS);
                    System.out.println("methodName = " + methodName); // TODO remove
                    getter = methods.get(methodName);
                    if (isValidGetter(getter, field)) {
                        result.put(field.getName(), getter);
                    }
                }
            }
        }
        return result;
    }

    public static Map<String, Function<Object, Object>> getterFunctions(Map<String, Method> map) {
        Map<String, Function<Object, Object>> result = new HashMap<>();
        for(Map.Entry<String, Method> entry : map.entrySet()) {
            result.put(entry.getKey(), getterFunction(entry.getValue()));
        }
        return result;
    }

    private static boolean isValidSetter(Method method, Field field) {
        return method != null && method.getParameterTypes()[0].equals(field.getType());
    }

    private static boolean isSetter(Method method) {
        return method.getName().startsWith(SET)
                && method.getParameterCount() == 1
                && method.getReturnType().equals(void.class)
                && ! Modifier.isVolatile(method.getModifiers());
    }

    private static BiConsumer<Object, Object> setterBiConsumer(Method setter) {
        return (o, value) -> {
            try {
                setter.invoke(o, value);
            } catch (Exception e) {
                e.printStackTrace(); // TODO
            }
        };
    }

    private static boolean isValidGetter(Method method, Field field) {
        return method != null && method.getReturnType().equals(field.getType());
    }

    private static Function<Object, Object> getterFunction(Method getter) {
        return o -> {
            try {
                return getter.invoke(o);
            } catch (Exception e) {
                e.printStackTrace(); // TODO
                return null;
            }
        };
    }

    private static boolean isGetter(Method method) {
        return (method.getName().startsWith(GET) || method.getName().startsWith(IS))
                && method.getParameterCount() == 0
                && ! method.getReturnType().equals(void.class)
                && ! Modifier.isVolatile(method.getModifiers());
    }

    private static boolean isTransientOrStatic(Field field) {
        return Modifier.isTransient(field.getModifiers())
                || Modifier.isStatic(field.getModifiers());
    }

    private static String methodName(String fieldName, int index, String prefix) {
        StringBuilder sb = new StringBuilder(prefix);
        sb.append(fieldName);
        sb.setCharAt(index, Character.toUpperCase(sb.charAt(index)));

        return sb.toString();
    }
}
