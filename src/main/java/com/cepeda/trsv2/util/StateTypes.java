package com.cepeda.trsv2.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 *
 * @author CyborgK27
 */
@Getter
@AllArgsConstructor
public enum StateTypes {
    INACTIVE(0),
    ACTIVE(1);
    
    final int value;
}
