package com.armalagon.zacate;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Implementacion para convertir un numero a su valor en letras en el idioma espanol. Se pueden configurar variables adicionales como la
 * moneda y el formato largo para las decenas (por ej: veinticinco o veinte y cinco).
 *
 * @author aalaniz
 * @version 1.0
 * @since 1.0
 */
public final class SpanishNumber2Letter implements Number2LetterConverter {

    private static final int NUMBER_MIN = 0;
    private static final int NUMBER_MAX = 999_999_999;
    private static final int SPECIALS_MIN = 0;
    private static final int SPECIALS_MAX = 15;
    private static final String[] SPECIALS = {"cero", "uno", "dos", "tres", "cuatro", "cinco", "seis", "siete", "ocho", "nueve", "diez",
        "once", "doce", "trece", "catorce", "quince"};
    private static final String[][] TENS = {{"diez", "dieci"}, {"veinte", "veinti"}, {"treinta", "treinti"}, {"cuarenta", "cuarenti"},
        {"cincuenta", "cincuenti"}, {"sesenta", "sesenti"},
        {"setenta", "setenti"}, {"ochenta", "ochenti"},
        {"noventa", "noventi"}};
    private static final String[] HUNDREDS = {"cien", "ciento", "cientos"};
    private static final String[] HUNDRED_SPECIALS = {"quinientos", "setecientos", "novecientos"};
    private static final String ONE = "un";
    private static final String THOUSAND = "mil";
    private static final String[] MILLIONS = {"millon", "millones"};
    private static final String FRACTION_SUFFIX = "/100";
    private static final String FRACTION_WITH = " con ";
    private static final String AND = " y ";

    private final int number;
    private final Integer decimal;
    private final String currency;
    private final boolean longFormat;
    private final boolean throwError;
    private String letter;

    private SpanishNumber2Letter(Number2LetterBuilder builder) {
        this.number = Math.abs(builder.number);
        this.decimal = builder.decimal;
        this.currency = builder.currency;
        this.longFormat = builder.longFormat;
        this.throwError = builder.throwError;
    }

    private String convert(int theNumber) {
        int index;
        int otherNumber;
        StringBuilder sb = new StringBuilder();
        if (theNumber >= SPECIALS_MIN && theNumber <= SPECIALS_MAX) {
            sb.append(SPECIALS[theNumber]);
        } else if (theNumber < 100) {
            otherNumber = theNumber % 10;
            index = theNumber/10;
            if (!longFormat) {
                sb.append(TENS[index - 1][otherNumber > 0 ? 1 : 0]);
            } else {
                sb.append(TENS[index - 1][0]);
                if (otherNumber > 0) {
                    sb.append(AND);
                }
            }
            if (otherNumber > 0) {
                sb.append(SPECIALS[otherNumber]);
            }
        } else if (theNumber < 1000) {
            otherNumber = theNumber % 100;
            index = theNumber/100;
            switch (index) {
                case 1:
                    sb.append(HUNDREDS[otherNumber > 0 ? 1 : 0]);
                    break;
                case 5:
                    sb.append(HUNDRED_SPECIALS[0]);
                    break;
                case 7:
                    sb.append(HUNDRED_SPECIALS[1]);
                    break;
                case 9:
                    sb.append(HUNDRED_SPECIALS[2]);
                    break;
                default:
                    sb.append(SPECIALS[index]).append(HUNDREDS[2]);
                    break;
            }
            if (otherNumber > 0) {
                sb.append(' ');
                sb.append(convert(otherNumber));
            }
        }
        return sb.toString();
    }

    private boolean isOutOfBounds() {
        return !(number >= NUMBER_MIN && number <= NUMBER_MAX);
    }

    @Override
    public String toLetter() throws NumberConversionException {
        if (isOutOfBounds()) {
            if (throwError) {
                throw new NumberConversionException(number);
            } else {
                letter = String.valueOf(number);
            }
        }

        if (letter != null) {
            return letter;
        }

        StringBuilder sb = new StringBuilder();
        int theNumber = number; // version modificable del valor original
        int currentGroup; // Numero a convertir
        int power;
        int length = String.valueOf(number).length();
        int count = (int) Math.ceil(length/3.0);

        for (int i = count; i >= 1; i--) {
            // El numero original tiene al menos 4 digitos
            if (i > 1) {
                power = (int) Math.pow(10, (i - 1)*3);
                currentGroup = theNumber/power;
                theNumber -= currentGroup*power;
            } else {
                // Tiene a lo sumo 3 digitos
                currentGroup = theNumber;
            }
            if (currentGroup == 0 && i > 1) continue;

            if (currentGroup == 1 && i > 1) {
                sb.append(ONE);
            } else {
                sb.append(convert(currentGroup));
            }
            switch (i) {
                case 3:
                    sb.append(' ');
                    sb.append(currentGroup == 1 ? MILLIONS[0] : MILLIONS[1]);
                    break;
                case 2:
                    sb.append(' ').append(THOUSAND);
                    break;
                default:
            }
            if (theNumber == 0) break;
            if (i > 1) sb.append(' ');
        }
        if (currency != null && !currency.trim().isEmpty()) {
            sb.append(' ').append(currency);
        }
        if (decimal != null) {
            sb.append(FRACTION_WITH).append(decimal).append(FRACTION_SUFFIX);
        }
        letter = sb.toString();
        return letter;
    }

    public static class Number2LetterBuilder {

        private static final BigDecimal ONE_HUNDRED = new BigDecimal(100);

        final int number;
        final Integer decimal;
        String currency = null;
        boolean longFormat = false;
        boolean throwError = false;

        public Number2LetterBuilder(int number) {
            this.number = number;
            this.decimal = null;
        }

        public Number2LetterBuilder(BigDecimal number) {
            this.number = number.intValue();
            this.decimal = number
                    .setScale(2, RoundingMode.HALF_UP)
                    .remainder(BigDecimal.ONE)
                    .multiply(ONE_HUNDRED)
                    .intValue();
        }

        public Number2LetterBuilder currency(String currency) {
            this.currency = currency;
            return this;
        }

        public Number2LetterBuilder longFormat(boolean longFormat) {
            this.longFormat = longFormat;
            return this;
        }

        public Number2LetterBuilder throwError(boolean throwError) {
            this.throwError = throwError;
            return this;
        }

        public Number2LetterConverter build() {
            return new SpanishNumber2Letter(this);
        }
    }
}
