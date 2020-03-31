/*
 * This file was last modified at 2020.03.22 22:46 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * Setters.java
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

public class Setters {

    private static final Logger LOGGER = LoggerFactory.getLogger(Getters.class);

    private final Map<String, Method> methodMap;

    private final Map<String, BiConsumer<Object, Object>> biConsumerMap;

    public Setters(Class<?> aClass) {
        this.methodMap = Collections.unmodifiableMap(setters(aClass));
        this.biConsumerMap = Collections.unmodifiableMap(setterBiConsumers(methodMap));
    }

    public Method get(String name) {
        return methodMap.get(name);
    }

    public BiConsumer<Object, Object> getBiConsumer(String name) {
        return biConsumerMap.get(name);
    }


    public void forEach(BiConsumer<String, BiConsumer<Object, Object>> action) {
        Objects.requireNonNull(action);
        for (Map.Entry<String, BiConsumer<Object, Object>> entry : biConsumerMap.entrySet()) {
            String k;
            BiConsumer<Object, Object> v;
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

    private static Map<String, Method> setters(Class aClass) {
        Map<String, Method> methods = Arrays.stream(aClass.getMethods())
            .filter(MethodUtil::isSetter)
            .peek(Setters::logTrace) // TODO remove
            .collect(Collectors.toMap(Method::getName, Function.identity()));
        Map<String, Method> result = new HashMap<>();

        for (Field field : aClass.getDeclaredFields()) {
            if ( ! FieldUtil.isTransientOrStatic(field)) {
                String methodName = MethodUtil.name(field.getName(), MethodUtil.SETTER);
                Method setter = methods.get(methodName);
                if (MethodUtil.isValidSetter(setter, field)) {
                    LOGGER.trace("methodName = {}", methodName); // TODO remove
                    result.put(field.getName(), setter);
                }
            }
        }
        return result;
    }

    private static Map<String, BiConsumer<Object, Object>> setterBiConsumers(Map<String, Method> map) {
        Map<String, BiConsumer<Object, Object>> result = new HashMap<>();
        for(Map.Entry<String, Method> entry : map.entrySet()) {
            result.put(entry.getKey(), setterBiConsumer(entry.getValue()));
        }
        return result;
    }

    private static BiConsumer<Object, Object> setterBiConsumer(Method setter) {
        return (o, value) -> {
            try {
                setter.invoke(o, value);
            } catch (Exception e) {
                logError("Setter invoke exception: ", e);
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
