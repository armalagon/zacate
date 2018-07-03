package com.zacate.conversion;

import com.zacate.bean.BeanUtils;
import com.zacate.identifier.EnumLookup;
import com.zacate.identifier.NaturalIdentifier;
import com.zacate.identifier.StringNaturalIdentifierLocalizable;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

/**
 *
 * @author Armando Alaniz
 * @version 1.0
 * @since 1.0
 */
public class DefaultDatatypeConverter extends ReflectionConverter {

    private static final DefaultDatatypeConverter INSTANCE = new DefaultDatatypeConverter();

    protected static final String ERROR_FOR_UNABLE_TO_CONVERT_VALUE = "The value [%s] can't be converted to the requested type [%s]";
    protected static final String ERROR_FOR_UNSUPPORTED_TYPE = "The requested type [%s] is not supported yet";

    protected static final String STRING_FACTORY_METHOD_NAME = "valueOf";

    private final List<Class<?>> excludedTypes;
    private final List<String> isoDateFormats = new ArrayList<>();

    public DefaultDatatypeConverter() {
        this.excludedTypes = new ArrayList<>();
        // Date is excluded due to deprecated String constructor
        this.excludedTypes.add(Date.class);
        // ISO Date/Datetime formats supported
        this.isoDateFormats.add("yyyy-MM-dd");
        this.isoDateFormats.add("yyyy-MM-ddTHH:mm:ss");
        this.isoDateFormats.add("yyyy-MM-ddTHH:mm:ss.SSS");
    }

    protected void addExcludedType(final Class<?> clazz) {
        this.excludedTypes.add(Objects.requireNonNull(clazz));
    }

    protected void addISODateFormat(final String dateFormat) {
        this.isoDateFormats.add(dateFormat);
    }

    private boolean isExcludedType(final Class<?> clazz) {
        return excludedTypes.contains(clazz);
    }

    private boolean isNotExcludedType(final Class<?> clazz) {
        return !isExcludedType(clazz);
    }

    private Date convertToDate(final String dateValue) throws ParseException {
        ParseException pe = null;
        for (String isoDateFormat : isoDateFormats) {
            try {
                // TODO Remarks: Handle LOCALE
                SimpleDateFormat dateFormatter = new SimpleDateFormat(isoDateFormat);
                dateFormatter.setLenient(false);
                return dateFormatter.parse(dateValue);
            } catch (ParseException ex) {
                // TODO Handle error
                pe = ex;
            }
        }
        if (pe != null) {
            throw pe;
        }
        // This isn't going to happen
        return null;
    }

    public <T> T getValue(final String valueToConvert, final Class<T> clazz) {
        Objects.requireNonNull(clazz, "clazz");

        if (valueToConvert == null || valueToConvert.length() == 0 || valueToConvert.trim().length() == 0) {
            return (T) valueToConvert;
        }

        if (clazz == String.class) {
            return (T) valueToConvert;
        }

        if (clazz.isEnum()) {
            if (BeanUtils.interfaceIsSupertypeOf(clazz, StringNaturalIdentifierLocalizable.class)) {
                return (T) EnumLookup.findByCode(clazz, valueToConvert);
            } else if (BeanUtils.interfaceIsSupertypeOf(clazz, NaturalIdentifier.class)) {
                // TODO Handle conversion of valueToConvert to the proper type
            }

            throw new IllegalArgumentException(String.format(ERROR_FOR_UNSUPPORTED_TYPE, clazz.getName()));
        }

        // TODO Move this logic to ReflectionConverter
        Constructor<?> strConstructor;
        Method strFactoryMethod;
        Object convertedValue;

        try {
            if (isNotExcludedType(clazz)) {
                strConstructor = clazz.getConstructor(String.class);
                convertedValue = strConstructor.newInstance(valueToConvert);
                return (T) convertedValue;
            }
        } catch (NoSuchMethodException ex) {
            // TODO Handle this exception | getConstructor
        } catch (SecurityException ex) {
            // TODO Handle this exception | getConstructor
        } catch (InstantiationException ex) {
            // TODO Handle this exception | newInstance
        } catch (IllegalAccessException ex) {
            // TODO Handle this exception | newInstance
        } catch (IllegalArgumentException ex) {
            // TODO Handle this exception | newInstance
        } catch (InvocationTargetException ex) {
            // TODO Handle this exception | newInstance
        }

        try {
            if (isNotExcludedType(clazz)) {
                strFactoryMethod = clazz.getMethod(STRING_FACTORY_METHOD_NAME, String.class);
                if (Modifier.isStatic(strFactoryMethod.getModifiers())) {
                    convertedValue = strFactoryMethod.invoke(null, valueToConvert);
                    return (T) convertedValue;
                }
            }
        } catch (NoSuchMethodException ex) {
            // TODO Handle this exception | getMethod
        } catch (SecurityException ex) {
            // TODO Handle this exception | getMethod
        } catch (IllegalAccessException ex) {
            // TODO Handle this exception | invoke
        } catch (IllegalArgumentException ex) {
            // TODO Handle this exception | invoke
        } catch (InvocationTargetException ex) {
            // TODO Handle this exception | invoke
        }

        try {
            // Handle special cases as: Date, GregorianCalendar, java.sql.Date, LocalDate, etc...
            if (clazz == Date.class) {
                // TODO Handle locale
                convertedValue = convertToDate(valueToConvert);
                return (T) convertedValue;
            } else if (clazz == java.sql.Date.class) {
                // TODO Handle locale
                Date date = convertToDate(valueToConvert);
                convertedValue = new java.sql.Date(date.getTime());
                return (T) convertedValue;
            } else if (clazz == java.sql.Timestamp.class) {
                // TODO Handle locale
                Date date = convertToDate(valueToConvert);
                convertedValue = new java.sql.Timestamp(date.getTime());
                return (T) convertedValue;
            } else if (clazz == GregorianCalendar.class) {
                // TODO Handle locale
                Date date = convertToDate(valueToConvert);
                GregorianCalendar gc = new GregorianCalendar();
                gc.setTime(date);
                convertedValue = gc;
                return (T) convertedValue;
            } else if (clazz == LocalDate.class) {
                // TODO Handle locale
                Date date = convertToDate(valueToConvert);
                convertedValue = LocalDate.ofEpochDay(date.getTime());
                return (T) convertedValue;
            } else if (clazz == LocalDateTime.class) {
                // TODO Handle locale, TimeZone, ZoneId
                Date date = convertToDate(valueToConvert);
                convertedValue = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
                return (T) convertedValue;
            }
        } catch (ParseException ex) {
            // TODO Handle this exception | parse
        }

        throw new IllegalArgumentException(String.format(ERROR_FOR_UNSUPPORTED_TYPE, clazz.getName()));
    }

    public static DefaultDatatypeConverter getInstance() {
        return INSTANCE;
    }

}
