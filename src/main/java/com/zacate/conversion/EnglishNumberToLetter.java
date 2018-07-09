package com.zacate.conversion;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 *
 * @author Armando Alaniz
 * @version 1.0
 * @since 1.0
 */
public class EnglishNumberToLetter extends NumberToLetter {

    private static final Map<Integer, String> SPECIAL_NUMBERS = new HashMap<>();
    static {
        SPECIAL_NUMBERS.put(11, "eleven");
        SPECIAL_NUMBERS.put(12, "twelve");
        SPECIAL_NUMBERS.put(13, "thirteen");
        SPECIAL_NUMBERS.put(14, "fourteen");
        SPECIAL_NUMBERS.put(15, "fifteen");
        SPECIAL_NUMBERS.put(16, "sixteen");
        SPECIAL_NUMBERS.put(17, "seventeen");
        SPECIAL_NUMBERS.put(18, "eighteen");
        SPECIAL_NUMBERS.put(19, "nineteen");
    }

    private static final char DASH = '-';
    private static final char AND = 'y';
    private static final String THOUSAND = "thousand";
    private static final String MILLION = "million";
    private static final String DIVIDE_BY_ONE_HUNDRED = "/100";

    public EnglishNumberToLetter(final int num) {
        super(num);
    }

    public EnglishNumberToLetter(final BigDecimal num) {
        super(num);
    }

    public EnglishNumberToLetter(final BigDecimal num, final String currency) {
        super(num, currency);
    }

    private void doPrepare(final StringBuilder resp) {
        if (resp.length() > 0) {
            resp.append(SPACE);
        }
    }

    @Override
    protected String translateNumber() {
        final StringBuilder resp = new StringBuilder();

        for (final GroupedNumber group : groups) {
            System.out.println("-> group: " + group);

            for (int i = 0; i < group.count; i++) {
                doPrepare(resp);
                final int partialNum = group.numbers[i];
                if (partialNum >= 0 && partialNum <= 9) {
                    resp.append(getLetter(partialNum));
                } else if (partialNum >= 10 && partialNum <= 99) {
                    if (group.tensIfApply >= 11 && group.tensIfApply <= 19) {
                        resp.append(SPECIAL_NUMBERS.get(group.tensIfApply));
                        break;
                    } else {
                        resp.append(getLetter(partialNum));
                        if (i + 1 < group.count) {
                            resp.append(DASH);
                            resp.append(getLetter(group.numbers[i + 1]));
                            break;
                        }
                    }
                } else if (partialNum >= 100 && partialNum <= 999) {
                    resp.append(getLetter(partialNum));
                } else {
                    throw new IllegalStateException("Unknown amount of digits");
                }
            }

            if (group.tenToPowerOf == 9) {
                doPrepare(resp);
                resp.append(THOUSAND);
            } else if (group.tenToPowerOf == 6) {
                doPrepare(resp);
                resp.append(MILLION);
            } else if (group.tenToPowerOf == 3) {
                doPrepare(resp);
                resp.append(THOUSAND);
            }
        }

        return resp.toString();
    }

    @Override
    protected String translateDecimalPart() {
        final StringBuilder resp = new StringBuilder();
        resp.append(AND);
        resp.append(SPACE);
        resp.append(decimalPart);
        resp.append(DIVIDE_BY_ONE_HUNDRED);
        return resp.toString();
    }

    @Override
    protected Locale numberLocale() {
        return Locale.ENGLISH;
    }

}
