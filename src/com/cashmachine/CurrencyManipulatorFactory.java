package com.cashmachine;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by KenTerror on 28.12.2016.
 */
public class CurrencyManipulatorFactory {
    private static Map<String, CurrencyManipulator> mapOfCurrency = new HashMap<>();
    private static CurrencyManipulatorFactory currencyManipulatorFactory;

    private CurrencyManipulatorFactory(){}

    public static CurrencyManipulatorFactory getInstance() {
        if(currencyManipulatorFactory == null) {
            currencyManipulatorFactory = new CurrencyManipulatorFactory();
        }
        return currencyManipulatorFactory;
    }

    public static CurrencyManipulator getManipulatorByCurrencyCode(String currencyCode){
        if(!mapOfCurrency.containsKey(currencyCode)) {
            mapOfCurrency.put(currencyCode, new CurrencyManipulator(currencyCode));
        }

        return mapOfCurrency.get(currencyCode);
    }

    public static Collection <CurrencyManipulator> getAllCurrencyManipulators() {
        return mapOfCurrency.values();
    }
}
