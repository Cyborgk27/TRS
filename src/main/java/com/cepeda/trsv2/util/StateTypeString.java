package com.cepeda.trsv2.util;

/**
 *
 * @author CyborgK27
 */
public class StateTypeString {
    public static String stateToString(int state) {
        return state == 0 ? "INACTIVO" : "ACTIVO";
    }
}
