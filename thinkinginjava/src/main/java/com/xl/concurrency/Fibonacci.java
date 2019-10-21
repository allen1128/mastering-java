package com.xl.concurrency;

import java.util.ArrayList;
import java.util.List;

public class Fibonacci implements Runnable {
    private int num;
    private int id;
    private List<Integer> list = new ArrayList<Integer>();

    public Fibonacci(int num, int id) {
        this.num = num;
        this.id = id;
    }

    public void run() {
        if (num >= 0) {
            list.add(0);
        }

        if (num >= 1) {
            list.add(1);
        }

        if (num >= 2) {
            recur(2);
        }

        System.out.println(id + "=" + list);
    }

    private void recur(int n) {
        if (n >= num) {
            return;
        } else {
            list.add(list.get(list.size() - 2) + list.get(list.size() - 1));
            System.out.println(id + "=" + list);
            recur(n + 1);
        }
    }
}
