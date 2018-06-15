package com.zacate.conversion;

import java.math.BigDecimal;

/**
 *
 * @author aalaniz
 * @version 1.0
 * @since 1.0
 */
public class SpanishNumber2LetterBuilder extends Number2LetterAbstractBuilder {

    public SpanishNumber2LetterBuilder(int number) {
        super(number);
    }

    public SpanishNumber2LetterBuilder(BigDecimal number) {
        super(number);
    }

    @Override
    public Number2Letter createConverter() {
        return new SpanishNumber2Letter(this);
    }
}
