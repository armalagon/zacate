package com.zacate.util;

/**
 *
 * @author Armando Alaniz
 * @version 1.0
 * @since 1.0
 */
public abstract class StringUtils {

    public static final String EMPTY = "";

    public static String emptyIfNull(final String value) {
        return value != null ? value : EMPTY;
    }

}
