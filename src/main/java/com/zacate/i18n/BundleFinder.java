package com.zacate.i18n;

import com.zacate.bean.Reflections;
import static com.zacate.i18n.LocalizedConstants.DOT;
import static com.zacate.i18n.LocalizedConstants.I18N_PACKAGE_SUFFIX;
import static com.zacate.i18n.LocalizedConstants.STANDARD_BUNDLE_FILENAME;
import java.util.List;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

/**
 *
 * @author Armando Alaniz
 * @version 1.0
 * @since 1.0
 */
public abstract class BundleFinder {

    public static List<ResourceBundle> findBundles() {
        return findBundles(I18N_PACKAGE_SUFFIX, STANDARD_BUNDLE_FILENAME, null);
    }

    public static List<ResourceBundle> findBundles(Locale locale) {
        return findBundles(I18N_PACKAGE_SUFFIX, STANDARD_BUNDLE_FILENAME, locale);
    }

    public static List<ResourceBundle> findBundles(String packageSuffix, String bundleName, Locale locale) {
        List<String> bundleNames = Reflections.findPackagesEndWith(packageSuffix)
                .stream()
                .map(Package::getName)
                .map(name -> new StringBuilder(name).append(DOT).append(bundleName).toString())
                .collect(Collectors.toList());

        List<ResourceBundle> bundles = bundleNames
                .stream()
                .map(name -> {
                    ResourceBundle bundle;
                    try {
                        if (locale != null) {
                            bundle = ResourceBundle.getBundle(name, locale);
                        } else {
                            bundle = ResourceBundle.getBundle(name);
                        }
                    } catch (MissingResourceException mre) {
                        bundle = null;
                    }
                    return bundle;
                })
                .filter(rb -> rb != null)
                .collect(Collectors.toList());

        return bundles;
    }

}
