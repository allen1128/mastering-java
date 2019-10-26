package com.xl.concurrency.semaphore;

import java.util.List;
import java.util.Vector;
import java.util.concurrent.Semaphore;
import java.util.function.Function;

public class ObjPool<T, R> {
    final List<T> pool;
    final Semaphore sem;

    public ObjPool(int size, T t) {
        this.pool = new Vector<>();
        for (int i = 0; i < size; i++) {
            this.pool.add(t);
        }
        this.sem = new Semaphore(size);
    }

    R exec(Function<T, R> func) {
        T t = null;
        try {
            sem.acquire(); //blocking
            try {
                t = pool.remove(0);
                return func.apply(t);
            } finally {
                pool.add(t);
                sem.release();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) throws InterruptedException {
        ObjPool objPool = new ObjPool<Long, String>(10, 2l);
        for (int i = 0; i < 100; i++) {
            new Thread( () -> {
                objPool.exec(t -> {
                    System.out.println(t);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return t.toString();
                });
            }).start();
        }
    }
}
