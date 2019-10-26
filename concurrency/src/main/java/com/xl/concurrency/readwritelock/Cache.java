package com.xl.concurrency.readwritelock;

import org.omg.CORBA.INTERNAL;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Cache<K, V> {
    final Map<K, V> map = new HashMap<>();
    final ReadWriteLock rwl = new ReentrantReadWriteLock();

    final Lock w = rwl.writeLock();
    final Lock r = rwl.readLock();

    V get(K key) {
        V v = null;
        r.lock();
        try {
            v = map.get(key);
        } finally {
            r.unlock();
        }

        if (v != null) {
            return v;
        }

        //initializing the cache as it's not found
        w.lock();
        try {
            //double check in case other fetch has occurred in other Threads.
            v = map.get(key);
            if (v != null) {
                return v;
            }
            //get the value of v
            map.put(key, v);
        } finally {
            w.unlock();
        }
        return v;
    }

    V put(K key, V value) {
        w.lock();
        try {
            map.put(key, value);
        } finally {
            w.unlock();
        }

        return value;
    }

    boolean isDirty = false;

    void processData() {
        V v = null;
        r.lock();
        if (isDirty) {
            r.unlock();
            w.lock();

            try {
                if (isDirty) {
                    //get the new v;
                    isDirty = false;
                }

                r.lock();
            } finally {
                w.unlock();
            }
        }

        try {
            //use data
        } finally {
            r.unlock();
        }
    }

    public static void main(String[] args) {
        Cache cache = new Cache<String, Integer>();
        cache.put("1", 1);
        cache.put("1", 2);
        System.out.println(cache.get("1"));
    }
}
