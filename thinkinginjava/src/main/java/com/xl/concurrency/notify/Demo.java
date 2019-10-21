package com.xl.concurrency.notify;

import java.util.concurrent.CountDownLatch;

public class Demo {
    public static void main(String[] args) throws InterruptedException {
        Account account1 = new Account(1, 1000f);
        Account account2 = new Account(2, 1000f);

        CountDownLatch countDownLatch = new CountDownLatch(1000);

        for (int i = 0; i < 1000; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        account1.transfer(account2, 1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
            countDownLatch.countDown();
        }

        countDownLatch.await();
        System.out.println(account1);
        System.out.println(account2);
    }
}
