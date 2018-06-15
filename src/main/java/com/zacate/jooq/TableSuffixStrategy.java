package com.zacate.jooq;

import org.jooq.codegen.DefaultGeneratorStrategy;
import org.jooq.meta.Definition;
import org.jooq.meta.TableDefinition;

/**
 *
 * @author Armando Alaniz
 * @version 1.0
 * @since 1.0
 */
public class TableSuffixStrategy extends DefaultGeneratorStrategy {

    private static final String TABLE_SUFFIX = "Table";

    @Override
    public String getJavaClassName(Definition definition, Mode mode) {
        if (definition instanceof TableDefinition && Mode.DEFAULT == mode) {
            return super.getJavaClassName(definition, mode) + TABLE_SUFFIX;
        } else {
            return super.getJavaClassName(definition, mode);
        }
    }

}
