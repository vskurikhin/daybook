/*
 * This file was last modified at 2020.04.06 22:03 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * FieldUtil.java
 * $Id$
 */

package su.svn.showcase.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.*;
import java.util.*;
import java.util.function.Consumer;

public class FieldUtil {
    public static boolean isTransientOrStatic(Field field) {
        return Modifier.isTransient(field.getModifiers())
            || Modifier.isStatic(field.getModifiers());
    }

    public static boolean isSimpleObject(Object value) {
        return value instanceof Boolean
            || value instanceof Byte
            || value instanceof Character
            || value instanceof Short
            || value instanceof Integer
            || value instanceof Long
            || value instanceof Float
            || value instanceof Double
            || value instanceof BigInteger
            || value instanceof BigDecimal
            || value instanceof UUID
            || value instanceof String
            || value instanceof Class
            || value instanceof TimeZone
            || value instanceof Currency
            || value instanceof Date
            || value instanceof Calendar
            || value instanceof Duration
            || value instanceof Instant
            || value instanceof LocalDateTime
            || value instanceof LocalDate
            || value instanceof LocalTime
            || value instanceof OffsetDateTime
            || value instanceof OffsetTime
            || value instanceof ZonedDateTime;
            // TODO || value instanceof java.sql.Date
            // TODO || value instanceof Time
            // TODO || value instanceof Timestamp
    }

    public static  <T> void updateIfNotNull(Consumer<T> consumer, T o) {
        if (o != null) consumer.accept(o);
    }
}
