package com.zacate.i18n;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 *
 * @author Armando Alaniz
 * @version 1.0
 * @since 1.0
 */
public abstract class BundleAggregator extends ResourceBundle {

    private static final Set<Locale> supportedLocales = new HashSet<>();

    private final Map<Locale, List<ResourceBundle>> bundles = new HashMap<>();
    private final Enumeration<String> keys;

    public BundleAggregator(List<String> packages) {
        if (!supportedLocales.isEmpty()) {
            supportedLocales.forEach(locale -> bundles.put(locale, BundleFinder.findBundles(packages, locale)));
        } else {
            bundles.put(Locale.getDefault(), BundleFinder.findBundles(packages));
        }
        this.keys = accumulateKeys(bundles);
    }

    private Enumeration<String> accumulateKeys(Map<Locale, List<ResourceBundle>> bundles) {
        Set<String> keys = new HashSet<>();
        Set<ResourceBundle> distinctBundles = bundles.values().stream().flatMap(files -> files.stream()).collect(Collectors.toSet());
        for (ResourceBundle bundle : distinctBundles) {
            Enumeration<String> bundleKeys = bundle.getKeys();
            while (bundleKeys.hasMoreElements()) {
                keys.add(bundleKeys.nextElement());
            }
        }
        return Collections.enumeration(keys);
    }

    private String normalizeKey(String key) {
        // TODO Implements a cache for keys
        return key.contains(LocalizedConstants.SPECIAL_KEY_SEPARATOR_STR) ?
                key.replaceAll(LocalizedConstants.SPECIAL_KEY_SEPARATOR_STR, LocalizedConstants.DOT_STR) : key;
    }

    private List<ResourceBundle> getBundles() {
        // TODO Get the locale: use an strategy to resolve the locale available
        // TODO If this is JSF, get the locale from Faces context, REST from a custom header otherwise from a thread local
        // TODO There should be 2 locales: the one selected by the user and a default one
        return bundles.getOrDefault(Locale.getDefault(), bundles.get(Locale.getDefault()));
    }

    @Override
    protected Object handleGetObject(String key) {
        String _key = normalizeKey(key);
        String value;

        try {
            Predicate<ResourceBundle> containsKey = rb -> rb.containsKey(_key);

            long count = getBundles().stream()
                    .filter(containsKey)
                    .count();

            if (count > 1) {
                // TODO Send a warning stating that this key is available in at least two bundles
            }

            value = getBundles().stream()
                .filter(containsKey)
                .findFirst()
                .get()
                .getString(_key);
        } catch (MissingResourceException mre) {
            value = _key;
        }

        return value;
    }

    @Override
    public Enumeration<String> getKeys() {
        return keys;
    }

    protected static void addSupportedLanguage(final Locale language) {
        Objects.requireNonNull(language, "language");
        supportedLocales.add(language);
    }

}
