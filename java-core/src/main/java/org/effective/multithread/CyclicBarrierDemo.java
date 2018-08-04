package org.effective.multithread;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.stream.IntStream;

public class CyclicBarrierDemo {
    public static class ChildThread extends Thread {

        private final CyclicBarrier barrier;

        public ChildThread(CyclicBarrier barrier) {
            this.barrier = barrier;
        }

        @Override
        public void run() {
            try {
                Thread.sleep((int) Math.random() * 1000);
                barrier.await();
                System.out.println("Arrived at A at " + System.currentTimeMillis() +
                        "; Thread " + Thread.currentThread().getName());

                Thread.sleep((int) Math.random() * 1000);
                barrier.await();
                System.out.println("Arrived at B at " + System.currentTimeMillis() +
                        " ; Thread " + Thread.currentThread().getName());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        }
    }


    public static void main(String[] args) {

        //the number of parties is equal to child threads
        final int parties = 30;
        CyclicBarrier c = new CyclicBarrier(parties, new Runnable() {
            @Override
            public void run() {
                System.out.println("Waiting for child threads; executed by " + Thread.currentThread().getName());
            }
        });

        Thread[] threads = new Thread[parties];
        IntStream.range(0, parties).forEach( n -> {
            threads[n] = new ChildThread(c);
            threads[n].start();
        });
    }
}
