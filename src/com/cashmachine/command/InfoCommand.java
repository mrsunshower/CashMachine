package com.cashmachine.command;

import com.cashmachine.CashMachine;
import com.cashmachine.ConsoleHelper;
import com.cashmachine.CurrencyManipulator;
import com.cashmachine.CurrencyManipulatorFactory;
import com.cashmachine.exception.InterruptOperationException;

import java.util.ResourceBundle;

/**
 * Created by KenTerror on 28.12.2016.
 */
class InfoCommand implements Command {
    private ResourceBundle res = ResourceBundle.getBundle(CashMachine.RESOURCE_PATH + "info_en");
    @Override
    public void execute() throws InterruptOperationException {
        ConsoleHelper.writeMessage(res.getString("before"));
        boolean hasMoney = false;

        for (CurrencyManipulator currency : CurrencyManipulatorFactory.getAllCurrencyManipulators()) {
            if (currency.hasMoney()) {
                System.out.format("%s - %d\n", currency.getCurrencyCode(), currency.getTotalAmount());
                hasMoney = true;
            }
        }

        if(!hasMoney) {
            System.out.println(res.getString("no.money"));
        }
    }
}
