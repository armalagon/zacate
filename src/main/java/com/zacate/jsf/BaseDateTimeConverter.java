package com.zacate.jsf;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;
import java.util.Optional;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import org.omnifaces.util.Faces;

/**
 *
 * @author BalusC
 * @author Armando Alaniz
 * @see <a href="https://stackoverflow.com/questions/34883270/how-to-use-java-time-zoneddatetime-localdatetime-in-pcalendar/34924587">Original question on stackoverflow</a>
 */
public abstract class BaseDateTimeConverter implements Converter {

    private static final String ERROR_SUMMARY = "Conversion error";

    public abstract String getDefaultDateTimePattern();

    private boolean isValid(Object modelValue) {
        return Converters.targetModelType(this).isAssignableFrom(modelValue.getClass());
    }

    private String getForClassName() {
        return Converters.targetModelType(this).getSimpleName();
    }

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String submittedValue) {
        if (submittedValue == null || submittedValue.isEmpty()) {
            return null;
        }

        try {
            return getFormatter(context, component).parse(submittedValue);
        } catch (DateTimeParseException dtpe) {
            throw new ConverterException(new FacesMessage(ERROR_SUMMARY, submittedValue + " is not a valid " + getForClassName()), dtpe);
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object modelValue) {
        if (modelValue == null) {
            return "";
        }

        if (isValid(modelValue)) {
            return getFormatter(context, component).format((LocalDateTime) modelValue);
        } else {
            throw new ConverterException(new FacesMessage(ERROR_SUMMARY, modelValue.getClass().getName() + " is not a valid " +
                    getForClassName()));
        }
    }

    private DateTimeFormatter getFormatter(FacesContext context, UIComponent component) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(getPattern(component), getLocale(context, component));
        return formatter;
    }

    private String getPattern(UIComponent component) {
        String pattern = Optional.ofNullable((String) component.getAttributes().get("pattern")).orElse(getDefaultDateTimePattern());

        if (pattern == null) {
            throw new IllegalArgumentException("Pattern attribute is required");
        }

        return pattern;
    }

    private Locale getLocale(FacesContext context, UIComponent component) {
        Object locale = Optional.ofNullable(component.getAttributes().get("locale")).orElse(Faces.getContextAttribute("locale"));
        return (locale instanceof Locale) ? (Locale) locale :
                (locale instanceof String) ? new Locale((String) locale) : context.getViewRoot().getLocale();
    }

}
