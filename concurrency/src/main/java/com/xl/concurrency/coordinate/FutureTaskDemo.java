package com.xl.concurrency.coordinate;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class FutureTaskDemo {
    FutureTask<String> futureTask2 = new FutureTask<>(new TaskThread2());
    FutureTask<String> futureTask1 = new FutureTask<>(new TaskThread1(futureTask2));

    //this task will CLEAN FLOOR, CLEAN TABLE, HANG THE wet cloth
    public class TaskThread1 implements Callable<String> {
        private FutureTask futureTask;

        public TaskThread1(FutureTask ft) {
            this.futureTask = ft;
        }

        @Override public String call() throws Exception {
            System.out.println(Thread.currentThread().getName() + " : " + "Sweep floor for BR1");
            Thread.sleep(20);
            System.out.println(Thread.currentThread().getName() + " : " + "Sweep floor for BR2");
            Thread.sleep(20);
            System.out.println(Thread.currentThread().getName() + " : " + "Sweep floor for BR3");
            Thread.sleep(20);
            System.out.println(futureTask.get());
            return Thread.currentThread().getName() + " : Hang the cloth to dry - Done";
        }
    }


    //this task will gather the cloth from bedroom 1, 2, 3, put it into washer.
    public class TaskThread2 implements Callable<String> {
        @Override public String call() throws Exception {
            System.out.println(Thread.currentThread().getName() + " : " + "Gather clothe from BR 1");
            Thread.sleep(20);
            System.out.println(Thread.currentThread().getName() + " : " + "Gather clothe from BR 2");
            Thread.sleep(20);
            System.out.println(Thread.currentThread().getName() + " : " + "Gather clothe from BR 3");
            Thread.sleep(20);
            return Thread.currentThread().getName() + " : " + "Put the cloth into washer for cleaning";
        }
    }


    public static void main(String[] args) throws ExecutionException, InterruptedException {
        FutureTaskDemo fts = new FutureTaskDemo();
        new Thread(fts.futureTask1).start();
        new Thread(fts.futureTask2).start();
        System.out.println(fts.futureTask1.get());
    }

}
