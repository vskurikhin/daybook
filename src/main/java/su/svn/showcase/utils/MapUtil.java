/*
 * This file was last modified at 2020.02.24 20:09 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * MapUtil.java
 * $Id$
 */

package su.svn.showcase.utils;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class MapUtil {

    public static class Builder<K, V> {

        private final Map<K, V> map = new HashMap<K, V>();

        public Keys<K, V> key(K key) {
            return new Keys<K, V>(this, key);
        }

        @SafeVarargs
        public final Keys<K, V> keys(K... keys) {
            return new Keys<K, V>(this, keys);
        }

        public Map<K, V> build() {
            return new HashMap<K, V>(map);
        }

        @SuppressWarnings("Java9CollectionFactory")
        public Map<K, V> unmodifiableMap() {
            return Collections.unmodifiableMap(new HashMap<K, V>(map));
        }

        public static <X, Y> Builder<X, Y> create() {
            return new Builder<X, Y>();
        }

        public static class Keys<K, V> {
            private final MapUtil.Builder<K, V> builder;
            private final K[] keys;

            @SafeVarargs
            Keys(MapUtil.Builder<K, V> builder, K ... keys) {
                this.builder = builder;
                this.keys = keys;
            }

            public Builder<K, V> value(V value) {
                for (K key : keys) {
                    this.builder.map.put(key, value);
                }
                return builder;
            }
        }
    }
}
