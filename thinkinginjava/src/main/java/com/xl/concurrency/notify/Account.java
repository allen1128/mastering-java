package com.xl.concurrency.notify;

public class Account {
    private int id;
    private float amount;

    public Account(int id, float amount) {
        this.id = id;
        this.amount = amount;
    }

    public void transfer(Account target, float amount) throws InterruptedException {
        AccountManager.getInstance().lock(this, target);
        synchronized (this) {
            synchronized (target) {
                this.amount -= amount;
                target.amount += amount;

            }
        }
        AccountManager.getInstance().release(this, target);
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", amount=" + amount +
                '}';
    }
}
