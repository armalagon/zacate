package com.zacate.identifier;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.Objects;
import java.util.function.UnaryOperator;

/**
 * Wrapper del valor de una cedula. A partir de una cadena, se valida su estructura y solo si es correcta, se
 * obtienen las secciones que la componen (codigo del municipio, fecha de nacimiento, consecutivo y digito
 * verificador) y se inicializan sus respectivos atributos, en caso contrario se inicializan a {@code null}.
 *
 * @author aalaniz
 * @version 1.0
 * @since 1.0
 */
public final class NicaraguanNationalId {

    private static final int LENGTH = 14;
    private static final char[] DIGITS = "ABCDEFGHJKLMNPQRSTUVWXY".toCharArray();
    private static final char HYPHEN = '-';
    private static final DateTimeFormatter BIRTHDAY_FORMATTER = DateTimeFormatter.ofPattern("ddMMuuuu")
            .withResolverStyle(ResolverStyle.STRICT);

    private final String raw;
    private final String district;
    private final String birthday;
    private final String consecutive;
    private final Character digit;
    private final String formatted;

    private NicaraguanNationalId(final String raw, final boolean isValid) {
        this.raw = raw;

        if (isValid) {
            this.district = raw.substring(0, 3);
            this.birthday = raw.substring(3, 9);
            this.consecutive = raw.substring(9, 13);
            this.digit = raw.charAt(LENGTH - 1);
            this.formatted = this.district + HYPHEN + this.birthday + HYPHEN + this.consecutive + this.digit;
        } else {
            this.district = null;
            this.birthday = null;
            this.consecutive = null;
            this.digit = null;
            this.formatted = null;
        }
    }

    public String getRaw() {
        return raw;
    }

    public boolean isValid() {
        return district != null && birthday != null && consecutive != null && digit != null;
    }

    public String getDistrict() {
        return district;
    }

    public String getBirthday() {
        return birthday;
    }

    public String getConsecutive() {
        return consecutive;
    }

    public Character getDigit() {
        return digit;
    }

    public String getFormatted() {
        return formatted;
    }

    public String getFormatted(final UnaryOperator<String> formatter) {
        Objects.requireNonNull(formatter);
        if (isValid()) {
            return formatter.apply(raw);
        } else {
            return null;
        }
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 37 * hash + Objects.hashCode(this.raw);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof NicaraguanNationalId)) {
            return false;
        }
        final NicaraguanNationalId other = (NicaraguanNationalId) obj;
        if (!Objects.equals(this.raw, other.raw)) {
            return false;
        }
        return true;
    }

    private static boolean validate(final String value) {
        final long number;
        final int index;
        final String date;
        final String _birthday;

        if (value == null || value.trim().length() != LENGTH) {
            return false;
        }
        try {
            number = Long.parseLong(value.substring(0, LENGTH - 1));
        } catch (NumberFormatException nfe) {
            return false;
        }
        date = value.substring(3, 9);
        // En los proximos anios se necesitaria considerar tambien el 20YY
        _birthday = date.substring(0, 4) + "19" + date.substring(4);
        try {
            BIRTHDAY_FORMATTER.parse(_birthday);
        } catch (DateTimeParseException dtpe) {
            return false;
        }
        index = (int) (number % DIGITS.length);
        if (DIGITS[index] != Character.toUpperCase(value.charAt(LENGTH - 1))) {
            return false;
        }
        return true;
    }

    public static NicaraguanNationalId of(final String value) {
        final boolean isValid = validate(value);
        return new NicaraguanNationalId(value, isValid);
    }
}
