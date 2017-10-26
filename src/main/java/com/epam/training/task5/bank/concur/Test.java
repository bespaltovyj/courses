package com.epam.training.task5.bank.concur;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Test {

    public static int countThreads = 10;

    public static void main(String[] args) {
        Bank bank = new Bank(100);

        Runnable bankUser1 = new BankUser(bank);
        Runnable bankUser2 = new BankUser(bank);
        Runnable bankUser3 = new BankUser(bank);
        Runnable bankUser4 = new BankUser(bank);

        Executor executor = Executors.newFixedThreadPool(countThreads);
        executor.execute(bankUser1);
        executor.execute(bankUser2);
        executor.execute(bankUser3);
        executor.execute(bankUser4);
    }
}
