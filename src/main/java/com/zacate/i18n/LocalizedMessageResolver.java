package com.zacate.i18n;

import static com.zacate.i18n.LocalizedConstants.LEFT_BRACE_STR;
import static com.zacate.i18n.LocalizedConstants.PREFIX_TO_EXPAND_PARAMETER_KEY;
import static com.zacate.i18n.LocalizedConstants.RIGHT_BRACE_STR;
import com.zacate.util.ArrayUtils;
import java.text.MessageFormat;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 *
 * @author Armando Alaniz
 * @version 1.0
 * @since 1.0
 */
public abstract class LocalizedMessageResolver {

    public static boolean isReservedArgument(final String argument) {
        Objects.requireNonNull(argument, "argument");
        return argument.startsWith(LEFT_BRACE_STR) && argument.endsWith(RIGHT_BRACE_STR);
    }

    public static boolean isReservedArgumentForAutoExpansion(final String argument) {
        Objects.requireNonNull(argument, "argument");
        return isReservedArgument(argument) && argument.startsWith(PREFIX_TO_EXPAND_PARAMETER_KEY, 1);
    }

    public static String translate(final String bundleName, final String key) {
        Objects.requireNonNull(bundleName, "bundleName");
        Objects.requireNonNull(key, "key");
        ResourceBundle bundle = ResourceBundle.getBundle(bundleName);
        String message = bundle.getString(key);
        return message;
    }

    public static String translate(final String bundleName, final String key, final Object... arguments) {
        String template = translate(bundleName, key);

        if (ArrayUtils.isEmpty(arguments)) {
            return template;
        }

        ResourceBundle bundle = ResourceBundle.getBundle(bundleName);
        Object[] translatedArguments = new Object[arguments.length];

        for (int i = 0; i < arguments.length; i++) {
            if (arguments[i] instanceof Localized) {
                translatedArguments[i] = ((Localized) arguments[i]).getMessage();
            } else if (arguments[i] instanceof String) {
                String argument = (String) arguments[i];

                if (isReservedArgument(argument)) {
                    String extractedKey = argument.substring(1, argument.length() - 1);
                    translatedArguments[i] = bundle.getString(extractedKey);
                } else {
                    translatedArguments[i] = argument;
                }
            } else {
                translatedArguments[i] = arguments[i];
            }
        }

        return MessageFormat.format(template, translatedArguments);
    }

}
