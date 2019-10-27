package com.xl.concurrency;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ThreadPool {

    BlockingQueue<Runnable> workQueue;

    List<WorkThread> threadList = new ArrayList<>();

    public ThreadPool(int poolSize, BlockingQueue<Runnable> workQueue) {
        this.workQueue = workQueue;
        for (int i = 0; i < poolSize; i++) {
            WorkThread thread = new WorkThread();
            thread.start();
            threadList.add(thread);
        }
    }

    class WorkThread extends Thread {
        @Override
        public void run() {
            while (true) {
                try {
                    Runnable runnable = workQueue.take();
                    runnable.run();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }

        }
    }

    public void execute(Runnable runnable) {
        workQueue.add(runnable);
    }

    public static void main(String[] args) throws InterruptedException {
        BlockingQueue<Runnable> queue = new LinkedBlockingQueue<>(2);
        ThreadPool threadPool = new ThreadPool(10, queue);
        for (int i = 0; i < 1000; i++) {
            threadPool.execute(() -> {
                System.out.println("ThreadName" + Thread.currentThread().getName() +" printing hello world");
            });
            Thread.sleep(10);
        }
    }

}
