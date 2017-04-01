package com.cashmachine;

/**
 * Created by KenTerror on 28.12.2016.
 */
public enum Operation {
    LOGIN,
    INFO,
    DEPOSIT,
    WITHDRAW,
    EXIT;

    public static Operation getAllowableOperationByOrdinal(Integer i) throws IllegalArgumentException {
        if (i > 0 && i <= 4) {
            return Operation.values()[i];
        } else {
            throw new IllegalArgumentException();
        }
    }
}
