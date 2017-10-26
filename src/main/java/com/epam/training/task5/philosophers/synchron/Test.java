package com.epam.training.task5.philosophers.synchron;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Test {

    public static void main(String[] args) {
        Fork fork1 = new Fork();
        Fork fork2 = new Fork();
        Fork fork3 = new Fork();
        Fork fork4 = new Fork();
        Fork fork5 = new Fork();

        Runnable philosopher1 = new Philosopher(fork1, fork2,1);
        Runnable philosopher2 = new Philosopher(fork2, fork3,2);
        Runnable philosopher3 = new Philosopher(fork3, fork4,3);
        Runnable philosopher4 = new Philosopher(fork4, fork5,4);
        Runnable philosopher5 = new Philosopher(fork1, fork5,5);

        Executor executor = Executors.newFixedThreadPool(5);
        executor.execute(philosopher1);
        executor.execute(philosopher2);
        executor.execute(philosopher3);
        executor.execute(philosopher4);
        executor.execute(philosopher5);
    }
}
