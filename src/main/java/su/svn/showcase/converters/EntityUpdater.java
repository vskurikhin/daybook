/*
 * This file was last modified at 2020.04.05 23:23 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * EntityUpdater.java
 * $Id$
 */

package su.svn.showcase.converters;

import java.util.function.Consumer;

public abstract class EntityUpdater {
    public static  <T> void updateIfNotNull(Consumer<T> consumer, T o) {
        if (o != null) consumer.accept(o);
    }
}
