package com.armalagon.zacate;

/**
 *
 * @author aalaniz
 * @version 1.0
 * @since 1.0
 */
public class NumberConversionException extends Exception {

    public NumberConversionException(int number) {
        super("No se puede convertir el numero " + number);
    }
}
