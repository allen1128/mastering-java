package com.xl.concurrency.coordinate;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

public class CompletableFutureDemo {
    static class OwnThreadFactory implements ThreadFactory {

        @Override
        public Thread newThread(Runnable r) {
            return new Thread(r, "Own name");
        }
    }

    public static void main(String[] args) {
        ExecutorService gatherClothExecutor = Executors.newFixedThreadPool(2, new OwnThreadFactory());
        ExecutorService sweepFloorExecutor = Executors.newFixedThreadPool(2, new OwnThreadFactory());

        CompletableFuture<String> f2 =
            CompletableFuture.supplyAsync(() -> {
                System.out.println(Thread.currentThread().getName() + ": Gather clothe from BR 1");
                sleep(10);
                System.out.println(Thread.currentThread().getName() + ": Gather clothe from BR 2");
                sleep(10);
                System.out.println(Thread.currentThread().getName() + ": Gather clothe from BR 3");
                sleep(10);
                return "Put the cloth into washer for cleaning";
            }, gatherClothExecutor);

        CompletableFuture<Void> f1 =
            CompletableFuture.runAsync(() -> {
                System.out.println(Thread.currentThread().getName() + ": Sweep floor for BR 1");
                sleep(10);
                System.out.println(Thread.currentThread().getName() + ": Sweep floor for BR 2");
                sleep(10);
                System.out.println(Thread.currentThread().getName() + ": Sweep floor for BR 3");
                sleep(10);
            }, sweepFloorExecutor);

        CompletableFuture<String> f3 =
            f1.thenCombine(f2, (__, tf) -> {
                System.out.println(Thread.currentThread().getName() + ": Hang the cloth to dry");
                return "Done";
            });

        System.out.println(f3.join());
        gatherClothExecutor.shutdown();
        sweepFloorExecutor.shutdown();
    }

    public static void sleep(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
