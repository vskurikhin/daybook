/*
 * This file was last modified at 2020.04.05 22:45 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * Getters.java
 * $Id$
 */

package su.svn.showcase.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Getters {

    private static final Logger LOGGER = LoggerFactory.getLogger(Getters.class);

    private final Map<String, Method> methodMap;

    private final Map<String, Function<Object, Object>> functionMap;

    public Getters(Class<?> aClass) {
        this.methodMap = Collections.unmodifiableMap(getters(aClass));
        this.functionMap = Collections.unmodifiableMap(getterFunctions(methodMap));
    }

    public Method get(String name) {
        return methodMap.get(name);
    }

    public Function<Object, Object> getFunction(String name) {
        return functionMap.get(name);
    }

    public void forEach(BiConsumer<String, Function<Object, Object>> action) {
        Objects.requireNonNull(action);
        for (Map.Entry<String, Function<Object, Object>> entry : functionMap.entrySet()) {
            String k;
            Function<Object, Object> v;
            try {
                k = entry.getKey();
                v = entry.getValue();
            } catch(IllegalStateException ise) {
                // this usually means the entry is no longer in the map.
                throw new ConcurrentModificationException(ise);
            }
            action.accept(k, v);
        }
    }

    private static Map<String, Method> getters(Class aClass) {
        Map<String, Method> methods = Arrays.stream(aClass.getDeclaredMethods())
            .filter(MethodUtil::isGetter)
            .collect(Collectors.toMap(Method::getName, Function.identity()));
        Map<String, Method> result = new HashMap<>();

        for (Field field : aClass.getDeclaredFields()) {
            if ( ! FieldUtil.isTransientOrStatic(field)) {
                String methodName = MethodUtil.name(field.getName(), MethodUtil.GETTER);
                Method getter = methods.get(methodName);
                if (MethodUtil.isValidGetter(getter, field)) {
                    result.put(field.getName(), getter);
                } else {
                    methodName = MethodUtil.name(field.getName(), MethodUtil.IS);
                    getter = methods.get(methodName);
                    if (MethodUtil.isValidGetter(getter, field)) {
                        result.put(field.getName(), getter);
                    }
                }
            }
        }
        return result;
    }

    private static Map<String, Function<Object, Object>> getterFunctions(Map<String, Method> map) {
        Map<String, Function<Object, Object>> result = new HashMap<>();
        for(Map.Entry<String, Method> entry : map.entrySet()) {
            result.put(entry.getKey(), getterFunction(entry.getValue()));
        }
        return result;
    }

    private static Function<Object, Object> getterFunction(Method getter) {
        return o -> {
            try {
                return getter.invoke(o);
            } catch (Exception e) {
                logError("Getter invoke exception: ", e);
                return null;
            }
        };
    }

    private static void logError(String message, Throwable throwable) {
        LOGGER.error(message, throwable);
    }

    private static void logTrace(Object o) {
        LOGGER.trace(o.toString());
    }
}
