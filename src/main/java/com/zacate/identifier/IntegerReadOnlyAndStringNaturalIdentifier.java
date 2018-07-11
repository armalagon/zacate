package com.zacate.identifier;

import com.zacate.model.ReadOnlyIdentifier;

/**
 *
 * @author Armando Alaniz
 * @version 1.0
 * @since 1.0
 */
public abstract class IntegerReadOnlyAndStringNaturalIdentifier extends ReadOnlyIdentifier<Integer> implements NaturalIdentifier<String> {

    protected IntegerReadOnlyAndStringNaturalIdentifier(Integer id) {
        super(id);
    }

}
