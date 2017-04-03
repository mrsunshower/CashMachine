package com.cashmachine.command;

import com.cashmachine.CashMachine;
import com.cashmachine.ConsoleHelper;
import com.cashmachine.CurrencyManipulator;
import com.cashmachine.CurrencyManipulatorFactory;
import com.cashmachine.exception.InterruptOperationException;
import com.cashmachine.exception.NotEnoughMoneyException;

import java.util.ResourceBundle;

/**
 * Created by KenTerror on 28.12.2016.
 */
class WithdrawCommand implements Command {
    private ResourceBundle res = ResourceBundle.getBundle(CashMachine.RESOURCE_PATH + "withdraw_en");
    @Override
    public void execute() throws InterruptOperationException {
        String code = ConsoleHelper.askCurrencyCode();
        CurrencyManipulator currencyManipulator = CurrencyManipulatorFactory.getManipulatorByCurrencyCode(code);

        String sum = "";
        boolean isIncorrect = true;
        boolean isEnoughMoney = false;

        while(isIncorrect) {
            do {
                ConsoleHelper.writeMessage(res.getString("specify.amount"));
                sum = ConsoleHelper.readString();

                if (sum.length() == 0) {
                    ConsoleHelper.writeMessage(res.getString("specify.not.empty.amount"));
                }

                isEnoughMoney = !currencyManipulator.isAmountAvailable(Integer.parseInt(sum));

                if (!isEnoughMoney) {
                    ConsoleHelper.writeMessage(res.getString("not.enough.money"));
                }
            } while (sum.length() <= 0 || !isEnoughMoney);

            try {
                currencyManipulator.withdrawAmount(Integer.parseInt(sum));
                ConsoleHelper.writeMessage(String.format(res.getString("success.format"), sum, code));
                isIncorrect = false;
            }
            catch (NotEnoughMoneyException e) {
                ConsoleHelper.writeMessage(res.getString("exact.amount.not.available"));
            }
        }
    }
}
