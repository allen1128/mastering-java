package org.effective.multithread;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class ThreadPoolDemoThread implements Runnable {

    private int taskNum;

    public ThreadPoolDemoThread(int taskNum){
        this.taskNum = taskNum;
    }

    public static void main(String [] args) {
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(5);
        for (int i = 0; i < 10; i++){
            ThreadPoolDemoThread demo = new ThreadPoolDemoThread(i);
            executor.execute(demo);
            System.out.println("PoolSize=" + executor.getPoolSize() + ", Waiting=" +
                    executor.getQueue().size() +", Finished=" + executor.getCompletedTaskCount());
        }
        executor.shutdown();
    }

    @Override
    public void run() {
        System.out.println(String.format("task %s is executing", taskNum));
        try {
            Thread.currentThread().sleep((long) (Math.random() * 1000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(String.format("task %s finished executing", taskNum));
    }
}
