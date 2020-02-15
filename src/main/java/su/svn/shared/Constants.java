/*
 * This file was last modified at 2020.02.15 14:30 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * Constants.java$
 * $Id$
 */

package su.svn.shared;

public final class Constants
{
    public static class Db
    {
        public static final java.lang.String PERSISTENCE_UNIT_NAME = "PgPU";

        private Db() {}
    }

    public static final class Long
    {
        public static final long ZERO = 0;

        private Long() {}
    }

    public static final class String
    {

        public static final java.lang.String ZERO = "00000000" + "00000000";

        private String() {}
    }

    public static final class UUID
    {
        public static final java.util.UUID ZERO = new java.util.UUID(0, 0);

        private UUID() {}
    }

    private Constants() {}
}
//EOF
