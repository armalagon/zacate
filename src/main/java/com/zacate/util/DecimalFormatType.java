package com.zacate.util;

import java.math.BigDecimal;
import java.util.Locale;

/**
 *
 * @author Armando Alaniz
 * @version 1.0
 * @since 1.0
 */
public enum DecimalFormatType implements LocaleAwareDecimalFormat {

    QUANTITY("#,##0"), AMOUNT("#,##0.00");

    private final String pattern;

    private DecimalFormatType(final String pattern) {
        this.pattern = pattern;
    }

    @Override
    public String format(final long number, final Locale locale) {
        return FormatUtils.formatDecimal(number, pattern, locale);
    }

    @Override
    public String format(final BigDecimal number, final Locale locale) {
        return FormatUtils.formatDecimal(number, pattern, locale);
    }

}
