package com.armalagon.zacate;

/**
 * Convertir un numero a su valor en letras.
 *
 * @author aalaniz
 * @version 1.0
 * @since 1.0
 */
public interface Number2LetterConverter {

    int NUMBER_MIN = 0;
    int NUMBER_MAX = 999_999_999;

    String toLetter() throws NumberConversionException;

    static boolean isOutOfBounds(int number) {
        return !(number >= NUMBER_MIN && number <= NUMBER_MAX);
    }
}
