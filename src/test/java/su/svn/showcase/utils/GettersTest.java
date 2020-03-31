/*
 * This file was last modified at 2020.03.31 11:38 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * GettersTest.java
 * $Id$
 */

package su.svn.showcase.utils;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

class GettersTest {

    Getters getters;
    C c;

    @BeforeEach
    void setUp() {
        getters = new Getters(C.class);
        c = new C();
        c.b = 13;
        c.i = Integer.MAX_VALUE;
        c.s = "test";
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void get() throws InvocationTargetException, IllegalAccessException {
        Method getBMethod = getters.get("b");
        Assertions.assertEquals((byte) 13, getBMethod.invoke(c));

        Method getIMethod = getters.get("i");
        Assertions.assertEquals(Integer.MAX_VALUE, getIMethod.invoke(c));

        Method getSMethod = getters.get("s");
        Assertions.assertEquals("test", getSMethod.invoke(c));
    }

    @Test
    void getFunction() {
        Assertions.assertEquals((byte) 13, getters.getFunction("b").apply(c));
        Assertions.assertEquals(Integer.MAX_VALUE, getters.getFunction("i").apply(c));
        Assertions.assertEquals("test", getters.getFunction("s").apply(c));
    }

    @Test
    void forEach() {
        Set<String> test = new HashSet<>();
        Set<String> expected = new HashSet<String>() {{
            add("13");
            add("test");
            add("2147483647");
        }};
        getters.forEach((s, f) -> test.add(f.apply(c).toString()));
        Assertions.assertEquals(expected, test);
    }

    static class C {
        byte b;
        int i;
        String s;

        public byte getB() {
            return b;
        }

        public int getI() {
            return i;
        }

        public String getS() {
            return s;
        }
    }
}