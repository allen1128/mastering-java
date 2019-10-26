package com.xl.concurrency.coordinate;

import java.util.Vector;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Demo {
    private Vector<String> list1 = new Vector<>();
    private Vector<String> list2 = new Vector<>();

    private Executor executor = Executors.newSingleThreadExecutor();

    final CyclicBarrier barrier = new CyclicBarrier(2, ()-> {
        executor.execute(() -> check());
    });

    public void check(){
        String str1 = list1.remove(0);
        String str2 = list2.remove(0);
        System.out.println(str1 + " - " + str2);
    }

    public void produce() {
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 100; i++) {
                    list1.add("a" + i);
                    try {
                        barrier.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (BrokenBarrierException e) {
                        e.printStackTrace();
                    }
                }

            }
        });

        thread1.start();

        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 100; i++) {
                    list2.add("b" + i);
                    try {
                        barrier.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (BrokenBarrierException e) {
                        e.printStackTrace();
                    }
                }

            }
        });

        thread2.start();
    }


    public static void main(String[] args) {
        Demo d = new Demo();
        d.produce();
    }

}
