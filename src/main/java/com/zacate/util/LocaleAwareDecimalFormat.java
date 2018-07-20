package com.zacate.util;

import java.math.BigDecimal;
import java.util.Locale;

/**
 *
 * @author Armando Alaniz
 * @version 1.0
 * @since 1.0
 */
public interface LocaleAwareDecimalFormat {

    String format(final long number, final Locale locale);

    String format(final BigDecimal number, final Locale locale);

}
