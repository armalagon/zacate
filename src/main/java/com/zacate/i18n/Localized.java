package com.zacate.i18n;

import static com.zacate.i18n.LocalizedConstants.DOT;
import java.util.ResourceBundle;

/**
 *
 * @author Armando Alaniz
 * @version 1.0
 * @since 1.0
 */
public interface Localized extends BundleIdentifier {

    default String getMessage(String key) {
        ResourceBundle bundle = ResourceBundle.getBundle(getDefaultBaseBundleName());
        return bundle.getString(key);
    }

    default String getMessage() {
        if (this instanceof Enum) {
            final Enum<?> enumObj = (Enum<?>) this;
            final StringBuilder key = new StringBuilder(enumObj.getDeclaringClass().getCanonicalName());
            key.append(DOT);
            key.append(enumObj.name());
            return getMessage(key.toString());
        } else {
            throw new UnsupportedOperationException("The class [" + this.getClass().getName() + "] must implement this method");
        }
    }
}
