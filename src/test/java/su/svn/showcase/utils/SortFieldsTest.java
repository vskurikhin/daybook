/*
 * This file was last modified at 2020.04.24 21:39 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * SortFieldsTest.java
 * $Id$
 */

package su.svn.showcase.utils;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import su.svn.showcase.domain.Record;

import static org.junit.jupiter.api.Assertions.*;

class SortFieldsTest {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testToString() {
        SortFields test = new SortFields(Record.class);
        System.out.println("test = " + test);
    }
}