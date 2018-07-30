package com.zacate.jdbc;

import com.zacate.util.Arguments;

/**
 *
 * @author Armando Alaniz
 * @version 1.0
 * @since 1.0
 */
public abstract class JDBCUtils {

    public static void close(AutoCloseable jdbcObj) throws Exception {
        if (jdbcObj == null) {
            return;
        }
        jdbcObj.close();
    }

    public static void close(AutoCloseable... objs) throws Exception {
        if (Arguments.isEmpty(objs)) {
            return;
        }
        for (AutoCloseable obj : objs) {
            close(obj);
        }
    }

}
