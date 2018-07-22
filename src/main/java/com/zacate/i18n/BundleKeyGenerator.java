package com.zacate.i18n;

import static com.zacate.i18n.LocalizedConstants.*;

/**
 *
 * @author Armando Alaniz
 * @version 1.0
 * @since 1.0
 */
public abstract class BundleKeyGenerator {

    public static String createKeyUsing(final String key, final Class<?> clazz) {
        if (key.startsWith(clazz.getName())) {
            return key;
        }

        final StringBuilder fqkn = new StringBuilder(clazz.getName());
        fqkn.append(DOT);
        fqkn.append(key);
        return fqkn.toString();
    }

    public static String createKeyAsReservedParameter(final String key, final Class<?> clazz) {
        final String fqkn = createKeyUsing(key, clazz);
        final StringBuilder rkn = new StringBuilder(fqkn.length() + 2);
        rkn.append(LEFT_BRACE);
        rkn.append(fqkn);
        rkn.append(RIGHT_BRACE);
        return rkn.toString();
    }

    public static String expandReservedParameterUsing(final String key, final Class<?> clazz) {
        return key.replace(EMPTY_BRACKETS, clazz.getName());
    }

}
