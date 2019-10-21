package com.xl.concurrency.notify;

import java.util.ArrayList;
import java.util.List;

public class AccountManager {
    private static AccountManager instance = new AccountManager();

    private List<Account> locks = new ArrayList<>();

    private AccountManager() {};

    public static AccountManager getInstance() {
        return instance;
    }

    public synchronized void lock(Account one, Account another) throws InterruptedException {
        while (locks.contains(one) || locks.contains(another)) {
            this.wait();
        }

        locks.add(one);
        locks.add(another);
    }

    public synchronized void release(Account one, Account another) {
        locks.remove(one);
        locks.remove(another);
        this.notifyAll();
    }
}
