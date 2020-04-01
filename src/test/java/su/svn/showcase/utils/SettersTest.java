/*
 * This file was last modified at 2020.03.31 12:10 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * SettersTest.java
 * $Id$
 */

package su.svn.showcase.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;

class SettersTest {

    Setters setters;
    C c;

    @BeforeEach
    void setUp() {
        setters = new Setters(C.class);
        c = new C();
    }

    @Test
    void get() throws InvocationTargetException, IllegalAccessException {
        setters.get("b").invoke(c, (byte) 13);
        Assertions.assertEquals((byte) 13, c.b);

        setters.get("i").invoke(c, Integer.MAX_VALUE);
        Assertions.assertEquals(Integer.MAX_VALUE, c.i);

        setters.get("s").invoke(c, "test");
        Assertions.assertEquals("test", c.s);
    }

    @Test
    void getBiConsumer() {
        setters.getBiConsumer("b").accept(c, (byte) 13);
        Assertions.assertEquals((byte) 13, c.b);

        setters.getBiConsumer("i").accept(c, Integer.MAX_VALUE);
        Assertions.assertEquals(Integer.MAX_VALUE, c.i);

        setters.getBiConsumer("s").accept(c, "test");
        Assertions.assertEquals("test", c.s);
    }

    @Test
    void forEach() {
        setters.forEach((s, bi) -> {
            switch (s) {
                case "b":
                    bi.accept(c, (byte) 13);
                    break;
                case "i":
                    bi.accept(c, Integer.MAX_VALUE);
                    break;
                case "s":
                    bi.accept(c, "test");
                    break;
            }
        });
        Assertions.assertEquals((byte) 13, c.b);
        Assertions.assertEquals(Integer.MAX_VALUE, c.i);
        Assertions.assertEquals("test", c.s);
    }

    static class C {
        byte b;
        int i;
        String s;

        public void setB(byte b) {
            this.b = b;
        }

        public void setI(int i) {
            this.i = i;
        }

        public void setS(String s) {
            this.s = s;
        }
    }
}