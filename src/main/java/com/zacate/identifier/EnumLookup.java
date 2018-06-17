package com.zacate.identifier;

import com.zacate.identifier.NaturalIdentifier;
import java.io.Serializable;

/**
 *
 * @author Armando Alaniz
 * @version 1.0
 * @since 1.0
 */
public abstract class EnumLookup {

    public static <E extends Enum<E> & NaturalIdentifier<T>, T extends Serializable> E findByCode(Class<E> clazz,
            T code) {
        for (E constant : clazz.getEnumConstants()) {
            T constantCode = ((NaturalIdentifier<T>) constant).getCode();

            if (constantCode == null) {
                throw new NullPointerException("A code is required for the element [" + clazz.getName() + '.' + constant.name() + ']');
            }

            if (constantCode.equals(code)) {
                return constant;
            }
        }

        throw new IllegalArgumentException("There is no element of type [" + clazz.getName() + "] with the specified code [" + code + ']');
    }

}
