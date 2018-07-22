package com.zacate.jsf;

import java.util.Objects;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author Armando Alaniz
 * @version 1.0
 * @since 1.0
 */
public abstract class Converters {

    public static Class<?> targetModelType(final Converter converter) {
        return Objects.requireNonNull(converter, "converter").getClass().getAnnotation(FacesConverter.class).forClass();
    }

}
