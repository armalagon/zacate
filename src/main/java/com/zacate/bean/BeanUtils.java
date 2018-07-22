package com.zacate.bean;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Objects;

/**
 *
 * @author Armando Alaniz
 * @version 1.0
 * @since 1.0
 */
public abstract class BeanUtils {

    public static boolean interfaceIsSupertypeOf(final Class<?> clazz, final Class<?> interfaceTypeToSearch) {
        Objects.requireNonNull(clazz, "clazz");
        Objects.requireNonNull(interfaceTypeToSearch, "interfaceTypeToSearch");

        if (!interfaceTypeToSearch.isInterface()) {
            throw new IllegalArgumentException("An Interface Type is required");
        }
        if (clazz == interfaceTypeToSearch) {
            throw new IllegalArgumentException("Both arguments are referencing the same Interface Type");
        }

        return Arrays.asList(clazz.getInterfaces()).contains(interfaceTypeToSearch);
    }

    public static Type[] getTypeArgumentsFromGenericInterface(final Class<?> clazz, final Class<?> interfaceTypeToSearch) {
        if (!interfaceIsSupertypeOf(clazz, interfaceTypeToSearch)) {
            throw new IllegalArgumentException("The type [" + clazz.getName() + "] doesn't implement the interface [" +
                    interfaceTypeToSearch.getName() + ']');
        }

        for (Type type : clazz.getGenericInterfaces()) {
            ParameterizedType pt = (ParameterizedType) type;

            if (pt.getRawType().getClass() == interfaceTypeToSearch) {
                return pt.getActualTypeArguments();
            }
        }

        throw new IllegalArgumentException("The type [" + interfaceTypeToSearch.getName() + "] is not a Generic Interface");
    }

    public static boolean isSomeIntegerType(final Object value) {
        return value instanceof Byte || value instanceof Short || value instanceof Integer || value instanceof Long;
    }

    public static boolean isSomeDateTimeType(final Object value) {
        return value instanceof Date || value instanceof GregorianCalendar || value instanceof java.sql.Date ||
                value instanceof java.sql.Timestamp || value instanceof LocalDate || value instanceof LocalDateTime;
    }

}
