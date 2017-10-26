package com.epam.training.task5.bank.synchron;

import com.epam.training.task5.bank.ImpossibleOperationException;


public class Bank {

    private int money;

    public Bank(int money) {
        this.money = money;
    }

    public int getMoney(int amount) throws ImpossibleOperationException {
        if (amount > money) {
            throw new ImpossibleOperationException("Bank has no money");
        }
        money -= amount;
        System.out.println(money);
        return amount;
    }

    public boolean hasMoney() {
        return money > 0;
    }
}
