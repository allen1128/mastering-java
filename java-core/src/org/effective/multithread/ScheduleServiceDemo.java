package org.effective.multithread;

import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static java.lang.Thread.sleep;

public class ScheduleServiceDemo {

    public static class LongRunningTask implements Runnable {
        @Override
        public void run() {
            System.out.println("Long running task start. Thread=" + Thread.currentThread().getName());
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Long running task finish. Thread=" + Thread.currentThread().getName());
            throw new RuntimeException();
        }
    }

    public static class FixedDelayedRunningTask implements Runnable {
        @Override
        public void run() {
            System.out.println("Fixed delayed running task start. Thread=" + Thread.currentThread().getName());
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Fixed delayed running task finish. Thread=" + Thread.currentThread().getName());
        }
    }

    public static class ScheduleAtFixedRate implements Runnable {
        @Override
        public void run() {
            System.out.println("Scheduled at fixed rate task start. Thread=" + Thread.currentThread().getName());
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Scheduled at fixed rate task finish. Thread=" + Thread.currentThread().getName());
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(100);
        executorService.schedule(new LongRunningTask(), 0, TimeUnit.SECONDS);
        executorService.schedule(new FixedDelayedRunningTask(), 100, TimeUnit.MILLISECONDS);
        executorService.scheduleAtFixedRate(new ScheduleAtFixedRate(), 0L, 1L, TimeUnit.MICROSECONDS);
        sleep(10000);
        executorService.shutdown();
    }

}
