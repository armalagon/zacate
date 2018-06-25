package com.zacate.collection;

import java.util.Arrays;

/**
 *
 * @author Armando Alaniz
 * @version 1.0
 * @since 1.0
 */
public abstract class ArrayUtils {

    public static <T> boolean isEmpty(final T[] array) {
        return array == null || array.length == 0 || Arrays.asList(array).parallelStream().filter(e -> e != null).count() == 0;
    }

}
