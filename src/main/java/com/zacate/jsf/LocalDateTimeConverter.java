package com.zacate.jsf;

import java.time.LocalDateTime;
import javax.faces.convert.FacesConverter;
import org.omnifaces.util.Faces;

/**
 *
 * @author Armando Alaniz
 * @version 1.0
 * @since 1.0
 */
@FacesConverter(forClass = LocalDateTime.class)
public class LocalDateTimeConverter extends BaseDateTimeConverter {

    @Override
    public String getDefaultDateTimePattern() {
        return Faces.getContextAttribute("localDateTimePattern");
    }

}
