package com.zacate.jsf;

import java.time.LocalDate;
import javax.faces.convert.FacesConverter;
import org.omnifaces.util.Faces;

/**
 *
 * @author Armando Alaniz
 * @version 1.0
 * @since 1.0
 */
@FacesConverter(forClass = LocalDate.class)
public class LocalDateConverter extends BaseDateTimeConverter {

    @Override
    public String getDefaultDateTimePattern() {
        return Faces.getContextAttribute("localDatePattern");
    }

}
