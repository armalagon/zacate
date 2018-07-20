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

    public static String decimalFormat(final long number, final String pattern, final Locale locale) {
        return getDecimalFormatFor(pattern, locale).format(number);
    }

    public static String decimalFormat(final BigDecimal number, final String pattern, final Locale locale) {
        Objects.requireNonNull(number, "number");
        return getDecimalFormatFor(pattern, locale).format(number);
    }

    public static String dateFormat(final Date date, final String pattern, final Locale locale) {
        Objects.requireNonNull(date, "date");
        Objects.requireNonNull(pattern, "pattern");
        Objects.requireNonNull(locale, "locale");
        SimpleDateFormat sdf = new SimpleDateFormat(pattern, locale);
        sdf.setLenient(false);
        return sdf.format(date);
    }

    public static Date dateParse(final String strDate, final String pattern) throws ParseException {
        Objects.requireNonNull(strDate, "strDate");
        Objects.requireNonNull(pattern, "pattern");
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        sdf.setLenient(false);
        return sdf.parse(strDate);
    }

    public static String dateFormat(final LocalDate date, final String pattern, final Locale locale) {
        Objects.requireNonNull(date, "date");
        Objects.requireNonNull(pattern, "pattern");
        Objects.requireNonNull(locale, "locale");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern, locale);
        return date.format(formatter);
    }

    public static String dateFormat(final LocalDate date, final DateTimeFormatter formatter, final Locale locale) {
        Objects.requireNonNull(date, "date");
        Objects.requireNonNull(formatter, "formatter");
        Objects.requireNonNull(locale, "locale");
        return date.format(formatter);
    }

    public static String dateFormat(final LocalDateTime date, final String pattern, final Locale locale) {
        Objects.requireNonNull(date, "date");
        Objects.requireNonNull(pattern, "pattern");
        Objects.requireNonNull(locale, "locale");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern, locale);
        return date.format(formatter);
    }

    public static String dateFormat(final LocalDateTime date, final DateTimeFormatter formatter, final Locale locale) {
        Objects.requireNonNull(date, "date");
        Objects.requireNonNull(formatter, "formatter");
        Objects.requireNonNull(locale, "locale");
        return date.format(formatter);
    }

}
