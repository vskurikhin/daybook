/*
 * This file was last modified at 2020.04.24 21:39 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * SortFieldsTest.java
 * $Id$
 */

package su.svn.showcase.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import su.svn.showcase.domain.Record;

class SortFieldsTest {

    @BeforeEach
    void setUp() {
    }

    @Test
    void testToString() {
        SortFields test = new SortFields(Record.class);
        Assertions.assertTrue(test.contains(Record.getDefaultOrderMap()));
    }
}