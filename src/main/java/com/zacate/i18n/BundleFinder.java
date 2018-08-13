package com.zacate.i18n;

import static com.zacate.i18n.LocalizedConstants.DOT;
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

    public static List<ResourceBundle> findBundles(List<String> packages) {
        return findBundles(packages, STANDARD_BUNDLE_FILENAME, null);
    }

    public static List<ResourceBundle> findBundles(List<String> packages, Locale locale) {
        return findBundles(packages, STANDARD_BUNDLE_FILENAME, locale);
    }

    public static List<ResourceBundle> findBundles(List<String> packages, String bundleName, Locale locale) {
        List<String> bundleNames = packages
                .stream()
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
