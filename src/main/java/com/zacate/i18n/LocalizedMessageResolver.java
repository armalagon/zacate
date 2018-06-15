package com.zacate.i18n;

import static com.zacate.i18n.LocalizedConstants.LEFT_BRACE_STR;
import static com.zacate.i18n.LocalizedConstants.PREFIX_TO_EXPAND_PARAMETER_KEY;
import static com.zacate.i18n.LocalizedConstants.RIGHT_BRACE_STR;
import java.text.MessageFormat;
import java.util.ResourceBundle;

/**
 *
 * @author Armando Alaniz
 * @version 1.0
 * @since 1.0
 */
public abstract class LocalizedMessageResolver {

    public static boolean isReservedArgument(String argument) {
        return argument.startsWith(LEFT_BRACE_STR) && argument.endsWith(RIGHT_BRACE_STR);
    }

    public static boolean isReservedArgumentForAutoExpansion(String argument) {
        return isReservedArgument(argument) && argument.startsWith(PREFIX_TO_EXPAND_PARAMETER_KEY, 1);
    }

    public static String translate(String bundleName, String key) {
        ResourceBundle bundle = ResourceBundle.getBundle(bundleName);
        String message = bundle.getString(key);

        if (message == null) {
            throw new IllegalArgumentException("The key [" + key + "] doesn't exist in the specified bundle [" + bundle.getBaseBundleName() + "]");
        }

        return message;
    }

    public static String translate(String bundleName, String key, Object... arguments) {
        String template = translate(bundleName, key);

        if (arguments == null || arguments.length == 0) {
            return template;
        }

        ResourceBundle bundle = ResourceBundle.getBundle(bundleName);
        Object[] translatedArguments = new Object[arguments.length];

        for (int i = 0; i < arguments.length; i++) {
            if (arguments[i] instanceof LocalizedEnum) {
                translatedArguments[i] = ((LocalizedEnum) arguments[i]).getMessage();
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
