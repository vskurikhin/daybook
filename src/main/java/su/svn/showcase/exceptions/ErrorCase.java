package su.svn.showcase.exceptions;

import org.slf4j.Logger;
import org.slf4j.helpers.FormattingTuple;
import org.slf4j.helpers.MessageFormatter;

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

    public static RuntimeException open(Logger logger, String format, Object... arguments) {
        logger.error(format, arguments);
        FormattingTuple formattingTuple = MessageFormatter.arrayFormat(format, arguments);
        return open(formattingTuple.getMessage(), formattingTuple.getThrowable());
    }
}
