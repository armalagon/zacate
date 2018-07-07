package com.zacate.number;

import java.math.BigDecimal;

/**
 *
 * @author Armando Alaniz
 * @version 1.0
 * @since 1.0
 */
public abstract class NumberUtils {

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

}
