package com.xl.concurrency;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ConcurrencyMain {

    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 10; i++) {
            executor.execute(new DemoTask(i));
        }

        executor.shutdown();

        for (int i = 0; i < 10; i++) {
            Thread thread = new Thread(new Fibonacci(10, i));
            thread.start();
        }
    }

}
