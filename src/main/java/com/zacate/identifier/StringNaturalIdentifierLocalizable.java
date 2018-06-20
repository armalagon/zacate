package com.zacate.identifier;

import com.zacate.i18n.LocalizedEnum;
import java.util.Objects;

/**
 *
 * @author Armando Alaniz
 * @version 1.0
 * @since 1.0
 */
public interface StringNaturalIdentifierLocalizable extends NaturalIdentifier<String>, LocalizedEnum {

    default boolean containsIgnoreCase(String value) {
        value = Objects.requireNonNull(value).toLowerCase();
        return !(getCode() == null || getMessage() == null) ? getCode().toLowerCase().contains(value) ||
                getMessage().toLowerCase().contains(value) : false;
    }

}
