package com.zacate.conversion;

/**
 * Convertir un numero a su valor en letras.
 *
 * @author aalaniz
 * @version 1.0
 * @since 1.0
 */
public interface Number2Letter {

    int NUMBER_MIN = 0;
    int NUMBER_MAX = 999_999_999;

    String toLetter();

    static boolean isOutOfBounds(int number) {
        return !(number >= NUMBER_MIN && number <= NUMBER_MAX);
    }
}
