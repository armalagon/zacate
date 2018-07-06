package com.zacate.conversion;

import com.zacate.bean.ReflectionException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author Armando Alaniz
 * @version 1.0
 * @since 1.0
 */
public class ReflectionConverter {

    protected static final String ERROR_FOR_CONSTRUCTOR_INSTANTIATION = "Error instantiating the type %s through the String constructor";
    protected static final String ERROR_FOR_STATIC_FACTORY_INSTANTIATION = "Error instantiating the type %s through the static factory " +
            "method";
    protected static final String ERROR_FOR_UNSUPPORTED_TYPE = "The requested type [%s] is not supported yet";

    protected static final String STRING_FACTORY_METHOD_NAME = "valueOf";

    protected final List<Class<?>> excludedTypes;

    public ReflectionConverter() {
        this.excludedTypes = new ArrayList<>();
        // Date is excluded due to deprecated String constructor
        this.excludedTypes.add(Date.class);
    }

    protected void addExcludedType(final Class<?> clazz) {
        this.excludedTypes.add(Objects.requireNonNull(clazz));
    }

    protected boolean isExcluded(final Class<?> clazz) {
        return excludedTypes.contains(clazz);
    }

    public <T> T getValue(final String valueToConvert, final Class<T> clazz) {
        Objects.requireNonNull(clazz, "clazz");

        if (valueToConvert == null) {
            return (T) valueToConvert;
        }
        if (clazz == String.class) {
            return (T) valueToConvert;
        }
        if (isExcluded(clazz)) {
            throw new IllegalArgumentException(String.format(ERROR_FOR_UNSUPPORTED_TYPE, clazz.getName()));
        }

        Constructor<?> strConstructor;
        Method strFactoryMethod;
        Object convertedValue;

        try {
            strConstructor = clazz.getConstructor(String.class);
            convertedValue = strConstructor.newInstance(valueToConvert);
            return (T) convertedValue;
        } catch (NoSuchMethodException | SecurityException nsmOrSecEx) {
            // TODO Log this error
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            throw new ReflectionException(String.format(ERROR_FOR_CONSTRUCTOR_INSTANTIATION, clazz.getName()), ex);
        }

        try {
            strFactoryMethod = clazz.getMethod(STRING_FACTORY_METHOD_NAME, String.class);
            if (Modifier.isStatic(strFactoryMethod.getModifiers())) {
                convertedValue = strFactoryMethod.invoke(null, valueToConvert);
                return (T) convertedValue;
            }
        } catch (NoSuchMethodException | SecurityException nsmOrSecEx) {
            // TODO Log this error
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            throw new ReflectionException(String.format(ERROR_FOR_STATIC_FACTORY_INSTANTIATION, clazz.getName()), ex);
        }

        throw new IllegalArgumentException(String.format(ERROR_FOR_UNSUPPORTED_TYPE, clazz.getName()));
    }
}
