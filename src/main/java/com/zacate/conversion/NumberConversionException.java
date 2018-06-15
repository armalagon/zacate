package com.zacate.conversion;

/**
 *
 * @author aalaniz
 * @version 1.0
 * @since 1.0
 */
public class NumberConversionException extends Exception {

    private static final String ZACATE_ERR_001 = "No se puede convertir el numero %d, esta fuera del rango " +
            "permitido [" + Number2Letter.NUMBER_MIN + ", " + Number2Letter.NUMBER_MAX + "]";

    public NumberConversionException(int number) {
        super(String.format(ZACATE_ERR_001, number));
    }
}
