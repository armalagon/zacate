package com.zacate.i18n;

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

}
