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

    protected static final String ERROR_FOR_CONSTRUCTOR_INSTANTIATION = "Error while trying to instantiate the type %s using the " +
            "string constructor";
    protected static final String ERROR_FOR_STATIC_FACTORY_INSTANTIATION = "Error while trying to instantiate the type %s using the " +
            "static factory method";
    protected static final String ERROR_FOR_UNSUPPORTED_TYPE = "The requested type [%s] is not supported yet";

    protected static final char COLON = ':';
    protected static final char SPACE = ' ';

    protected static final String STRING_FACTORY_METHOD_NAME = "valueOf";

    private final List<Class<?>> excludedTypes;

    public ReflectionConverter() {
        this.excludedTypes = new ArrayList<>();
        // Date is excluded due to deprecated String constructor
        this.excludedTypes.add(Date.class);
    }

    protected void addExcludedType(final Class<?> clazz) {
        this.excludedTypes.add(clazz);
    }

    protected boolean isExcluded(final Class<?> clazz) {
        return excludedTypes.contains(clazz);
    }

    private ReflectionException createException(Throwable cause, String summary, String detail) {
        StringBuilder newMsg = new StringBuilder(summary);
        newMsg.append(COLON);
        newMsg.append(SPACE);
        newMsg.append(detail);
        return new ReflectionException(newMsg.toString(), cause);
    }

    private void handleReflectionException(Throwable cause, String summary) throws ReflectionException {
        if (cause instanceof SecurityException) {
            throw createException(cause, summary, "couldn't access the string method");
        } else if (cause instanceof InstantiationException) {
            throw createException(cause, summary, "couldn't instantiate an abstract class");
        } else if (cause instanceof IllegalAccessException) {
            throw createException(cause, summary, "couldn't access the string method");
        } else if (cause instanceof IllegalArgumentException) {
            throw createException(cause, summary, "there is something wrong with the String argument");
        } else if (cause instanceof InvocationTargetException) {
            throw createException(cause, summary, "the method threw an exception");
        }
    }

    public <T> T getValue(final String valueToConvert, final Class<T> clazz) {
        Objects.requireNonNull(clazz, "clazz");

        if (isExcluded(clazz)) {
            throw new UnsupportedOperationException(String.format(ERROR_FOR_UNSUPPORTED_TYPE, clazz.getName()));
        }

        if (valueToConvert == null || clazz == String.class) {
            return (T) valueToConvert;
        }

        Object convertedValue;

        try {
            Constructor<?> strConstructor = clazz.getConstructor(String.class);
            convertedValue = strConstructor.newInstance(valueToConvert);
            return (T) convertedValue;
        } catch (NoSuchMethodException ex) {
            // TODO Log this error
        } catch (Exception ex) {
            String msg = String.format(ERROR_FOR_CONSTRUCTOR_INSTANTIATION, clazz.getName());
            handleReflectionException(ex, msg);
        }

        try {
            Method strFactoryMethod = clazz.getMethod(STRING_FACTORY_METHOD_NAME, String.class);
            if (Modifier.isStatic(strFactoryMethod.getModifiers())) {
                convertedValue = strFactoryMethod.invoke(null, valueToConvert);
                return (T) convertedValue;
            }
        } catch (NoSuchMethodException ex) {
            // TODO Log this error
        } catch (Exception ex) {
            String msg = String.format(ERROR_FOR_STATIC_FACTORY_INSTANTIATION, clazz.getName());
            handleReflectionException(ex, msg);
        }

        throw new UnsupportedOperationException(String.format(ERROR_FOR_UNSUPPORTED_TYPE, clazz.getName()));
    }

}
