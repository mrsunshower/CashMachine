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
class DepositCommand implements Command {
    private ResourceBundle res = ResourceBundle.getBundle(CashMachine.RESOURCE_PATH + "deposit_en");
    @Override
    public void execute() throws InterruptOperationException {
        try {
            ConsoleHelper.writeMessage(res.getString("before"));
            String code = ConsoleHelper.askCurrencyCode();
            CurrencyManipulator currencyManipulator = CurrencyManipulatorFactory.getManipulatorByCurrencyCode(code);
            String[] temp = ConsoleHelper.getValidTwoDigits(currencyManipulator.getCurrencyCode());
            currencyManipulator.addAmount(Integer.parseInt(temp[0]), Integer.parseInt(temp[1]));
            ConsoleHelper.writeMessage(String.format(res.getString("success.format"), (Integer.parseInt(temp[0])*Integer.parseInt(temp[1])), code));
        } catch (NumberFormatException e) {
            ConsoleHelper.writeMessage("invalid.data");
        }
    }
}
