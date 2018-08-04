package org.effective.multithread;


import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;

public class Visibility {
    private static volatile boolean shutdown = false;

    static class DemoThread extends Thread {
        @Override
        public void run() {
            System.out.println("starting thread");
            while (!shutdown) {};
            System.out.println("exiting thread");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        new DemoThread().start();
        Thread.sleep(1000);
        shutdown = true;
        System.out.println("exiting main");

        Map<String, String> map = new ConcurrentSkipListMap();
        Queue<String> q = new LinkedBlockingQueue<>();



    }
}
