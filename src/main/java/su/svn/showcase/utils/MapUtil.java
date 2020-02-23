/*
 * This file was last modified at 2020.02.23 15:28 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * MapUtil.java
 * $Id$
 */

package su.svn.showcase.utils;

import java.util.HashMap;
import java.util.Map;

public class MapUtil {
    public static class Builder<K,V> {
        private final Map<K,V> map = new HashMap<K,V>();

        public Builder<K, V> add(V value, K... keys) {
            for(K key : keys) {
                map.put(key, value);
            }
            return this;
        }

        public Map<K,V> build() {
            return new HashMap<K,V>(map);
        }
//        @SuppressWarnings("Java9CollectionFactory")
//        public Map<K,V> build() {
//            return Collections.unmodifiableMap(new HashMap<K,V>(map));
//        }
    }
}
