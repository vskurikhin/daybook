/*
 * This file was last modified at 2020.02.06 22:28 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * StringUtilTest.java
 * $Id$
 */

package su.svn.showcase.utils;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StringUtilTest {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void generateStringId() {
        String test1 = StringUtil.generateStringId();
        Assertions.assertNotNull(test1);
        Assertions.assertTrue(test1.length() > 10);
    }

    @Test
    void generateTagId() {
        String test1 = StringUtil.generateTagId("");
        Assertions.assertNotNull(test1);
        Assertions.assertEquals(16, test1.length());
        String test2 = StringUtil.generateTagId("0123456789abcdef");
        Assertions.assertNotNull(test2);
        Assertions.assertEquals(16, test2.length());
    }
}