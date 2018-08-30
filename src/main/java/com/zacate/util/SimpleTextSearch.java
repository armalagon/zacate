package com.zacate.util;

import com.zacate.i18n.Localized;
import com.zacate.identifier.StringNaturalIdentifierLocalizable;
import java.util.Objects;

/**
 *
 * @author Armando Alaniz
 * @version 1.0
 * @since 1.0
 */
public class SimpleTextSearch {

    private final String text;
    private final boolean ignoreCase;
    private boolean found;

    private SimpleTextSearch(final String text, final boolean ignoreCase) {
        Objects.requireNonNull(text, "text");
        this.text = ignoreCase ? text.toLowerCase() : text;
        this.ignoreCase = ignoreCase;
        this.found = false;
    }

    private SimpleTextSearch(String text) {
        this(text, true);
    }

    public SimpleTextSearch field(final CharSequence value) {
        if (!(found || value == null)) {
            found = ignoreCase ? value.toString().toLowerCase().contains(text) : value.toString().contains(text);
        }
        return this;
    }

    public SimpleTextSearch field(final CharSequence... values) {
        if (!(found || Arguments.isEmpty(values))) {
            for (CharSequence value : values) {
                field(value);
            }
        }
        return this;
    }

    public SimpleTextSearch field(final StringNaturalIdentifierLocalizable value) {
        if (!(found || value == null)) {
            field(value.getCode());
            field(value.getMessage());
        }
        return this;
    }

    public SimpleTextSearch field(final StringNaturalIdentifierLocalizable... values) {
        if (!(found || Arguments.isEmpty(values))) {
            for (StringNaturalIdentifierLocalizable value : values) {
                field(value);
            }
        }
        return this;
    }

    public SimpleTextSearch field(final Localized value) {
        if (!(found || value == null)) {
            field(value.getMessage());
        }
        return this;
    }

    public SimpleTextSearch field(final Localized... values) {
        if (!(found || Arguments.isEmpty(values))) {
            for (Localized value : values) {
                field(value);
            }
        }
        return this;
    }

    public boolean contains() {
        return found;
    }

    public static SimpleTextSearch search(final String text) {
        return new SimpleTextSearch(text);
    }

    public static SimpleTextSearch search(final String text, final boolean ignoreCase) {
        return new SimpleTextSearch(text, ignoreCase);
    }

}
