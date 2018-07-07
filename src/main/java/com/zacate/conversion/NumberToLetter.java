package com.zacate.conversion;

import com.zacate.number.NumberUtils;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

/**
 *
 * @author Armando Alaniz
 * @version 1.0
 * @since 1.0
 */
public abstract class NumberToLetter {

    protected static final BigDecimal BD_ONE_HUNDRED = new BigDecimal(100);

    protected static final char SPACE = ' ';

    private static final String NUMBER_BUNDLE = "com.zacate.conversion.numbers";

    protected final int num;
    protected final int decimalPart;
    protected final String currency;
    private final ResourceBundle bundle;
    protected final List<GroupedNumber> groups;
    protected final String letter;

    public NumberToLetter(final int num) {
        this.num = num;
        this.decimalPart = -1;
        this.currency = null;
        this.bundle = ResourceBundle.getBundle(NUMBER_BUNDLE, numberLocale());
        this.groups = createGroups();
        this.letter = translate();
    }

    public NumberToLetter(final BigDecimal num) {
        this(num, null);
    }

    public NumberToLetter(final BigDecimal num, final String currency) {
        this.num = num.intValue();
        // TODO Refactor this into a function
        final BigDecimal remainder = num.remainder(BigDecimal.ONE);
        this.decimalPart = NumberUtils.isDifferentFromZero(remainder) ? remainder.multiply(BD_ONE_HUNDRED)
                .setScale(0, RoundingMode.HALF_UP).intValue() : -1;
        this.currency = currency;
        this.bundle = ResourceBundle.getBundle(NUMBER_BUNDLE, numberLocale());
        this.groups = createGroups();
        this.letter = translate();
    }

    public abstract String translateNumber();

    public abstract String translateCurrency();

    protected abstract Locale numberLocale();

    protected String getLetter(int number) {
        return bundle.getString(String.valueOf(number));
    }

    protected String translate() {
        final StringBuilder resp = new StringBuilder();
        resp.append(translateNumber());

        final String currPart = translateCurrency();
        if (currPart != null) {
            resp.append(SPACE);
            resp.append(currPart);
        }

        return resp.toString();
    }

    protected class GroupedNumber {
        protected final Integer number;
        protected final int[] numbers;
        protected final int tenToPowerOf; // 10^n
        protected final int count;
        protected final int tensIfApply;

        public GroupedNumber(final Integer number, final int[] numbers, final int tenToPowerOf) {
            this.number = number;
            this.numbers = numbers;
            this.count = numbers.length;
            this.tenToPowerOf = tenToPowerOf;
            if (this.number >= 10 && this.number <= 99) {
                this.tensIfApply = this.number;
            } else if (this.number >= 100 && this.number <= 999) {
                int sum = 0;
                for (int i = 1; i < this.count; i++) {
                    sum += numbers[i];
                }
                this.tensIfApply = sum != 0 ? sum : -1;
            } else {
                this.tensIfApply = -1;
            }
        }

        @Override
        public String toString() {
            return "GroupedNumber [number=" + number + ", numbers=" + Arrays.toString(numbers) + ", tenToPowerOf=" + tenToPowerOf
                    + ", tensIfApply=" + tensIfApply + "]";
        }
    }

    private List<GroupedNumber> createGroups() {
        final char[] digits = String.valueOf(num).toCharArray();
        final int threeDigitsCount = (int) Math.ceil(digits.length/3d);
        final List<GroupedNumber> _groups = new ArrayList<>(threeDigitsCount);

        int take = digits.length - (threeDigitsCount - 1) * 3;
        for (int i = 0, k = 0; i < threeDigitsCount; i++) {
            final int[] groupedParts = new int[take];
            int partialNum = 0;
            int zeroes = 0;
            for (int j = 0; j < take; j++) {
                final int digit = Character.digit(digits[k++], 10);

                if ((digit == 0 && i == 0 && j > 0) || (digit == 0 && i > 0)) {
                    ++zeroes;
                }

                groupedParts[j] = digit * (int) Math.pow(10, take - j - 1);
                partialNum += groupedParts[j];
            }

            if (!(partialNum == 0 && i > 0)) {
                final int[] clonedGroupedParts = new int[take - zeroes];
                for (int x = 0, y = 0; x < groupedParts.length; x++) {
                    if (!((groupedParts[x] == 0 && i > 0) || (groupedParts[x] == 0 && i == 0 && x > 0))) {
                        clonedGroupedParts[y++] = groupedParts[x];
                    }
                }
                _groups.add(new GroupedNumber(partialNum, clonedGroupedParts, digits.length - k));
            }

            take = 3;
        }

        return _groups;
    }

    public int getNum() {
        return num;
    }

    public int getDecimalPart() {
        return decimalPart;
    }

    public String getCurrency() {
        return currency;
    }

    public String getLetter() {
        return letter;
    }

    public static NumberToLetter getInstance(final int num) {
        final Locale spanish = new Locale("spa");

        if (Locale.getDefault().getLanguage().equals(spanish.getLanguage())) {
            return new SpanishNumberToLetter(num);
        } else {
            return new SpanishNumberToLetter(num);
        }
    }

}
