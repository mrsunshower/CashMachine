package com.cashmachine;

import com.cashmachine.exception.NotEnoughMoneyException;

import java.util.*;

/**
 * Created by KenTerror on 28.12.2016.
 */
public class CurrencyManipulator {
    private String currencyCode;
    private Map<Integer, Integer> denominations;

    public CurrencyManipulator(String currencyCode) {
        this.currencyCode = currencyCode;
        denominations = new TreeMap<>(Collections.reverseOrder());
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void addAmount(int denomination, int count) {
        if (denominations.containsKey(denomination)) {
            denominations.put(denomination, denominations.get(denomination) + count);
        } else {
            denominations.put(denomination, count);
        }
    }

    public int getTotalAmount() {
        int amount = 0;
        for (Map.Entry<Integer, Integer> nominal : denominations.entrySet()) {
            amount += nominal.getKey() * nominal.getValue();
        }

        return amount;
    }

    public boolean hasMoney() {
        if (denominations != null && !denominations.isEmpty()) {
            for (Map.Entry<Integer, Integer> entry : denominations.entrySet()) {
                if (entry.getValue() > 0) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isAmountAvailable(int expectedAmount) {
        return getTotalAmount() >= expectedAmount;
    }

    public Map<Integer, Integer> withdrawAmount(int expectedAmount) throws NotEnoughMoneyException {

            Map<Integer, Integer> denominationsCopy = new TreeMap<>(Collections.reverseOrder());
            Map<Integer, Integer> withdrawBanknouts = new TreeMap<>(Collections.reverseOrder());

            denominationsCopy.putAll(denominations);

        try {
            if (!banknoutCheck(denominationsCopy, withdrawBanknouts, expectedAmount)) {
                throw new NotEnoughMoneyException();
            }

            for (Map.Entry<Integer, Integer> entry : withdrawBanknouts.entrySet()) {
                denominations.put(entry.getKey(), denominations.get(entry.getKey()) - entry.getValue());
            }
        } catch (ConcurrentModificationException e){ }

        return withdrawBanknouts;
    }

    private boolean banknoutCheck(Map<Integer, Integer> denominationsCopy, Map<Integer, Integer> withdrawBanknouts, int expectedAmount) {
        withdrawBanknouts.clear();
        int tempExpectedAmount = expectedAmount;



        for (Map.Entry<Integer, Integer> pair : denominationsCopy.entrySet()) {
            boolean isToMuch = false;
            for(int i = pair.getValue(); i > 0 && !isToMuch; i--) {
                if (tempExpectedAmount - pair.getKey() > 0) {
                    tempExpectedAmount -= pair.getKey();
                    if (withdrawBanknouts.containsKey(pair.getKey())) {
                        withdrawBanknouts.put(pair.getKey(), withdrawBanknouts.get(pair.getKey()) + 1);
                    } else {
                        withdrawBanknouts.put(pair.getKey(), 1);
                    }
                } else if (tempExpectedAmount - pair.getKey() == 0) {
                    if (withdrawBanknouts.containsKey(pair.getKey())) {
                        withdrawBanknouts.put(pair.getKey(), withdrawBanknouts.get(pair.getKey()) + 1);
                    } else {
                        withdrawBanknouts.put(pair.getKey(), 1);
                    }
                    return true;
                } else {
                    isToMuch = true;
                }
            }
        }

        Iterator<Map.Entry<Integer, Integer>> iterator = denominationsCopy.entrySet().iterator();
        Map.Entry<Integer, Integer> pair2 = iterator.next();
        iterator.remove();

        return denominationsCopy.size() != 0 && banknoutCheck(denominationsCopy, withdrawBanknouts, expectedAmount);
    }
}