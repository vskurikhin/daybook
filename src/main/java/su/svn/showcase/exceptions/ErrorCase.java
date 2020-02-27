/*
 * This file was last modified at 2020.02.27 18:02 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * ErrorCase.java
 * $Id$
 */

package su.svn.showcase.exceptions;

import org.slf4j.Logger;
import org.slf4j.helpers.FormattingTuple;
import org.slf4j.helpers.MessageFormatter;
import su.svn.showcase.dto.UserOnlyLoginBaseDto;

public class ErrorCase extends RuntimeException {
    private ErrorCase(String message) {
        super(message);
    }

    private ErrorCase(String message, Throwable throwable) {
        super(message, throwable);
    }

    public static RuntimeException open(String message) {
        throw new ErrorCase(message);
    }

    public static RuntimeException open(String message, Throwable throwable) {
        throw new ErrorCase(message, throwable);
    }

    public static RuntimeException open(String format, Object... arguments) {
        FormattingTuple formattingTuple = MessageFormatter.arrayFormat(format, arguments);
        return open(formattingTuple.getMessage(), formattingTuple.getThrowable());
    }

    public static RuntimeException open(Logger logger, String format, Object... arguments) {
        logger.error(format, arguments);
        FormattingTuple formattingTuple = MessageFormatter.arrayFormat(format, arguments);
        return open(formattingTuple.getMessage(), formattingTuple.getThrowable());
    }

    public static RuntimeException bad(String msg, String value) {
        return open("Bad {} '{}'!", msg, value);
    }

    public static RuntimeException notFound() {
        return open("Not found!");
    }

    public static RuntimeException unknownType(String type) {
        return open("Unknown type '{}'!", type);
    }

    public static RuntimeException doesntEquals(String msg, Object value1, Object value2) {
        String sValue1 = value1 != null ? value1.toString() : "null";
        String sValue2 = value2 != null ? value2.toString() : "null";
        return open("{} '{}' doesn't equal '{}', either!", msg, sValue1, sValue2);
    }

    public static RuntimeException unsupportedOperation(Class<?> aClass) {
        return open("Unsupported operation for {}", aClass.getName());
    }
}
