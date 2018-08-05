package org.effective.multithread;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Iterator;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import static java.lang.System.exit;
import static java.lang.Thread.sleep;

public class ConsumerProducerDemo {
    public static class MyBlockingQueue<E> {
        private Queue<E> queue;
        private final int limit;

        public MyBlockingQueue(int limit) {
            this.queue = new ArrayDeque<>();
            this.limit = limit;
        }

        public synchronized void put(E e) throws InterruptedException {
            while (queue.size() >= limit) {
                wait();
            }
            queue.add(e);
            notifyAll();
        }

        public synchronized E poll() throws InterruptedException {
            while (queue.size() == 0) {
                wait();
            }

            E e = queue.poll();
            notifyAll();
            return e;
        }
    }

    public static class ProducerThread extends Thread {

        private MyBlockingQueue queue;

        public ProducerThread(MyBlockingQueue queue) {
            this.queue = queue;
        }

        @Override
        public void run() {
            int number = 0;
            try {
                while (true) {
                    sleep((long) (Math.random() * 1000));
                    queue.put(number);
                    number++;
                    System.out.println("number=" + number + " is put into queue");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static class ConsumerThread extends Thread {
        private MyBlockingQueue queue;

        public ConsumerThread(MyBlockingQueue queue) {
            this.queue = queue;
        }

        @Override
        public void run() {
            try {
                while (true) {
                    sleep((long) (Math.random() * 1000));
                    int res = (int) queue.poll();
                    System.out.println("number=" + res + " is consumed");
                }
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        MyBlockingQueue<Integer> queue = new MyBlockingQueue<>(5);
        //ArrayBlockingQueue<Integer> queue1 = new ArrayBlockingQueue<Integer>(5);

        Thread t1 = new ConsumerThread(queue);
        Thread t2 = new ProducerThread(queue);

        t1.start();
        t2.start();
    }
}
