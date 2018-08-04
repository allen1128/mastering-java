package org.effective.multithread;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.IntStream;

public class RaceCondition {
    static abstract class Counter {
        public abstract void incr();
        public abstract int getCount();
    }

    static class Counter1 extends Counter {
        private int count = 0;

        private Object lock = new Object();

        public void incr() {
            synchronized (lock) {
                count++;
            }
        }

        public int getCount() {
            return count;
        }
    }

    static class Counter2 extends Counter {
        private int count = 0;

        private Lock lock = new ReentrantLock();

        public void incr() {
            lock.lock();
            try {
                count++;
            } finally {
                lock.unlock();
            }
        }

        public int getCount(){
            return count;
        }
    }

    static class CounterThread extends Thread {
        private Counter counter;

        public CounterThread(Counter counter) {
            this.counter = counter;
        }

        @Override
        public void run() {
            for (int i = 0; i < 1000; i++) {
                counter.incr();
            }
        }
    }

    public static void main(String[] args) {
        int num = 1000;
        Counter counter = new Counter2();
        //Counter counter = new Counter1();
        Thread[] threads = new Thread[num];
        IntStream.range(0, num)
                .forEach(i -> {
                    threads[i] = new CounterThread(counter);
                    threads[i].start();
                });

        IntStream.range(0, num)
                .forEach(i -> {
                    try {
                        threads[i].join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });


        System.out.println("Count=" + counter.getCount());


    }
}
