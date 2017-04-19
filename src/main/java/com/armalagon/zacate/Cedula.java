package com.armalagon.zacate;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

/**
 * Wrapper del valor de una cedula. A partir de una cadena, se valida su estructura y solo si es correcta, se
 * obtienen las secciones que la componen (codigo del municipio, fecha de nacimiento, consecutivo y digito
 * verificador), en caso contrario se inicializan a {@code null}.
 *
 * @author aalaniz
 * @version 1.0
 * @since 1.0
 */
public final class Cedula {

    private static final int LENGTH = 14;
    private static final char[] DIGITS = "ABCDEFGHJKLMNPQRSTUVWXY".toCharArray();
    private static final char HYPHEN = '-';
    private static final int[] DISTRICT_INDEX = {0, 3};
    private static final int[] BIRTHDAY_INDEX = {3, 9};
    private static final int[] CONSECUTIVE_INDEX = {9, 13};
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
            this.district = raw.substring(DISTRICT_INDEX[0], DISTRICT_INDEX[1]);
            this.birthday = raw.substring(BIRTHDAY_INDEX[0], BIRTHDAY_INDEX[1]);
            this.consecutive = raw.substring(CONSECUTIVE_INDEX[0], CONSECUTIVE_INDEX[1]);
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

    private long parseAsLong(String value) throws NumberFormatException {
        String numeric = value.substring(0, LENGTH - 1);
        return Long.parseLong(numeric);
    }

    private boolean isDate(String value) {
        // En los proximos anios se necesitaria considerar tambien el 20
        String _birthday = value.substring(0, 4) + "19" + value.substring(4);
        try {
            BIRTHDAY_FORMATTER.parse(_birthday);
            return true;
        } catch (DateTimeParseException dtpe) {
            return false;
        }
    }

    private char calculateDigit(long numeric) {
        int index = (int) (numeric % DIGITS.length);
        return DIGITS[index];
    }

    private boolean validate() {
        long numeric;
        String date;

        if (raw == null || raw.trim().length() != LENGTH) {
            return false;
        }
        try {
            numeric = parseAsLong(raw);
        } catch (NumberFormatException nfe) {
            return false;
        }
        date = raw.substring(BIRTHDAY_INDEX[0], BIRTHDAY_INDEX[1]);
        if (!isDate(date)) {
            return false;
        }
        if (calculateDigit(numeric) != Character.toUpperCase(raw.charAt(LENGTH - 1))) {
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
}
