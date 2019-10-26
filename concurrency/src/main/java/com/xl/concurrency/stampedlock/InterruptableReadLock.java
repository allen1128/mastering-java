package com.xl.concurrency.stampedlock;

import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.StampedLock;

public class InterruptableReadLock {

    public static void main(String[] args) throws InterruptedException {
        StampedLock stampedLock = new StampedLock();

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                stampedLock.writeLock();
                LockSupport.park();
            }
        });

        t1.start();
        Thread.sleep(1000);

        Thread t2 = new Thread(() -> {
            //note that here readLoockInterruptibly should be used instead of readLock
            try {
                stampedLock.readLockInterruptibly();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        t2.start();
        Thread.sleep(1000);
        t2.interrupt();
        t2.join();
    }
}
