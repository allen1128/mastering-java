package com.xl.concurrency.lock;

import java.util.concurrent.CountDownLatch;

public class Demo {
    public static void main(String[] args) throws InterruptedException {
        Account a = new Account(1, 1000d);
        Account b = new Account(2, 1000d);

        CountDownLatch countDownLatch = new CountDownLatch(1000);
        for (int i = 0; i < 1000; i++) {
            if (i % 3 == 0) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            a.transfer(b, 1d);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        countDownLatch.countDown();
                    }
                }).start();
            } else {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            b.transfer(a, 1d);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        countDownLatch.countDown();
                    }
                }).start();
            }

        }

        countDownLatch.await();

        System.out.println(a);
        System.out.println(b);

    }
}
