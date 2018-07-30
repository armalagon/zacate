package com.zacate.conversion;

import com.zacate.util.NumberUtils;
import com.zacate.util.StringUtils;
import java.math.BigDecimal;
import java.util.*;
import java.util.function.BiFunction;

/**
 *
 * @author Armando Alaniz
 * @version 1.0
 * @since 1.0
 */
public abstract class NumberToLetter {

    public static final char SPACE = ' ';

    private static final String NUMBER_BUNDLE = "com.zacate.conversion.numbers";

    protected final long num;
    protected final int decimalPart;
    protected final String currency;
    private final ResourceBundle bundle;
    protected final List<GroupedNumber> groups;
    protected final String number2Letter;
    protected final String letter;

    public NumberToLetter(final long num, final int decimalPart, final String currency) {
        if (isOutOfRange(num)) {
            throw new IllegalArgumentException("Number [" + num + "] is out of boundaries [0, 999_999_999_999]");
        }
        this.num = num;
        this.decimalPart = decimalPart;
        this.currency = currency;
        this.bundle = ResourceBundle.getBundle(NUMBER_BUNDLE, numberLocale());
        this.groups = createGroups();
        this.number2Letter = translateNumber();
        this.letter = translate();
    }

    public NumberToLetter(final long num) {
        this(num, -1, null);
    }

    public NumberToLetter(final BigDecimal num) {
        this(num, null);
    }

    public NumberToLetter(final BigDecimal num, final String currency) {
        this(num.longValue(), NumberUtils.getDecimalPart(num), currency);
    }

    private boolean isOutOfRange(final long num) {
        return !(num >= 0l && num <= 999_999_999_999l);
    }

    protected abstract String translateNumber();

    protected abstract String translateDecimalPart();

    protected abstract Locale numberLocale();

    protected String getLetter(int number) {
        return bundle.getString(String.valueOf(number));
    }

    private void doAppendNewPart(final StringBuilder sb, final String part) {
        sb.append(SPACE);
        sb.append(part);
    }

    protected String translate() {
        final StringBuilder resp = new StringBuilder(number2Letter);
        if (decimalPart != -1) {
            doAppendNewPart(resp, translateDecimalPart());
        }
        if (currency != null) {
            doAppendNewPart(resp, currency);
        }
        return resp.toString();
    }

    private List<GroupedNumber> createGroups() {
        final char[] digits = String.valueOf(num).toCharArray();
        final int threeDigitsCount = (int) Math.ceil(digits.length/3d);
        final List<GroupedNumber> _groups = new ArrayList<>(threeDigitsCount);

        int take = digits.length - (threeDigitsCount - 1) * 3;
        int partialNum = -1;
        int digit = -1;
        for (int i = 0, k = 0; i < threeDigitsCount; i++) {
            partialNum = 0;
            int[] parts = new int[take];
            for (int j = 0; j < take; j++) {
                digit = Character.digit(digits[k++], 10);
                parts[j] = digit * (int) Math.pow(10, take - j - 1);
                partialNum += parts[j];
            }

            if (!(partialNum == 0 && i > 0)) {
                _groups.add(new GroupedNumber(partialNum, parts, digits.length - k));
            }

            take = 3;
        }

        return _groups;
    }

    public long getNum() {
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

    public String getLetter(final BiFunction<String, String, String> currencyAndDecimalPartCombiner) {
        final StringBuilder resp = new StringBuilder(number2Letter);
        if (!(decimalPart == -1 && currency == null)) {
            String _currency = StringUtils.emptyIfNull(currency);
            String _decimalPart = decimalPart != -1 ? translateDecimalPart() : StringUtils.EMPTY;
            doAppendNewPart(resp, currencyAndDecimalPartCombiner.apply(_currency, _decimalPart));
        }
        return resp.toString();
    }

    protected class GroupedNumber {
        protected final int number;
        protected final int[] numbers;
        protected final int tenToPowerOf; // 10^n
        protected final int count;
        protected final int tensIfApply;

        public GroupedNumber(final int number, final int[] numbers, final int tenToPowerOf) {
            this.number = number;
            this.tenToPowerOf = tenToPowerOf;

            // Remove unnecesary zeroes
            if (number != 0) {
                int zeroes = 0;
                int sum = 0;
                for (int i = 0; i < numbers.length; i++) {
                    if (numbers[i] == 0) {
                        ++zeroes;
                    }
                    if ((number >= 10 && number <= 99) || (number >= 100 && number <= 999 & i > 0)) {
                        sum += numbers[i];
                    }
                }
                int[] _numbers = new int[numbers.length - zeroes];
                for (int i = 0, j = 0; i < numbers.length; i++) {
                    if (numbers[i] != 0) {
                        _numbers[j++] = numbers[i];
                    }
                }
                this.numbers = _numbers;
                this.count = this.numbers.length;
                this.tensIfApply = sum != 0 ? sum : -1;
            } else {
                this.numbers = numbers;
                this.count = numbers.length;
                this.tensIfApply = -1;
            }
        }

        @Override
        public String toString() {
            return "GroupedNumber [number=" + number + ", numbers=" + Arrays.toString(numbers) + ", tenToPowerOf=" + tenToPowerOf
                    + ", tensIfApply=" + tensIfApply + "]";
        }
    }

    public static NumberToLetter getInstance(final long num) {
        // TODO Refactor this code based on a configurable Locale
        final Locale spanish = new Locale("spa");

        if (Locale.getDefault().getLanguage().equals(spanish.getLanguage())) {
            return new SpanishNumberToLetter(num);
        } else {
            return new SpanishNumberToLetter(num);
        }
    }

}
