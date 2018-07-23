package com.zacate.identifier;

import java.io.Serializable;

/**
 *
 * @author Armando Alaniz
 * @version 1.0
 * @since 1.0
 */
public interface WriteableNaturalIdentifier<K extends Serializable> {

    K getCode();

    void setCode(K k);

}
