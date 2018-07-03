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

        // TODO Improve this: possible code smell
        String enumClass = enumObj.getDeclaringClass().getName();
        int dollarSign = enumClass.indexOf('$', enumClass.lastIndexOf(DOT));
        if (dollarSign != -1) {
            char[] enumClass2Chars = enumClass.toCharArray();
            enumClass2Chars[dollarSign] = DOT;
            enumClass = new String(enumClass2Chars);
        }

        final StringBuilder key = new StringBuilder(enumClass);
        key.append(DOT);
        key.append(enumObj.name());
        return getMessage(key.toString());
    }

}
