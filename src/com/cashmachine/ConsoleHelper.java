package com.cashmachine;

import com.cashmachine.exception.InterruptOperationException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ResourceBundle;

/**
 * Created by KenTerror on 28.12.2016.
 */
public class ConsoleHelper {
    private static ResourceBundle res = ResourceBundle.getBundle(CashMachine.RESOURCE_PATH + "common_en");
    private static BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

    public static void writeMessage(String message){
        System.out.println(message);
    }

    public static void printExitMessage() {
        writeMessage(res.getString("the.end"));
    }

    public static String readString() throws InterruptOperationException {
        String line = "";

        try {
            if ((line = bufferedReader.readLine()).equalsIgnoreCase(res.getString("operation.EXIT"))) {
                throw new InterruptOperationException();
            }
        } catch (IOException e) {}

        return line;
    }

    public static String askCurrencyCode() throws InterruptOperationException {
        String currencyCode= "";

        writeMessage(res.getString("choose.currency.code"));
        while ((currencyCode = readString()).length() != 3){
            writeMessage(res.getString("invalid.data"));
            writeMessage(res.getString("choose.currency.code"));
        }
        return currencyCode.toUpperCase();
    }

    public static String[] getValidTwoDigits(String currencyCode) throws InterruptOperationException {
        String[] twoDigits = new String[2];

        writeMessage(String.format(res.getString("choose.denomination.and.count.format"), currencyCode));
        twoDigits = readString().split(" ");

        while ((Integer.parseInt(twoDigits[1]) <= 0) || (Integer.parseInt(twoDigits[0]) <= 0)){
            writeMessage(res.getString("invalid.data"));
            writeMessage(String.format(res.getString("choose.denomination.and.count.format"), currencyCode));
            twoDigits = readString().split(" ");
        }

        return twoDigits;
    }

    public static Operation askOperation() throws InterruptOperationException {
        String stringOperation = "5";
        Operation operation = Operation.DEPOSIT;

        try {
            writeMessage(res.getString("choose.operation"));
            stringOperation = readString();
            operation = Operation.getAllowableOperationByOrdinal(Integer.parseInt(stringOperation));
        } catch (IllegalArgumentException e) {
            writeMessage(res.getString("invalid.data"));
            askOperation();
        }

        return operation;
    }
}
