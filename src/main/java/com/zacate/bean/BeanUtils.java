package com.zacate.bean;

import static com.zacate.i18n.LocalizedConstants.DOT;
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

    public static String getEnumClassnameWithoutDollarSign(final Enum<?> instance) {
        // TODO Consider possible Enum class declared within a nested inner class
        if (instance.getDeclaringClass().getEnclosingClass() != null) {
            String classname = instance.getDeclaringClass().getName();
            final int dollarSign = classname.indexOf('$', instance.getDeclaringClass().getEnclosingClass().getName().length());
            if (dollarSign != -1) {
                char[] classname2Chars = classname.toCharArray();
                classname2Chars[dollarSign] = DOT;
                classname = new String(classname2Chars);
                return classname;
            }
        }

        return instance.getDeclaringClass().getName();
    }

}
