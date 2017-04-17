package com.armalagon.zacate;

/**
 * Convertir un numero a su valor en letras.
 *
 * @author aalaniz
 * @version 1.0
 * @since 1.0
 */
public interface Number2LetterConverter {

    String toLetter() throws NumberConversionException;
}
