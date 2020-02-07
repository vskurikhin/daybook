/*
 * This file was last modified at 2020.02.07 12:17 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * CollectionUtil.java
 * $Id$
 */

package su.svn.showcase.utils;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class CollectionUtil {
    public static <T> Stream<T> iterableToStream(Iterable<T> iterable) {
        return StreamSupport.stream(iterable.spliterator(), false);
    }

    public static <T> List<T> iterableToList(Iterable<T> iterable) {
        return iterableToStream(iterable).collect(Collectors.toList());
    }
}
//EOF
