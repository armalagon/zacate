package com.zacate.conversion;

import com.zacate.bean.BeanUtils;
import com.zacate.identifier.EnumLookup;
import com.zacate.identifier.NaturalIdentifier;
import com.zacate.identifier.StringNaturalIdentifierLocalizable;
import com.zacate.util.FormatUtils;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 *
 * @author Armando Alaniz
 * @version 1.0
 * @since 1.0
 */
public class DefaultDatatypeConverter extends ReflectionConverter {

    private static final DefaultDatatypeConverter INSTANCE = new DefaultDatatypeConverter();

    protected static final String ERROR_FOR_UNABLE_TO_CONVERT_VALUE = "The value [%s] can't be converted to the requested type [%s]";

    protected final List<String> isoDateFormats = new ArrayList<>();

    public DefaultDatatypeConverter() {
        // ISO Date/Datetime formats supported
        this.isoDateFormats.add("yyyy-MM-dd");
        this.isoDateFormats.add("yyyy-MM-ddTHH:mm:ss");
        this.isoDateFormats.add("yyyy-MM-ddTHH:mm:ss.SSS");
    }

    protected void addISODateFormat(final String dateFormat) {
        this.isoDateFormats.add(dateFormat);
    }

    private Date convertToDate(final String dateValue) throws ParseException {
        ParseException pe = null;
        for (String isoDateFormat : isoDateFormats) {
            try {
                return FormatUtils.dateParse(dateValue, isoDateFormat);
            } catch (ParseException ex) {
                // TODO Log error
                pe = ex;
            }
        }
        if (pe != null) {
            throw pe;
        }
        // This isn't going to happen
        return null;
    }

    @Override
    public <T> T getValue(final String valueToConvert, final Class<T> clazz) {
        Object convertedValue;

        if (clazz.isEnum()) {
            if (BeanUtils.interfaceIsSupertypeOf(clazz, StringNaturalIdentifierLocalizable.class)) {
                convertedValue = EnumLookup.findByCode(clazz, valueToConvert);
                return (T) convertedValue;
            } else if (BeanUtils.interfaceIsSupertypeOf(clazz, NaturalIdentifier.class)) {
                Class<?> codeType = BeanUtils.getTypeArgumentsFromGenericInterface(clazz, NaturalIdentifier.class)[0].getClass();
                Object code = getValue(valueToConvert, codeType);
                convertedValue = EnumLookup.findByCode(clazz, code);
                return (T) convertedValue;
            } else {
                for (T constant : clazz.getEnumConstants()) {
                    String name = ((Enum) constant).name();
                    if (name.equalsIgnoreCase(valueToConvert)) {
                        return constant;
                    }
                }
            }

            throw new UnsupportedOperationException(String.format(ERROR_FOR_UNSUPPORTED_TYPE, clazz.getName()));
        }

        try {
            // Handle special cases as: Date, GregorianCalendar, java.sql.Date, LocalDate, etc...
            // TODO Handle locale
            if (clazz == Date.class) {
                convertedValue = convertToDate(valueToConvert);
                return (T) convertedValue;
            } else if (clazz == java.sql.Date.class) {
                Date date = convertToDate(valueToConvert);
                convertedValue = new java.sql.Date(date.getTime());
                return (T) convertedValue;
            } else if (clazz == java.sql.Timestamp.class) {
                Date date = convertToDate(valueToConvert);
                convertedValue = new java.sql.Timestamp(date.getTime());
                return (T) convertedValue;
            } else if (clazz == GregorianCalendar.class) {
                Date date = convertToDate(valueToConvert);
                GregorianCalendar gc = new GregorianCalendar();
                gc.setTime(date);
                convertedValue = gc;
                return (T) convertedValue;
            } else if (clazz == LocalDate.class) {
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
            throw new IllegalArgumentException(String.format(ERROR_FOR_UNABLE_TO_CONVERT_VALUE, valueToConvert, clazz.getName()), ex);
        }

        // Fallback to default implementation
        return super.getValue(valueToConvert, clazz);
    }

    public static DefaultDatatypeConverter getInstance() {
        return INSTANCE;
    }

}
