package com.zacate.conversion;

import com.zacate.i18n.RegionalConstants;
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
public class SpanishNumberToLetter extends NumberToLetter {

    private static final Map<Integer, String> SPECIAL_NUMBERS = new HashMap<>();
    static {
        SPECIAL_NUMBERS.put(11, "once");
        SPECIAL_NUMBERS.put(12, "doce");
        SPECIAL_NUMBERS.put(13, "trece");
        SPECIAL_NUMBERS.put(14, "catorce");
        SPECIAL_NUMBERS.put(15, "quince");
    }

    private static final String ONE_FOR = "un";
    private static final String ONE_HUNDRED = "cien";
    private static final String THOUSAND = "mil";
    private static final String MILLION_SINGULAR = "millon";
    private static final String MILLION_PLURAL = "millones";
    private static final char AND = 'y';
    private static final String WITH = "con";
    private static final String DIVIDE_BY_ONE_HUNDRED = "/100";

    public SpanishNumberToLetter(final int num) {
        super(num);
    }

    public SpanishNumberToLetter(final BigDecimal num) {
        super(num);
    }

    public SpanishNumberToLetter(final BigDecimal num, final String currency) {
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
                    if (partialNum == 1 && group.tenToPowerOf >= 3) {
                        resp.append(ONE_FOR);
                    } else {
                        resp.append(getLetter(partialNum));
                    }
                } else if (partialNum >= 10 && partialNum <= 99) {
                    if (group.tensIfApply >= 11 && group.tensIfApply <= 15) {
                        resp.append(SPECIAL_NUMBERS.get(group.tensIfApply));
                        break;
                    } else {
                        resp.append(getLetter(partialNum));
                        if (i + 1 < group.count) {
                            resp.append(SPACE).append(AND);
                        }
                    }
                } else if (partialNum >= 100 && partialNum <= 999) {
                    if (partialNum == 100 && group.tensIfApply == -1) {
                        resp.append(ONE_HUNDRED);
                    } else {
                        resp.append(getLetter(partialNum));
                    }
                } else {
                    throw new IllegalStateException("Unknown amount of digits");
                }
            }

            if (group.tenToPowerOf == 9) {
                doPrepare(resp);
                resp.append(THOUSAND);
            } else if (group.tenToPowerOf == 6) {
                doPrepare(resp);
                resp.append(group.numbers[0] == 1 ? MILLION_SINGULAR : MILLION_PLURAL);
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
        resp.append(WITH);
        resp.append(SPACE);
        resp.append(decimalPart);
        resp.append(DIVIDE_BY_ONE_HUNDRED);
        return resp.toString();
    }

    @Override
    protected Locale numberLocale() {
        return RegionalConstants.SPANISH;
    }

}
