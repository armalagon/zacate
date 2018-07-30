package com.zacate.util;

import java.util.Arrays;

/**
 *
 * @author Armando Alaniz
 * @version 1.0
 * @since 1.0
 */
public abstract class Arguments {

    public static boolean isEmpty(Object... values) {
        return values == null || values.length == 0 || Arrays.asList(values).parallelStream().filter(e -> e != null).count() == 0;
    }

    public static boolean isNotEmpty(Object... values) {
        return !isEmpty(values);
    }

}
