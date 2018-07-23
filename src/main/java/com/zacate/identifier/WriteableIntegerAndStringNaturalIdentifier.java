package com.zacate.identifier;

import com.zacate.model.WriteableIdentifier;

/**
 *
 * @author Armando Alaniz
 * @version 1.0
 * @since 1.0
 */
public abstract class WriteableIntegerAndStringNaturalIdentifier extends WriteableIdentifier<Integer> implements WriteableNaturalIdentifier<String> {

    protected WriteableIntegerAndStringNaturalIdentifier() {
    }

    protected WriteableIntegerAndStringNaturalIdentifier(Integer id) {
        this.id = id;
    }

}
