package com.zacate.jdbc;

import com.zacate.util.ArrayUtils;
import java.time.LocalDate;

/**
 *
 * @author Armando Alaniz
 * @version 1.0
 * @since 1.0
 */
public abstract class JDBCUtils {

    public static LocalDate toLocalDate(java.sql.Date date) {
        return date != null ? LocalDate.ofEpochDay(date.getTime()) : null;
    }

    public static void close(AutoCloseable jdbcObj) throws Exception {
        if (jdbcObj == null) {
            return;
        }
        jdbcObj.close();
    }

    public static void close(AutoCloseable... objs) throws Exception {
        if (ArrayUtils.isEmpty(objs)) {
            return;
        }
        for (AutoCloseable obj : objs) {
            close(obj);
        }
    }

}
