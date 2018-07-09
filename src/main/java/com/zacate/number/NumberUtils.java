package com.zacate.number;

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

    public static int getDecimalPart(BigDecimal num) {
        int decimalPart = -1;

        if (num == null || isZero(num) || isOne(num)) {
            return decimalPart;
        }

        final BigDecimal remainder = num.remainder(BigDecimal.ONE);
        return isDifferentFromZero(remainder) ? remainder.multiply(BD_ONE_HUNDRED).setScale(0, RoundingMode.HALF_UP).intValue() : -1;
    }

}
