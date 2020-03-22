/*
 * This file was last modified at 2020.03.22 22:46 by Victor N. Skurikhin.
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
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
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

    private static Map<String, Method> getters(Class aClass) {
        Map<String, Method> methods = Arrays.stream(aClass.getDeclaredMethods())
            .filter(MethodUtil::isGetter)
            .peek(Getters::logTrace) // TODO remove
            .collect(Collectors.toMap(Method::getName, Function.identity()));
        Map<String, Method> result = new HashMap<>();

        for (Field field : aClass.getDeclaredFields()) {
            if ( ! FieldUtil.isTransientOrStatic(field)) {
                LOGGER.trace("field = {}", field); // TODO remove
                String methodName = MethodUtil.name(field.getName(), MethodUtil.GETTER);
                LOGGER.trace("methodName = {}", methodName); // TODO remove
                Method getter = methods.get(methodName);
                if (MethodUtil.isValidGetter(getter, field)) {
                    result.put(field.getName(), getter);
                } else {
                    methodName = MethodUtil.name(field.getName(), MethodUtil.IS);
                    LOGGER.trace("methodName = {}", methodName); // TODO remove
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
