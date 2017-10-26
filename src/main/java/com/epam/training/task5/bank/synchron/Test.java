package com.epam.training.task5.bank.synchron;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Test {
    public static void main(String[] args) {
        Bank bank = new Bank(100);

        Runnable bankUser1 = new BankUser(bank);
        Runnable bankUser2 = new BankUser(bank);
        Runnable bankUser3 = new BankUser(bank);
        Runnable bankUser4 = new BankUser(bank);

        Executor executor = Executors.newFixedThreadPool(4);
        executor.execute(bankUser1);
        executor.execute(bankUser2);
        executor.execute(bankUser3);
        executor.execute(bankUser4);
    }
}
