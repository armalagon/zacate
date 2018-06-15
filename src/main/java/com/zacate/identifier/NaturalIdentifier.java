package com.zacate.identifier;

import java.io.Serializable;

/**
 *
 * @author Armando Alaniz
 * @version 1.0
 * @since 1.0
 */
public interface NaturalIdentifier<K extends Serializable> {

    K getCode();

}
