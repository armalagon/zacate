package com.armalagon.zacate;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 *
 * @author aalaniz
 * @version 1.0
 * @since 1.0
 */
public abstract class Number2LetterAbstractBuilder {

    private static final BigDecimal ONE_HUNDRED = new BigDecimal(100);

    final int number;
    final Integer decimal;
    String currency = null;
    boolean longFormat = false;

    public Number2LetterAbstractBuilder(int number) {
        this.number = number;
        this.decimal = null;
    }

    public Number2LetterAbstractBuilder(BigDecimal number) {
        this.number = number.intValue();
        this.decimal = number
                .setScale(2, RoundingMode.HALF_UP)
                .remainder(BigDecimal.ONE)
                .multiply(ONE_HUNDRED)
                .intValue();
    }

    public Number2LetterAbstractBuilder currency(String currency) {
        this.currency = currency;
        return this;
    }

    public Number2LetterAbstractBuilder longFormat(boolean longFormat) {
        this.longFormat = longFormat;
        return this;
    }

    public abstract Number2Letter createConverter();

    public Number2Letter build() throws NumberConversionException {
        if (Number2Letter.isOutOfBounds(number)) {
            throw new NumberConversionException(number);
        }
        return createConverter();
    }
}
