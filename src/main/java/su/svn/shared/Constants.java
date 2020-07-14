/*
 * This file was last modified at 2020.07.11 21:08 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * Constants.java
 * $Id$
 */

package su.svn.shared;

public final class Constants
{
    public static final boolean RELEASE = false;

    public static final java.lang.String DEV_LOGIN = "ADMIN@DOMAIN";

    public static final String UPLOAD_DIRECTORY = "upload";
    public static final String UNZIP_DIRECTORY = "resources/main/html";
    public static final String DEFAULT_FILENAME = "default.file";

    public static final int MEMORY_THRESHOLD = 1024 * 1024 * 3;
    public static final int MAX_FILE_SIZE = 1024 * 1024 * 40;
    public static final int MAX_REQUEST_SIZE = 1024 * 1024 * 50;

    public static class Db
    {
        public static final java.lang.String PERSISTENCE_UNIT_NAME = "PgPU";

        private Db() {}
    }

    public static final class Types {

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

        private Types() {}
    }

    private Constants() {}
}
//EOF
