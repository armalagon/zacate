package com.zacate.bean;

import com.zacate.i18n.LocalizedConstants;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Objects;

/**
 *
 * @author Armando Alaniz
 * @version 1.0
 * @since 1.0
 */
public abstract class Reflections {

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

    public static String getParentPackageName(String packageName) {
        Objects.requireNonNull(packageName, "packageName");
        return packageName.substring(0, packageName.lastIndexOf(LocalizedConstants.DOT));
    }

}
