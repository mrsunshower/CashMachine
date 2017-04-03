package com.cashmachine.command;

import com.cashmachine.CashMachine;
import com.cashmachine.ConsoleHelper;
import com.cashmachine.exception.InterruptOperationException;

import java.util.ResourceBundle;

/**
 * Created by KenTerror on 28.12.2016.
 */
class ExitCommand implements Command {
    private ResourceBundle res = ResourceBundle.getBundle(CashMachine.RESOURCE_PATH + "exit_en");
    @Override
    public void execute() throws InterruptOperationException {
        ConsoleHelper.writeMessage(res.getString("Are sure want to exit?(y/n) : "));

        String answer = ConsoleHelper.readString();
        if (answer.equals(res.getString("yes"))) {
            System.out.println(res.getString("thank.message"));
        }
    }
}
