package com.zacate.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

/**
 *
 * @author Armando Alaniz
 * @version 1.0
 * @since 1.0
 */
public abstract class FormatUtils {

    public static DecimalFormat getDecimalFormatFor(final String pattern, final Locale locale) {
        Objects.requireNonNull(pattern, "pattern");
        Objects.requireNonNull(locale, "locale");
        return new DecimalFormat(pattern, new DecimalFormatSymbols(locale));
    }

    public static String formatDecimal(final long number, final String pattern, final Locale locale) {
        return getDecimalFormatFor(pattern, locale).format(number);
    }

    public static String formatDecimal(final BigDecimal number, final String pattern, final Locale locale) {
        Objects.requireNonNull(number, "number");
        return getDecimalFormatFor(pattern, locale).format(number);
    }

    public static String formatDate(final Date date, final String pattern, final Locale locale) {
        Objects.requireNonNull(date, "date");
        Objects.requireNonNull(pattern, "pattern");
        Objects.requireNonNull(locale, "locale");
        SimpleDateFormat sdf = new SimpleDateFormat(pattern, locale);
        sdf.setLenient(false);
        return sdf.format(date);
    }

    public static String formatDate(final LocalDate date, final String pattern, final Locale locale) {
        Objects.requireNonNull(date, "date");
        Objects.requireNonNull(pattern, "pattern");
        Objects.requireNonNull(locale, "locale");
        return date.format(DateTimeFormatter.ofPattern(pattern, locale));
    }

    public static String formatDate(final LocalDateTime date, final String pattern, final Locale locale) {
        Objects.requireNonNull(date, "date");
        Objects.requireNonNull(pattern, "pattern");
        Objects.requireNonNull(locale, "locale");
        return date.format(DateTimeFormatter.ofPattern(pattern, locale));
    }

    public static Date parseDate(final String strDate, final String pattern) throws ParseException {
        Objects.requireNonNull(strDate, "strDate");
        Objects.requireNonNull(pattern, "pattern");
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        sdf.setLenient(false);
        return sdf.parse(strDate);
    }

}
