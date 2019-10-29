package com.xl.concurrency.coordinate;

import org.apache.tools.ant.Executor;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CompletionService;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class CompletionServiceDemo {
    static class OwnThreadFactory implements ThreadFactory {
        private ThreadFactory threadFactory;
        private AtomicInteger ai = new AtomicInteger(1);
        private String prefix;

        public OwnThreadFactory(ThreadFactory tf, String prefix) {
            this.threadFactory = tf;
            this.prefix = prefix;
        }

        @Override
        public Thread newThread(Runnable r) {
            Thread t = threadFactory.newThread(r);
            t.setName(prefix + ai.getAndIncrement());
            return t;
        }
    }

    static public ExecutorService executorService =
            Executors.newFixedThreadPool(3, new OwnThreadFactory(Executors.defaultThreadFactory(), "task-pool-thread"));

    static public float getS1() {
        return 1.1f;
    }

    static public float getS2() {
        return 12.2f;
    }

    static public float getS3() {
        return 3.3f;
    }


    public static void save(float price) {
        System.out.println("saving price " + price);

    }

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        BlockingQueue<Future<Float>> blockingQueue = new LinkedBlockingDeque<>(3);
        CompletionService<Float> cs = new ExecutorCompletionService<Float>(executorService, blockingQueue);

        cs.submit(() -> getS1());
        cs.submit(() -> getS2());
        cs.submit(() -> getS3());

        AtomicReference<Float> ar = new AtomicReference<>(0.0f);
        CountDownLatch cdl =  new CountDownLatch(3);
        for (int i = 0; i < 3; i++) {
            float price = cs.take().get();
            executorService.execute(() -> save(price));
            ar.set(Math.max(ar.get(), price));
            cdl.countDown();
        }
        cdl.await();

        System.out.println("max price:" + ar);
        executorService.shutdown();
    }
}
