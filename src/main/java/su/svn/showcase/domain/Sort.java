/*
 * This file was last modified at 2020.04.23 22:31 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * Sort.java
 * $Id$
 */

package su.svn.showcase.domain;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(value = ElementType.FIELD)
@Retention(value = RetentionPolicy.RUNTIME)
public @interface Sort {
    boolean decrease() default false;
    String[] cluster() default {};
}
//EOF