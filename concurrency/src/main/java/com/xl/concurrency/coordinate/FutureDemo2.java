package com.xl.concurrency.coordinate;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class FutureDemo2 {
    public static float getPriceByS1() throws InterruptedException {
        Thread.sleep(100000);
        return 1.1f;
    }

    public static float getPriceByS2() throws InterruptedException {
        Thread.sleep(100);
        return 2.1f;
    }

    public static float getPriceByS3() throws InterruptedException {
        Thread.sleep(100);
        return 3.1f;
    }

    public static void save(float price) {
        System.out.println("saving price " + price);

    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(3);
        Future<Float> f1 = executor.submit( ()->getPriceByS1());
        Future<Float> f2 = executor.submit( ()->getPriceByS2());
        Future<Float> f3 = executor.submit( ()->getPriceByS3());

        final float r= f1.get();
        executor.execute(()->save(r));

        final float r2=f2.get();
        executor.execute(()->save(r2));

        final float r3=f3.get();
        executor.execute(()->save(r3));
    }
}
