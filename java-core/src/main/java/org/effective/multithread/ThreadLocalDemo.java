package org.effective.multithread;

import java.util.concurrent.ConcurrentHashMap;

public class ThreadLocalDemo {
    private static ThreadLocal<Integer> local = new ThreadLocal<>();
    private static ConcurrentHashMap<String, String> map;

    public static void main(String[] args){
        Thread newThread = new Thread() {
            @Override
            public void run() {
                local.set(100);
                System.out.println("local="+local.get()+ " by Thread=" + Thread.currentThread().getName());
            }
        };
        local.set(200);
        newThread.start();
        System.out.println("local="+local.get() + " by Thread=" + Thread.currentThread().getName());
    }
}
