package com.zacate.i18n;

import com.zacate.bean.Reflections;
import static com.zacate.i18n.LocalizedConstants.*;

/**
 *
 * @author Armando Alaniz
 * @version 1.0
 * @since 1.0
 */
public interface BundleIdentifier {

    default String getBundlePrefix() {
        final StringBuilder i18n = new StringBuilder();
        i18n.append(Reflections.getParentPackageName(this.getClass()));
        i18n.append(DOT);
        i18n.append(I18N_PACKAGE_SUFFIX);
        return i18n.toString();
    }

    default String getBundleFilename() {
        return STANDARD_BUNDLE_FILENAME;
    }

    default String getDefaultBaseBundleName() {
        String bundlePrefix = getBundlePrefix();
        final StringBuilder baseName = new StringBuilder(bundlePrefix);
        if (!bundlePrefix.endsWith(DOT_STR)) {
            baseName.append(DOT);
        }
        baseName.append(getBundleFilename());
        return baseName.toString();
    }

}
