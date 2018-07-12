package com.zacate.i18n;

import static com.zacate.i18n.LocalizedConstants.DOT;

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

        final Enum<?> enumObj = (Enum<?>) this;
        final StringBuilder key = new StringBuilder(enumObj.getDeclaringClass().getCanonicalName());
        key.append(DOT);
        key.append(enumObj.name());
        return getMessage(key.toString());
    }

}
