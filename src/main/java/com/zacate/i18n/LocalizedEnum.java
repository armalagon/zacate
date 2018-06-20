package com.zacate.i18n;

import static com.zacate.i18n.LocalizedConstants.DOT;
import java.util.Objects;

/**
 *
 * @author Armando Alaniz
 * @version 1.0
 * @since 1.0
 */
public interface LocalizedEnum extends Localized {

    default String getMessage() {
        if (!(this instanceof Enum)) {
            throw new UnsupportedOperationException("The class [" + this.getClass().getName() + "] must be an Enum type to be able to retrieve the description");
        }

        Enum<?> enumObj = (Enum<?>) this;

        StringBuilder key = new StringBuilder(enumObj.getDeclaringClass().getName());
        key.append(DOT);
        key.append(enumObj.name());
        return getMessage(key.toString());
    }

    default boolean containsIgnoreCase(String value) {
        return getMessage().toLowerCase().contains(Objects.requireNonNull(value).toLowerCase());
    }

}
