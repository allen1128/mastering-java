package org.effective.multithread;

import java.util.Timer;
import java.util.TimerTask;

import static java.lang.Thread.sleep;

/**
 * TimerTask is executed in the same thread.
 * uncaught exception will terminate the thread.
 * Therefore each TimeTask needs to catch the exception in its run method
 */
public class TimerFixedDelayDemo {
    public static class LongRunningTaskA extends TimerTask {
        @Override
        public void run() {
            System.out.println("task A by thread=" + Thread.currentThread().getName());
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //throw  new RuntimeException("task A with exception");
        }
    }

    public static class FixedDelayedTaskB extends TimerTask {
        @Override
        public void run() {
            System.out.println("task B by thread=" + Thread.currentThread().getName());
        }
    }

    public static void main(String[] args) {
        Timer timer = new Timer();
        timer.schedule(new LongRunningTaskA(), 0);
        timer.schedule(new FixedDelayedTaskB(), 100);
    }
}
