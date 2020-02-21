/*
 * This file was last modified at 2020.02.15 20:16 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * NotFound.java$
 * $Id$
 */

package su.svn.showcase.exceptions;

public class NotFound extends RuntimeException {
    private NotFound(String message) {
        super(message);
    }

    public static RuntimeException is() {
        return new NotFound("Not Found!");
    }
}
