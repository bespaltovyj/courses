package com.epam.training.task5.bank.synchron;

import com.epam.training.task5.bank.ImpossibleOperationException;

public class BankUser implements Runnable {

    private final Bank bank;

    public BankUser(Bank bank) {
        this.bank = bank;
    }

    @Override
    public void run() {
        synchronized (bank) {
            try {
                while (bank.hasMoney()) {
                    bank.getMoney(5);
                }

            }catch (ImpossibleOperationException e) {
                System.out.println(e.getMessage());
            }
        }
        System.out.println("Thread finished");
    }
}
