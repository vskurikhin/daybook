/*
 * This file was last modified at 2020.03.31 12:51 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * KeyMarkedSet.java
 * $Id$
 */

package su.svn.showcase.utils;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class KeyMarkedSet {

    private final Set<Long> longSet = new HashSet<>();

    private final Set<String> stringSet = new HashSet<>();

    private final Set<UUID> uuidSet = new HashSet<>();


    public boolean contains(Object o) {
        if (o instanceof String) {
            return stringSet.contains(o);
        } else if (o instanceof Long) {
            return longSet.contains(o);
        } else if (o instanceof UUID) {
            return uuidSet.contains(o);
        }
        return false;
    }

    public boolean add(Long aLong) {
        return longSet.add(aLong);
    }

    public boolean add(String s) {
        return stringSet.add(s);
    }

    public boolean add(UUID uuid) {
        return uuidSet.add(uuid);
    }
}
