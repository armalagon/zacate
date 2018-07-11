package com.zacate.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 *
 * @author Armando Alaniz
 * @version 1.0
 * @since 1.0
 */
public abstract class NumberUtils {

    public static final BigDecimal BD_ONE_HUNDRED = new BigDecimal(100);

    public static boolean isZero(final BigDecimal num) {
        return num != null && num.compareTo(BigDecimal.ZERO) == 0;
    }

    public static boolean isDifferentFromZero(final BigDecimal num) {
        return !isZero(num);
    }

    public static boolean isOne(final BigDecimal num) {
        return num != null && num.compareTo(BigDecimal.ONE) == 0;
    }

    public static boolean isDifferentFromOne(final BigDecimal num) {
        return !isOne(num);
    }

    public static int getDecimalPart(final BigDecimal num) {
        return getDecimalPart(num, 2);
    }

    public static int getDecimalPart(final BigDecimal num, final int scale) {
        if (scale <= 0) {
            throw new IllegalArgumentException("The scale must be a positive number");
        }

        int decimalPart = -1;

        if (num == null || isZero(num) || isOne(num)) {
            return decimalPart;
        }

        final BigDecimal remainder = num.remainder(BigDecimal.ONE);
        if (isDifferentFromZero(remainder)) {
            final int tenToPowerOf = (int) Math.pow(10, scale);
            return remainder.multiply(new BigDecimal(tenToPowerOf)).setScale(0, RoundingMode.HALF_UP).intValue();
        } else {
            return -1;
        }
    }

}
