package com.cashmachine.command;



import com.cashmachine.Operation;
import com.cashmachine.exception.InterruptOperationException;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by KenTerror on 28.12.2016.
 */
public class CommandExecutor {
    static Map<Operation, Command> mapOfCommands = new HashMap<>();

    static {
        mapOfCommands.put(Operation.LOGIN, new LoginCommand());
        mapOfCommands.put(Operation.INFO, new InfoCommand());
        mapOfCommands.put(Operation.DEPOSIT, new DepositCommand());
        mapOfCommands.put(Operation.WITHDRAW, new WithdrawCommand());
        mapOfCommands.put(Operation.EXIT, new ExitCommand());
    }

    private CommandExecutor() {}

    public static final void execute(Operation operation) throws InterruptOperationException {
        if (mapOfCommands.containsKey(operation)) {
            mapOfCommands.get(operation).execute();
        }
    }
}
