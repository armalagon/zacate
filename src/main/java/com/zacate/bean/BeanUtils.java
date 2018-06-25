package com.zacate.bean;

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
public abstract class BeanUtils {

    public static boolean typeDeclaredInterfaceAsSuperType(final Class<?> clazz, final Class<?> interfaceTypeToSearch) {
        Objects.requireNonNull(clazz, "clazz");
        Objects.requireNonNull(interfaceTypeToSearch, "interfaceTypeToSearch");

        if (!interfaceTypeToSearch.isInterface()) {
            throw new IllegalArgumentException("The [interfaceTypeToSearch] argument must be an Interface Type");
        }
        if (clazz == interfaceTypeToSearch) {
            throw new IllegalArgumentException("Both arguments are referencing the same Interface Type");
        }

        return Arrays.asList(clazz.getInterfaces()).contains(interfaceTypeToSearch);
    }

    public static boolean typeDeclaredOneInterfaceAsSuperType(final Class<?> clazz, final Class<?>... interfaces) {
        Objects.requireNonNull(clazz, "clazz");
        Objects.requireNonNull(interfaces, "interfaces");

        for (Class<?> type : interfaces) {
            if (typeDeclaredInterfaceAsSuperType(clazz, type)) {
                return true;
            }
        }

        return false;
    }

    public static Type[] getTypeArgumentsFromGenericInterface(final Class<?> clazz, final Class<?> interfaceTypeToSearch) {
        if (!typeDeclaredInterfaceAsSuperType(clazz, interfaceTypeToSearch)) {
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

}
