/*
 * This file was last modified at 2020.02.10 21:35 by Victor N. Skurikhin.
 * This is free and unencumbered software released into the public domain.
 * For more information, please refer to <http://unlicense.org>
 * BigIntegerUtil.java
 * $Id$
 */

package su.svn.showcase.utils;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class BigIntegerUtil {

    public static final BigInteger BILLION = new BigInteger("1000000000");

    public static BigInteger getUTCEpoch(LocalDateTime localDateTime) {
        return BigInteger.valueOf(localDateTime.toEpochSecond(ZoneOffset.UTC));
    }

    public static BigInteger generateBigIntegerId(LocalDateTime now) {
        long nano = now.getNano();
        BigInteger epoch = getUTCEpoch(now);

        return epoch.multiply(BILLION).add(BigInteger.valueOf(nano));
    }
}
//EOF
