package com.cashmachine.command;

import com.cashmachine.CashMachine;
import com.cashmachine.ConsoleHelper;
import com.cashmachine.exception.InterruptOperationException;

import java.util.Enumeration;
import java.util.ResourceBundle;

/**
 * Created by KenTerror on 29.12.2016.
 */
public class LoginCommand implements Command {
    private ResourceBundle validCreditCards = ResourceBundle.getBundle(CashMachine.RESOURCE_PATH + "verifiedCards");
    private ResourceBundle res = ResourceBundle.getBundle(CashMachine.RESOURCE_PATH + "login_en");

    @Override
    public void execute() throws InterruptOperationException {
        ConsoleHelper.writeMessage(res.getString("before"));
        boolean isCorrect = false;

        while (!isCorrect) {
            Enumeration bundleKeys = validCreditCards.getKeys();
            String[] userCard = new String[2];

            ConsoleHelper.writeMessage(res.getString("specify.data"));
            userCard[0] = ConsoleHelper.readString();
            userCard[1] = ConsoleHelper.readString();

            if (userCard[0] != null && userCard[1] != null && userCard[0].length() == 12 && userCard[1].length() == 4) {
                while (bundleKeys.hasMoreElements()) {
                    String key = (String) bundleKeys.nextElement();
                    String value = validCreditCards.getString(key);

                    if (key.equals(userCard[0])) {
                        if (value.equals(userCard[1])) {
                            ConsoleHelper.writeMessage(String.format(res.getString("success.format"), userCard[0]));
                            isCorrect = true;
                            break;
                        } else {
                            ConsoleHelper.writeMessage(String.format(res.getString("not.verified.format"), userCard[0]));
                            ConsoleHelper.writeMessage(res.getString("try.again.or.exit"));
                        }
                    }
                }
            } else {
                ConsoleHelper.writeMessage(String.format(res.getString("not.verified.format"), userCard[0]));
                ConsoleHelper.writeMessage(res.getString("try.again.with.details"));
            }
        }
    }
}
