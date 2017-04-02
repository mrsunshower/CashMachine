package com.cashmachine;



import com.cashmachine.command.CommandExecutor;
import com.cashmachine.exception.InterruptOperationException;

import java.util.Locale;

/**
 * Created by KenTerror on 28.12.2016.
 */
public class CashMachine {
    public static final String RESOURCE_PATH = "com.cashmachine.resources.";

    public static void main(String[] args) {
        Locale.setDefault(Locale.ENGLISH);

        Operation operation;

        try {
            CommandExecutor.execute(Operation.LOGIN);
            do {
                operation = ConsoleHelper.askOperation();
                CommandExecutor.execute(operation);
            }
            while (operation != Operation.EXIT);
        } catch (InterruptOperationException e) {
            ConsoleHelper.printExitMessage();
        }
    }
}
