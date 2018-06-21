package com.zacate.jdbc;

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
        if (jdbcObj != null) {
            jdbcObj.close();
        }
    }

    public static void close(AutoCloseable... objs) throws Exception {
        if (objs == null || objs.length == 0) {
            return;
        }
        for (AutoCloseable obj : objs) {
            close(obj);
        }
    }

}
