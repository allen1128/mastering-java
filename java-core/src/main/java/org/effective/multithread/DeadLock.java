package org.effective.multithread;

public class DeadLock {
    private static Object lock1 = new Object();
    private static Object lock2 = new Object();

    static class StartThreadA extends Thread {
        @Override
        public void run(){
            synchronized (lock1) {
                System.out.println("got lock1");

                try {
                    Thread.sleep(1000);
                    synchronized (lock2) {
                        System.out.println("got both locks");
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static class StartThreadB extends Thread {
        @Override
        public void run(){
            synchronized (lock2) {
                System.out.println("got lock2");
                try {
                    Thread.sleep(1000);
                    synchronized (lock1) {
                        System.out.println("get both locks");
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args){
        new StartThreadA().start();
        new StartThreadB().start();

        //try to get the lock in the agreed upon order to avoid this.
    }
}
