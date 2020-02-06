/*
 * This file was last modified at 2020.02.06 22:26 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * ValidateUtil.java
 * $Id$
 */

package su.svn.utils;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Iterator;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ValidateUtil {

    private static Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    public static <T> Iterator<ConstraintViolation<T>> isNull(int constraintViolationsSize, T dto) {
        Set<ConstraintViolation<T>> constraintViolations = validator.validate(dto);
        assertEquals(constraintViolationsSize, constraintViolations.size());

        Iterator<ConstraintViolation<T>> iterator = constraintViolations.iterator();

        for (int i = 0; i < constraintViolationsSize; ++i) {
            ConstraintViolation<T> next = iterator.next();
            assertEquals("{javax.validation.constraints.NotNull.message}", next.getMessageTemplate());
        }
        return iterator;
    }
}
//EOF
