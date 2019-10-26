package com.xl.concurrency.lock;


import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Account {
    private int id;
    private double amount;
    private final Lock lock = new ReentrantLock();

    public Account(int id, double amount) {
        this.id = id;
        this.amount = amount;
    }

    public void transfer(Account target, double amount) throws InterruptedException {
        boolean flag = true;
        while (flag) {
            //wait a random amount to avoid live lock.
            if (lock.tryLock((long) (Math.random() * 1000), TimeUnit.NANOSECONDS)) {
                if (target.lock.tryLock((long) (Math.random() * 1000), TimeUnit.NANOSECONDS)) {
                    target.amount += amount;
                    this.amount -= amount;
                    flag = false;
                    target.lock.unlock();
                }
                lock.unlock();
            }
        }
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", amount=" + amount +
                '}';
    }
}
