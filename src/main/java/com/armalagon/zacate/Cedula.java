package com.armalagon.zacate;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.Objects;

/**
 * Wrapper del valor de una cedula. A partir de una cadena, se valida su estructura y solo si es correcta, se
 * obtienen las secciones que la componen (codigo del municipio, fecha de nacimiento, consecutivo y digito
 * verificador) y se inicializan sus respectivos atributos, en caso contrario se inicializan a {@code null}.
 *
 * @author aalaniz
 * @version 1.0
 * @since 1.0
 */
public final class Cedula {

    private static final int LENGTH = 14;
    private static final char[] DIGITS = "ABCDEFGHJKLMNPQRSTUVWXY".toCharArray();
    private static final char HYPHEN = '-';
    private static final DateTimeFormatter BIRTHDAY_FORMATTER = DateTimeFormatter.ofPattern("ddMMuuuu")
            .withResolverStyle(ResolverStyle.STRICT);

    private final String raw;
    private final boolean valid;
    private final String district;
    private final String birthday;
    private final String consecutive;
    private final Character digit;
    private final String formatted;

    public Cedula(String raw) {
        this.raw = raw;
        this.valid = validate();

        if (this.valid) {
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

    private boolean validate() {
        long number;
        int index;
        String date;
        String _birthday;

        if (raw == null || raw.trim().length() != LENGTH) {
            return false;
        }
        try {
            number = Long.parseLong(raw.substring(0, LENGTH - 1));
        } catch (NumberFormatException nfe) {
            return false;
        }
        date = raw.substring(3, 9);
        // En los proximos anios se necesitaria considerar tambien el 20YY
        _birthday = date.substring(0, 4) + "19" + date.substring(4);
        try {
            BIRTHDAY_FORMATTER.parse(_birthday);
        } catch (DateTimeParseException dtpe) {
            return false;
        }
        index = (int) (number % DIGITS.length);
        if (DIGITS[index] != Character.toUpperCase(raw.charAt(LENGTH - 1))) {
            return false;
        }
        return true;
    }

    public String getRaw() {
        return raw;
    }

    public boolean isValid() {
        return valid;
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
        if (!(obj instanceof Cedula)) {
            return false;
        }
        final Cedula other = (Cedula) obj;
        if (!Objects.equals(this.raw, other.raw)) {
            return false;
        }
        return true;
    }
}
