package com.zacate.identifier;

import com.zacate.model.ReadOnlyIdentifier;

/**
 *
 * @author Armando Alaniz
 * @version 1.0
 * @since 1.0
 */
public abstract class ReadOnlyIntegerAndStringNaturalIdentifier extends ReadOnlyIdentifier<Integer> implements NaturalIdentifier<String> {

    protected ReadOnlyIntegerAndStringNaturalIdentifier(Integer id) {
        super(id);
    }

}
