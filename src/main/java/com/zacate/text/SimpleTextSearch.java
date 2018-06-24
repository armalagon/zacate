package com.zacate.text;

import com.zacate.collection.ArrayUtils;
import com.zacate.i18n.LocalizedEnum;
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

    private SimpleTextSearch(String text, boolean ignoreCase) {
        Objects.requireNonNull(text);
        this.text = ignoreCase ? text.toLowerCase() : text;
        this.ignoreCase = ignoreCase;
        this.found = false;
    }

    private SimpleTextSearch(String text) {
        this(text, true);
    }

    public SimpleTextSearch field(CharSequence value) {
        if (!(found || value == null)) {
            found = ignoreCase ? value.toString().toLowerCase().contains(text) : value.toString().contains(text);
        }
        return this;
    }

    public SimpleTextSearch field(CharSequence... values) {
        if (!(found || ArrayUtils.isEmpty(values))) {
            for (CharSequence value : values) {
                field(value);
            }
        }
        return this;
    }

    public SimpleTextSearch field(StringNaturalIdentifierLocalizable value) {
        if (!(found || value == null)) {
            found = !(value.getCode() == null || value.getMessage() == null) ? ignoreCase ? value.getCode().toLowerCase().contains(text) ||
                    value.getMessage().toLowerCase().contains(text) : value.getCode().contains(text) || value.getMessage().contains(text)
                    : false;
        }
        return this;
    }

    public SimpleTextSearch field(StringNaturalIdentifierLocalizable... values) {
        if (!(found || ArrayUtils.isEmpty(values))) {
            for (StringNaturalIdentifierLocalizable value : values) {
                field(value);
            }
        }
        return this;
    }

    public SimpleTextSearch field(LocalizedEnum value) {
        if (!(found || value == null)) {
            found = ignoreCase ? value.getMessage().toLowerCase().contains(text) : value.getMessage().contains(text);
        }
        return this;
    }

    public SimpleTextSearch field(LocalizedEnum... values) {
        if (!(found || ArrayUtils.isEmpty(values))) {
            for (LocalizedEnum value : values) {
                field(value);
            }
        }
        return this;
    }

    public boolean contains() {
        return found;
    }

    public static SimpleTextSearch search(String text) {
        return new SimpleTextSearch(text);
    }

    public static SimpleTextSearch search(String text, boolean ignoreCase) {
        return new SimpleTextSearch(text, ignoreCase);
    }

}
