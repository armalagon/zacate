package com.zacate.identifier;

import com.zacate.bean.Reflections;
import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author Armando Alaniz
 * @version 1.0
 * @since 1.0
 */
public abstract class EnumLookup {

    private static final String ERROR_FOR_CODE_REQUIRED = "A code is required for the element [%s.%s]";
    private static final String ERROR_FOR_CODE_NOT_FOUND = "There is no element of type [%s] with the specified code [%s]";

    public static <E extends Enum<E> & NaturalIdentifier<T>, T extends Serializable> E findByCode(final Class<E> clazz, final T code) {
        Objects.requireNonNull(clazz, "clazz");
        Objects.requireNonNull(code, "code");

        for (E constant : clazz.getEnumConstants()) {
            T constantCode = ((NaturalIdentifier<T>) constant).getCode();

            if (constantCode == null) {
                throw new NullPointerException(String.format(ERROR_FOR_CODE_REQUIRED, clazz.getName(), constant.name()));
            }

            if (constantCode.equals(code)) {
                return constant;
            }
        }

        throw new IllegalArgumentException(String.format(ERROR_FOR_CODE_NOT_FOUND, clazz, code));
    }

    public static Object findByCode(final Class<?> clazz, final Object code) {
        Objects.requireNonNull(clazz, "clazz");
        Objects.requireNonNull(code, "code");

        if (!clazz.isEnum()) {
            throw new IllegalArgumentException("The type [" + clazz.getName() + "] must be an Enum Type to perform this search");
        }

        Class<?> inferredCodeType = null;
        if (Reflections.interfaceIsSupertypeOf(clazz, StringNaturalIdentifierLocalizable.class)) {
            inferredCodeType = String.class;
        } else if (Reflections.interfaceIsSupertypeOf(clazz, NaturalIdentifier.class)) {
            inferredCodeType = Reflections.getTypeArgumentsFromGenericInterface(clazz, NaturalIdentifier.class)[0].getClass();
        }

        if (inferredCodeType != null) {
            if (code.getClass() != inferredCodeType) {
                throw new IllegalArgumentException("The [code] argument (" + code.getClass().getName() + ") must be a [" +
                        inferredCodeType.getName() + "] type to perform this search");
            }

            for (Object constant : clazz.getEnumConstants()) {
                Object constantCode = ((NaturalIdentifier<?>) constant).getCode();

                if (constantCode == null) {
                    throw new NullPointerException(String.format(ERROR_FOR_CODE_REQUIRED, clazz.getName(), ((Enum) constant).name()));
                }

                if (constantCode.equals(code)) {
                    return constant;
                }
            }

            throw new IllegalArgumentException(String.format(ERROR_FOR_CODE_NOT_FOUND, clazz, code));
        } else {
            throw new IllegalArgumentException("The type [" + clazz.getName() + "] doesn't implement the NaturalIdentifier interface " +
                    "to perform this search");
        }
    }

}
