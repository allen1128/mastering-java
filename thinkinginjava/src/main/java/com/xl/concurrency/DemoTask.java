package com.xl.concurrency;

public class DemoTask implements Runnable {
    private int count = 3;
    private int id;

    public DemoTask(int id) {
        this.id = id;
        System.out.println("Starting DemoTask" + id);
    }

    public void run() {
        while (count-- > 0){
            System.out.println("YIELD for " + id);
            Thread.yield();
        }
        System.out.println("Ending of DemoTask" + id);
    }
}
