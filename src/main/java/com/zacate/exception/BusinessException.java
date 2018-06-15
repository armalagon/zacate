package com.zacate.exception;

import com.zacate.i18n.BundleIdentifier;
import com.zacate.i18n.BundleKeyGenerator;
import com.zacate.i18n.LocalizedMessageResolver;

/**
 *
 * @author Armando Alaniz
 * @version 1.0
 * @since 1.0
 */
public class BusinessException extends Exception implements BundleIdentifier {

    protected final String errorCode;
    protected final Object[] arguments;

    public BusinessException() {
        this.errorCode = null;
        this.arguments = null;
    }

    public BusinessException(String message) {
        super(message);
        this.errorCode = null;
        this.arguments = null;
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
        this.errorCode = null;
        this.arguments = null;
    }

    public BusinessException(Throwable cause) {
        super(cause);
        this.errorCode = null;
        this.arguments = null;
    }

    public BusinessException(String errorCode, Object... arguments) {
        this(errorCode, true, arguments);
    }

    public BusinessException(String errorCode, boolean expandKeyBasedOnCurrentClass, Object... arguments) {
        this.errorCode = expandKeyBasedOnCurrentClass ? BundleKeyGenerator.expandKeyUsing(errorCode, this.getClass()) : errorCode;
        this.arguments = doTransform(arguments);
    }

    public String getErrorCode() {
        return errorCode;
    }

    public Object[] getArguments() {
        return arguments;
    }

    @Override
    public String getMessage() {
        if (errorCode != null) {
            return LocalizedMessageResolver.translate(getDefaultBaseBundleName(), errorCode, arguments);
        } else {
            return super.getMessage();
        }
    }

    public String getCategory() {
        String category = null;
        // Swallow any error
        try {
            category = LocalizedMessageResolver.translate(getDefaultBaseBundleName(), this.getClass().getName());
        } catch (RuntimeException re) {
            // TODO At least log the error
        }
        return category;
    }

    protected Object[] doTransform(Object[] arguments) {
        if (arguments == null || arguments.length == 0) {
            return arguments;
        }

        Object[] argsTransformed = new Object[arguments.length];
        for (int i = 0; i < arguments.length; i++) {
            Object arg = arguments[i];
            if (arg instanceof String) {
                String value = (String) arg;
                if (LocalizedMessageResolver.isReservedArgumentForAutoExpansion(value)) {
                    argsTransformed[i] = BundleKeyGenerator.expandReservedParameterUsing(value, this.getClass());
                } else {
                    argsTransformed[i] = arg;
                }
            } else {
                argsTransformed[i] = arg;
            }
        }

        return argsTransformed;
    }

}